package cap.img;


import ij.process.ImageProcessor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCR {

    public static String tesseract(final CompoundImage compoundImage) {
        CompoundImage newCompoundImage = compoundImage.clone();
        Tesseract instance = Tesseract.getInstance();

        String result = "";
        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            try {
                String imageText = instance.doOCR(captchaImage.getImage().getBufferedImage());
                result = result.concat(imageText);
            }
            catch (TesseractException err) {

            }
        }
        return result;
    }
}
