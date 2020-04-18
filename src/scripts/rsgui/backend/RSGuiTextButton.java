package scripts.rsgui.backend;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class RSGuiTextButton extends RSGuiNode implements RSGuiMouseListener {
	private String text;
	private Color selectColor = Color.yellow;
	private Color normalColor = Color.white;
	private Color disableColor = Color.gray;
	private boolean disabled;
	protected boolean hover = false;

	public RSGuiTextButton(int x, int y, int width, int height, String text) {
		super(x, y, width, height);

		this.text = text;
	}

	protected void paint(Graphics g) {
		scripts.rsgui.font.RSFont f = RSGuiRes.FONT_REGULAR;
		g.setFont(f.getFont());

		if (this.width == -1)
			this.width = g.getFontMetrics().stringWidth(this.text);
		if (this.height == -1) {
			this.height = 16;
		}

		g.setColor(Color.BLACK);
		g.drawString(this.text, this.x + 1, this.y + 14);
		g.setColor(this.disabled ? this.disableColor : (this.hover ? this.selectColor : this.normalColor));
		g.drawString(this.text, this.x, this.y + 13);
	}

	public RSGuiTextButton setSelectColor(Color color) {
		this.selectColor = color;
		return this;
	}

	public RSGuiTextButton setColor(Color color) {
		this.normalColor = color;
		return this;
	}

	public boolean onMousePress(int x, int y) {
		if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener) this.listeners.get(i)).onMousePress(x, y);
			}
			return true;
		}
		return false;
	}

	public void onMouseDown(int x, int y) {
		if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener) this.listeners.get(i)).onMouseDown(x, y);
			}
		}
	}

	public void onMouseUpdate(int x, int y) {
		this.hover = getBounds().contains(x, y);

		if (this.hover) {
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener) this.listeners.get(i)).onMouseUpdate(x, y);
			}
		}
	}

	public String getText() {
		return this.text;
	}

	public void setText(String string) {
		this.text = string;
	}

	public void setDisabled(boolean b) {
		this.disabled = b;
	}

	public boolean isDisabled() {
		return this.disabled;
	}
}
