package scripts.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AwtUtil {
	public static BufferedImage getImage( String name ) {
		try {
			return ImageIO.read(RSGuiFrame.class.getClassLoader().getResource(name));
		} catch (IOException e) {
			//
		}
		return null;
	}
	public static Font getFont(String name) {
		Font font = null;
		String fName = "scripts/gui/res/" + name;

		try {
			URL url = RSGuiFrame.class.getClassLoader().getResource("scripts/gui/res/" + name);
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
			font = font.deriveFont( Font.PLAIN, 16 );
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(fName + " not loaded.  Using serif font.");
			font = new Font("serif", Font.PLAIN, 16);
		}
		return font;
	}

	public static void clearImage(BufferedImage image) {
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g2.setColor(new Color(0, 0, 0, 0));
		g2.fillRect(0,0,image.getWidth(),image.getHeight());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}
}
