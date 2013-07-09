package cap.img;


import cap.ProcessException;
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

    public static String asAlphanumeric(final String text) throws ProcessException {
        if (text.matches("[A-Za-z0-9]+")) {
            throw new ProcessException("is not alphanumeric");
        }
        return text;
    }

    public static String asNumeric(final String text) throws ProcessException {
        if (text.matches("[0-9]+")) {
            throw new ProcessException("is not numeric");
        }
        return text;
    }

    public static String asAlphabetic(final String text) throws ProcessException {
        if (text.matches("[A-Za-z]+")) {
            throw new ProcessException("is not alphabetic");
        }
        return text;
    }

    public static String findInDictionary(final String text) {
        return text;
    }


}
