package cap.img;


import cap.ProcessException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCR {

    public static String tesseract(final CompoundImage compoundImage) throws ProcessException {
        CompoundImage newCompoundImage = compoundImage.clone();
        Tesseract instance = Tesseract.getInstance();

        String result = "";
        for (CaptchaImage captchaImage : newCompoundImage.getImages()) {
            try {
                String imageText = instance.doOCR(captchaImage.getImage().getBufferedImage());
                result = result.concat(imageText);
            }
            catch (TesseractException err) {
                throw new ProcessException(err.getMessage());
            }
        }
        if (result.isEmpty()) {
            throw new ProcessException("OCR detected no text");
        }
        return result;
    }

    public static String isAlphanumeric(final String text) throws ProcessException {
        if (text.matches("[A-Za-z0-9]+")) {
            throw new ProcessException("is not alphanumeric");
        }
        return text;
    }

    public static String isNumeric(final String text) throws ProcessException {
        if (text.matches("[0-9]+")) {
            throw new ProcessException("is not numeric");
        }
        return text;
    }

    public static String isAlphabetic(final String text) throws ProcessException {
        if (text.matches("[A-Za-z]+")) {
            throw new ProcessException("is not alphabetic");
        }
        return text;
    }

    public static String findInDictionary(final String text) {
        return text;
    }


}
