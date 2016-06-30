package scripts.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiButton extends RSGuiNode implements RSGuiMouseListener {
	private BufferedImage buttonImage;
	private String text;
	private Color selectColor = Color.white;
	private Color normalColor = Color.white;
	private boolean hover = false;

	public RSGuiButton( int x, int y, String text ) {
		super( x, y, 1, 1 );

		this.text = text;

		// Generate button
		buttonImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
		GENERATE_BUTTON();
	}

	private void GENERATE_BUTTON() {
		Graphics g = buttonImage.getGraphics();
		Font f = RSGuiFrame.FONT_REGULAR;
		g.setFont(f);
		int stringWid = g.getFontMetrics().stringWidth(text);
		width = stringWid + 12;
		height = 30;

		if ( buttonImage == null || buttonImage.getWidth() != width || buttonImage.getHeight() != height ) {
			buttonImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
		}
	}

	@Override
	protected void paint(Graphics g) {
		Font f = RSGuiFrame.FONT_REGULAR;
		GENERATE_BUTTON();

		// Get the graphics for the button image
		Graphics g2 = buttonImage.getGraphics();
		g2.setColor( new Color( 78, 69, 58 ) );
		g2.fillRect( 3, 3, width - 6, height - 6 );

		g2.setFont( f );
		int tw = RSGuiFrame.BUTTON_NORMAL_1.getWidth();
		int th = RSGuiFrame.BUTTON_NORMAL_1.getHeight();
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_1, 0, 0, null );
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_3, width - tw, 0, null );
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_5, width - tw, height - th, null );
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_7, 0, height - th, null );

		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_2, tw, 0, width - tw*2, 3, null );
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_6, tw, height - 3, width - tw*2, 3, null );
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_4, width - 3, th, 3, height - th*2, null );
		g2.drawImage( RSGuiFrame.BUTTON_NORMAL_8, 0, th, 3, height - th*2, null );

		// Draw button text
		g2.setColor(Color.BLACK);
		g2.drawString(text, 7, height/2 + 7);
		g2.setColor(hover?selectColor:normalColor);
		g2.drawString(text, 6, height/2 + 6);

		// Draw the image to the parent node
		g.drawImage(buttonImage, x, y, null);
	}

	/**
	 * This method sets the color the text will turn when hovered.
	 * @param color
	 * @return
	 */
	public RSGuiButton setSelectColor( Color color ) {
		this.selectColor = color;
		return this;
	}

	/**
	 * This methods sets the normal drawing color for the text.
	 * @param color
	 * @return
	 */
	public RSGuiButton setColor( Color color ) {
		this.normalColor = color;
		return this;
	}

	@Override
	public boolean onMousePress(int x, int y) {
		if ( x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height ) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onMousePress(x, y);
			}
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
		}
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		hover = getBounds().contains(x,y);

		if ( x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height ) {
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onMouseUpdate(x, y);
			}
		}
	}
}
