package cap.img;

import java.util.ArrayList;

import ij.ImagePlus;
import ij.plugin.ChannelSplitter;
import ij.plugin.ChannelArranger;
import ij.process.ImageProcessor;



/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class Intensity {

    public static ImagePlus remove(ImagePlus image,
                                   final int beginIntervalParam,
                                   final int endIntervalParam,
                                   final int standardValue,
                                   final boolean includeBegin,
                                   final boolean includeEnd) {

        int beginInterval = includeBegin ? beginIntervalParam : beginIntervalParam - 1;
        int endInterval   = includeEnd   ? endIntervalParam : endIntervalParam   + 1;

        ImageProcessor imageProcessor = image.getProcessor();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                int pixelValue = image.getPixel(x, y)[0];
                boolean inArea = (beginInterval < pixelValue && pixelValue < endInterval);

                int newValue = (inArea) ? standardValue : pixelValue;
                imageProcessor.set(x, y, newValue);
            }
        }
        return image;
    }

    public static CompoundImage remove(CompoundImage compoundImage,
                                   final int beginInterval,
                                   final int endInterval,
                                   final int standardValue,
                                   final boolean includingBegin,
                                   final boolean includingEnd) {

        CompoundImage newCompoundImage = compoundImage.clone();
        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            ImagePlus[] images = ChannelSplitter.split(captchaImage.getImage());

            for (int channel = 0; channel < images.length; channel++) {
                images[channel] = Intensity.remove(images[channel],
                                                   beginInterval,
                                                   endInterval,
                                                   standardValue,
                                                   includingBegin,
                                                   includingEnd);
            }
        }
        return newCompoundImage;
    }


    public static CompoundImage brighten(final CompoundImage compoundImage, int value) {
        CompoundImage newCompoundImage = compoundImage.clone();

        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            ImagePlus image = captchaImage.getImage();

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int[] rgb = image.getPixel(x, y);

                    rgb[0] = StrictMath.max(0, StrictMath.min(rgb[0] + value, 255));
                    rgb[1] = StrictMath.max(0, StrictMath.min(rgb[1] + value, 255));
                    rgb[2] = StrictMath.max(0, StrictMath.min(rgb[2] + value, 255));
                }
            }
        }
        return compoundImage;
    }

    public static CompoundImage shade(final CompoundImage compoundImage, int value) {
        return Intensity.brighten(compoundImage, -value);
    }
}
