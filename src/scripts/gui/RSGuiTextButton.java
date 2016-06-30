package scripts.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiTextButton extends RSGuiNode implements RSGuiMouseListener {
	private String text;
	private Color selectColor = Color.white;
	private Color normalColor = Color.white;
	private boolean hover = false;

	public RSGuiTextButton( int x, int y, int width, int height, String text ) {
		super( x, y, width, height );

		this.text = text;
	}

	@Override
	protected void paint(Graphics g) {
		Font f = RSGuiFrame.FONT_REGULAR;

		// Draw button text
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString(text, x + 1, y + 14);
		g.setColor(hover?selectColor:normalColor);
		g.drawString(text, x, y + 13);
	}

	public RSGuiTextButton setSelectColor( Color color ) {
		this.selectColor = color;
		return this;
	}

	public RSGuiTextButton setColor( Color color ) {
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

	public String getText() {
		return this.text;
	}
}
