package scripts.gui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RSFont {
	private Font font;
	private BufferedImage img;

	public RSFont( Font f ) {
		this.img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.font = f;
	}

	public int getWidth( String text ) {
		String newStr = ChatColor.stripColor(text);
		Graphics g = img.getGraphics();
		g.setFont(font);
		return g.getFontMetrics().stringWidth(newStr);
	}

	public Font getFont() {
		return this.font;
	}

	public void drawString(Graphics g, String text, int x, int y) {
		g.translate(x, y);
		g.setFont(font);
		Color col = g.getColor();

		int chars = text.length();
		int offset = 0;
		for (int i = 0; i < chars; i++) {
			char c = text.charAt(i);
			if ( c == ChatColor.COLOR_CODE.charAt(0) && i < chars - 1 ) {
				String check = text.substring(i, i + 2);
				ChatColor chatColor = ChatColor.getChatColor(check);
				g.setColor( chatColor.toColor() );
				i++;
			} else {
				String cc = "" + c;
				g.drawString(cc, offset, 0);
				offset += getWidth( cc );
			}
		}
		g.translate(-x, -y);
		g.setColor(col);
	}

	public void drawStringShadow(Graphics g, String text, int x, int y) {
		String str = ChatColor.stripColor(text);

		Color col = g.getColor();
		g.setFont(font);
		drawString( g, ChatColor.BLACK + str, x + 1, y + 1 );
		g.setColor(col);
		drawString( g, text, x, y );
	}

	public int getHeight() {
		return 16;
	}
}
