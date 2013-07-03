package cap.img;

import ij.ImagePlus;
import java.awt.Point;

public class CaptchaImage {

    public CaptchaImage(Point position, ImagePlus image) {
        this.position = position;
        this.image = image;
    }

    public CaptchaImage(ImagePlus image) {
        this.position = new Point(0, 0);
        this.image = image;
    }

    public Point getPosition() {
        return position;
    }

    public ImagePlus getImageData() {
        return this.image;
    }

    private Point position;
    private ImagePlus image;

}
