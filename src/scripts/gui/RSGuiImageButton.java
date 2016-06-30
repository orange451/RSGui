package scripts.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiImageButton extends RSGuiNode implements RSGuiMouseListener {
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage currentImage;

	public RSGuiImageButton( BufferedImage img1, BufferedImage img2 ) {
		super( 0, 0, img1.getWidth(), img1.getHeight() );
		this.image1 = img1;
		this.image2 = img2;
		this.currentImage = image1;
	}

	@Override
	protected void paint(Graphics g) {
		g.drawImage(currentImage, x, y, null);
	}

	@Override
	public boolean onMousePress(int x, int y) {
		if ( x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height ) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onMousePress(x, y);
			}
			currentImage = image1;
			return true;
		}
		return false;
	}

	@Override
	public void onMouseDown(int x, int y) {
		if ( x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height ) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onMouseDown(x, y);
			}
			currentImage = image1;
		}
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		currentImage = image1;
		if ( x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height ) {
			currentImage = image2;
		}
	}
}
