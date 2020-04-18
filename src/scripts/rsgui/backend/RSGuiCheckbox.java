package scripts.rsgui.backend;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RSGuiCheckbox extends RSGuiNode implements RSGuiMouseListener {
	private boolean checked;
	private RSGuiImageButton button;
	private RSGuiTextButton text;
	private static final BufferedImage BUTTON_NONE = RSGuiRes.BUTTON_CHECK_1;
	private static final BufferedImage BUTTON_CHECK = RSGuiRes.BUTTON_CHECK_2;
	private static final BufferedImage BUTTON_CHECK_DIS = RSGuiRes.BUTTON_CHECK_3;
	private boolean disabled;
	
	private List<RSGuiCheckboxListener> checkListeners = new ArrayList<>();

	public RSGuiCheckbox(int x, int y, String text) {
		super(x, y, 1, 1);

		this.button = new RSGuiImageButton(x, y, BUTTON_NONE, BUTTON_NONE);
		this.text = new RSGuiTextButton(x, y, -1, -1, text);
	}
	
	public void addCheckboxListener(RSGuiCheckboxListener listener) {
		checkListeners.add(listener);
	}

	protected void paint(Graphics g) {
		this.button.setLocation(this.x, this.y);
		this.text.setLocation(this.x + this.button.getWidth() + 4, this.y + 1);
		this.text.setDisabled(this.isDisabled());

		BufferedImage img = this.checked ? (this.isDisabled() ? BUTTON_CHECK_DIS : BUTTON_CHECK) : BUTTON_NONE;
		this.button.setImages(img, img);

		this.button.paint(g);
		this.text.paint(g);

		this.width = (this.text.x + 4 + this.text.getWidth() - this.x);
		this.height = this.button.getHeight();
	}

	public RSGuiTextButton getTextButton() {
		return this.text;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean value) {
		this.checked = value;
	}

	public boolean onMousePress(int x, int y) {
		if (getBounds().contains(x, y)) {
			if ( this.isDisabled() )
				return false;
			
			setChecked(!this.isChecked());

			this.button.onMouseDown(x, y);
			this.text.onMouseDown(x, y);
			for (int i = 0; i < this.listeners.size(); i++) {
				((RSGuiMouseListener) this.listeners.get(i)).onMouseDown(x, y);
			}
			
			boolean c = this.isChecked();
			for (RSGuiCheckboxListener listener : checkListeners) {
				listener.onChecked(c);
			}
			
			return true;
		}

		return false;
	}

	public void onMouseUpdate(int x, int y) {
		this.button.onMouseUpdate(x, y);
		this.text.onMouseUpdate(x, y);

		this.text.hover = getBounds().contains(x, y);
	}

	public void onMouseDown(int x, int y) {
	}

	public void setDisabled(boolean b) {
		this.disabled = b;
	}

	public boolean isDisabled() {
		return this.disabled;
	}
}
