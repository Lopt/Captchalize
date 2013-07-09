package cap.img;

/**
 * Created with IntelliJ IDEA.
 * User: leto
 * Date: 09.07.13
 * Time: 20:59
 * To change this template use File | Settings | File Templates.
 */
public class Convert {

    static public CompoundImage toCompoundImage(CaptchaImage captchaImage) {
        return new CompoundImage(captchaImage);
    }
}
