package scripts.rsgui.backend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.tribot.api.General;

public class RSGuiButtonToggle extends RSGuiNode implements RSGuiMouseListener {
	private boolean selected;
	private BufferedImage buttonImage;
	private BufferedImage icon;

	public RSGuiButtonToggle(BufferedImage icon) {
		super(0, 0, icon.getWidth()+10, icon.getHeight()+10);
		this.buttonImage = new BufferedImage(this.width, this.height, 2);
		this.icon = icon;
	}

	protected void paint(Graphics g) {
		scripts.rsgui.font.RSFont f = RSGuiRes.FONT_REGULAR;
		
	    Graphics g2 = this.buttonImage.getGraphics();
	    g2.setColor(new Color(78, 69, 58));
	    g2.fillRect(3, 3, this.width - 6, this.height - 6);

		int tw = RSGuiRes.BUTTON_TOGGLE_1.getWidth();
		int th = RSGuiRes.BUTTON_TOGGLE_1.getHeight();
		g2.setColor(Color.white);
		
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_1:RSGuiRes.BUTTON_TOGGLE_1, 0, 0, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_3:RSGuiRes.BUTTON_TOGGLE_3, this.width - tw, 0, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_5:RSGuiRes.BUTTON_TOGGLE_5, this.width - tw, this.height - th, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_7:RSGuiRes.BUTTON_TOGGLE_7, 0, this.height - th, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_2:RSGuiRes.BUTTON_TOGGLE_2, tw, 0, this.width - tw * 2, th, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_6:RSGuiRes.BUTTON_TOGGLE_6, tw, this.height - th, this.width - tw * 2, th, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_4:RSGuiRes.BUTTON_TOGGLE_4, this.width - tw, th, tw, this.height - th * 2, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_8:RSGuiRes.BUTTON_TOGGLE_8, 0, th, tw, this.height - th * 2, null);
		g2.drawImage(selected?RSGuiRes.BUTTON_TOGGLE_S_9:RSGuiRes.BUTTON_TOGGLE_9, tw, th, this.width-tw*2,this.height-th*2, null);
		g2.setColor(Color.WHITE);
		
		g2.drawImage(icon, tw, th, null);
		
		g.drawImage(this.buttonImage, this.x, this.y, null);
	}

	public boolean onMousePress(int x, int y) {
		if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
			selected = true;
			
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener)this.listeners.get(i)).onMousePress(x, y);
			}
			
			return true;
		}
		return false;
	}

	public void onMouseDown(int x, int y) {
		if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener)this.listeners.get(i)).onMouseDown(x, y);
			}
		}
	}

	public void onMouseUpdate(int x, int y) {
		if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener)this.listeners.get(i)).onMouseUpdate(x, y);
			}
		}
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
