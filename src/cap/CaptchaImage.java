package cap;

import cap.Vector2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class CaptchaImage {
	
	public CaptchaImage(Vector2D position, BufferedImage image) {
		this.position = position;
		this.image = image;
	}

	public CaptchaImage(BufferedImage image) {
		this.position = new Vector2D();
		this.image = image;
	}


	public CaptchaImage(Vector2D position, Graphics2D imageData) {
		this.position = position;
		imageData.drawImage(this.image, null, 0, 0);
	}
	
	public CaptchaImage(Graphics2D imageData) {
		this.position = new Vector2D();
		imageData.drawImage(this.image, null, 0, 0);
	}
	
	
	public Vector2D getPosition() {
		return position;
	}

	public Graphics2D getImageData() {
		return this.image.createGraphics();
	}

	
	private Vector2D position; 
	private BufferedImage image;
	
}
