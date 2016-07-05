package scripts.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RSGuiImageLabel extends RSGuiNode {
	private BufferedImage image;

	public RSGuiImageLabel( int x, int y, BufferedImage image ) {
		super( x, y, 0, 0 );

		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
	}


	@Override
	protected void paint(Graphics g) {
		g.drawImage(image, x, y, null);
	}
}
