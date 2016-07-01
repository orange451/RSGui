package scripts.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RSGuiCheckbox extends RSGuiNode implements RSGuiMouseListener {
	private boolean checked;
	private RSGuiImageButton button;
	private RSGuiTextButton text;

	private static final BufferedImage BUTTON_NONE  = RSGuiFrame.BUTTON_CHECK_1;
	private static final BufferedImage BUTTON_CHECK = RSGuiFrame.BUTTON_CHECK_2;

	public RSGuiCheckbox( int x, int y, String text ) {
		super(x, y, 1, 1 );

		this.button = new RSGuiImageButton( x, y, BUTTON_NONE, BUTTON_NONE );
		this.text   = new RSGuiTextButton( x, y, -1, -1, text );
	}

	@Override
	protected void paint(Graphics g) {
		// UPdate locations of inner nodes
		this.button.setLocation(x, y);
		this.text.setLocation(x + this.button.getWidth() + 2, y + 1);

		// Update checked icon
		BufferedImage img = checked?BUTTON_CHECK:BUTTON_NONE;
		this.button.setImages( img, img );

		// Paint inner nodes
		this.button.paint(g);
		this.text.paint(g);

		// Update my width
		this.width = text.x + text.getWidth() - x;
		this.height = this.button.getHeight();
	}

	/**
	 * Returns the RSGuiTextButton associated with this check box.
	 * <br>
	 * <br>
	 * This can be used to customize the select color, or current checkbox text.
	 * @return
	 */
	public RSGuiTextButton getTextButton() {
		return this.text;
	}

	/**
	 * Returns whether or not the checkbox is checked.
	 * @return
	 */
	public boolean isChecked() {
		return this.checked;
	}

	@Override
	public boolean onMousePress(int x, int y) {
		if ( getBounds().contains( x, y ) ) {
			checked = !checked;

			this.button.onMouseDown(x, y);
			this.text.onMouseDown(x, y);
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onMouseDown(x, y);
			}
			return true;
		}

		return false;
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		this.button.onMouseUpdate(x, y);
		this.text.onMouseUpdate(x, y);
	}
	@Override public void onMouseDown(int x, int y) {}
}
