package cap.img;


import ij.ImagePlus;
import ij.plugin.GaussianBlur3D;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.ListIterator;


public class Blur {

    public static CompoundImage gaussian(final CompoundImage compoundImage, final double size) {
        CompoundImage newCompoundImage = compoundImage.clone();

        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            ImageProcessor imgProcessor  = captchaImage.getImage().getProcessor();

            imgProcessor.blurGaussian(size);
        }
        return newCompoundImage;
    }

    public static CompoundImage median(final CompoundImage compoundImage) {
        CompoundImage newCompoundImage = compoundImage.clone();

        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            ImageProcessor imgProcessor  = captchaImage.getImage().getProcessor();

            imgProcessor.medianFilter();
        }
        return newCompoundImage;
    }
}
