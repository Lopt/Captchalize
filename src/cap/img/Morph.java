package cap.img;


import ij.process.ImageProcessor;

public class Morph {

    public static CompoundImage dilate(final CompoundImage compoundImage) {
        CompoundImage newCompoundImage = compoundImage.clone();

        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            ImageProcessor imgProcessor  = captchaImage.getImage().getProcessor();
            imgProcessor.dilate();
        }
        return newCompoundImage;
    }

    public static CompoundImage erode(final CompoundImage compoundImage) {
        CompoundImage newCompoundImage = compoundImage.clone();

        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            ImageProcessor imgProcessor  = captchaImage.getImage().getProcessor();
            imgProcessor.erode();
        }
        return newCompoundImage;
    }

}
