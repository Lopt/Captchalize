package cap;

import cap.Vector2D;
import java.awt.Image;

public class CaptchaImage {
	
	public CaptchaImage(Vector2D position, Image imageData) {
		this.position = position;
		this.imageData = imageData;
		this.frequenceData = null;
	}
	
	public Vector2D getPosition() {
		return position;
	}

	public Image getFrequencyData() {
		if (this.frequenceData == null) {
			// TODO: Insert Fourier Transformation here
		}
		return this.frequenceData;
	}
	
	public Image getImageData() {
		return this.imageData;
	}

	
	private Vector2D position; 
	private Image    imageData;
	private Image    frequenceData;
	
}
