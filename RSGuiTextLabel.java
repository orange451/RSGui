package scripts.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RSGuiTextLabel extends RSGuiNode {
	private String text;
	private boolean bold;
	private Color color = Color.white;
	private boolean shadow;
	private boolean center;

	public RSGuiTextLabel( int x, int y, int width, int height, String text ) {
		super( x, y, width, height );
		this.text = text;
	}

	/**
	 * This function controls whether or not a dropshadow is drawn with the text.
	 * @param sh
	 * @return
	 */
	public RSGuiTextLabel setShadow( boolean sh ) {
		this.shadow = sh;
		return this;
	}

	/**
	 * This function controls how the text is drawn. If it is centered, then the x position will be the center of the text.
	 * @param center
	 * @return
	 */
	public RSGuiTextLabel setCentered( boolean center ) {
		this.center = center;
		return this;
	}

	/**
	 * This function sets the drawing color of the label.
	 * @param c
	 * @return
	 */
	public RSGuiTextLabel setColor( Color c ) {
		this.color = c;
		return this;
	}

	/**
	 * This function controls the font used to draw the text.
	 * @param bold
	 * @return
	 */
	public RSGuiTextLabel setBold( boolean bold ) {
		this.bold = bold;
		return this;
	}

	@Override
	protected void paint(Graphics g) {
		Font font = bold?RSGuiFrame.FONT_BOLD:RSGuiFrame.FONT_REGULAR;
		g.setFont( font );

		int xx = x;
		int wid = g.getFontMetrics().stringWidth(text);
		if (center)
			xx -= wid/2;

		if ( shadow ) {
			g.setColor(Color.black);
			g.drawString(text, xx+1, y+14);
		}
		g.setColor( color );
		g.drawString(text, xx, y + 13);
	}

	/**
	 * This function updates the text of this label.
	 * @param choice
	 */
	public void setText(String choice) {
		this.text = choice;
	}

	/**
	 * This function returns the current text of the label.
	 * @return
	 */
	public String getText() {
		return this.text;
	}
}
