package cap.img;

import ij.ImagePlus;
import ij.io.Opener;

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

    public CaptchaImage(String path) {
        this.position = new Point(0, 0);
        this.image = opener.openImage(path);
    }

    public Point getPosition() {
        return this.position;
    }

    public ImagePlus getImage() {
        return this.image;
    }

    @Override
    public CaptchaImage clone() {
        return new CaptchaImage((Point) this.position.clone(), this.image.duplicate());
    }

    private static Opener opener = new Opener();
    private Point position;
    private ImagePlus image;

}
