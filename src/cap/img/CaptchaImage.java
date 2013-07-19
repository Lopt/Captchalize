package cap.img;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;

import java.awt.Point;
import java.io.File;
import java.nio.ByteBuffer;

public class CaptchaImage {

    public CaptchaImage(Point position, ImagePlus image) {
        this.position = position;
        this.image = image;
    }

    public CaptchaImage(ImagePlus image) {
        this.position = new Point(0, 0);
        this.image = image;
    }

    public CaptchaImage(byte[] imageData) {
        this.position = new Point(0, 0);
        this.image = this.fromByteArray(imageData);
    }

    public CaptchaImage(File path) {
        this.position = new Point(0, 0);
        this.image = new ImagePlus(path.getName());
    }

    public CaptchaImage(String path) {
        this.position = new Point(0, 0);
        this.image = new ImagePlus(path);
    }

    public Point getPosition() {
        return this.position;
    }

    public ImagePlus getImage() {
        return this.image;
    }

    public byte[] toByteArray() {
        assert this.image.getProcessor() != null : "Corrupted image, processor could not be found.";

        ImagePlus image = this.image;
        ImageProcessor processor = image.getProcessor();

        int width = image.getWidth();
        int height = image.getHeight();
        int pixelSize = image.getBytesPerPixel();

        ByteBuffer buffer = ByteBuffer.allocate(16 + width * height * pixelSize);
        buffer.putInt(width);
        buffer.putInt(height);
        buffer.putInt(pixelSize);

        switch (pixelSize) {
            case 2:
                short[] pixelsTwo = (short[])processor.getPixels();
                for (int index = 0; index < width * height; index++) {
                    buffer.putShort(pixelsTwo[index]);
                }

                break;
            case 4:
                int[] pixelsFour = (int[])processor.getPixels();
                for (int index = 0; index < width * height; index++) {
                    buffer.putInt(pixelsFour[index]);
                }

                break;
            case 1:
            default:
                buffer.put((byte[])processor.getPixels());
                break;
        }

        return buffer.array();
    }

    @Override
    public CaptchaImage clone() {
        return new CaptchaImage((Point) this.position.clone(), this.image.duplicate());
    }

    private ImagePlus fromByteArray(byte[] imageData) {
        ByteBuffer buffer = ByteBuffer.wrap(imageData);

        int width = buffer.getInt();
        int height = buffer.getInt();
        int byteSize = buffer.getInt();

        ImageProcessor processor = null;
        switch (byteSize) {
            case 2:
                ShortProcessor shortProcessor = new ShortProcessor(width, height);
                short[] shortPixels = new short[width * height];

                for (int index = 0; index < width * height; index++) {
                    shortPixels[index] = buffer.getShort();
                }

                shortProcessor.setPixels(shortPixels);
                processor = shortProcessor;

                break;
            case 4:
                ColorProcessor colorProcessor = new ColorProcessor(width, height);
                int[] colorPixels = new int[width * height];

                for (int index = 0; index < width * height; index++) {
                    colorPixels[index] = buffer.getInt();
                }

                colorProcessor.setPixels(colorPixels);
                processor = colorProcessor;

                break;
            case 1:
            default:
                ByteProcessor byteProcessor = new ByteProcessor(width, height);
                byte[] bytePixels = new byte[width * height];

                byteProcessor.setPixels(buffer.get(bytePixels));
                processor = byteProcessor;

                break;
        }

        return new ImagePlus("Unnamed", processor);
    }

    private Point position;
    private ImagePlus image;

}
