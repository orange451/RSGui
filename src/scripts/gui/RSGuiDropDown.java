package scripts.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiDropDown extends RSGuiNode implements RSGuiMouseListener {
	private ArrayList<RSGuiNode> choices = new ArrayList<RSGuiNode>();
	private String choice = "";
	private boolean open;

	private static final BufferedImage image1 = RSGuiFrame.BUTTON_DROPDOWN_1;
	private static final BufferedImage image2 = RSGuiFrame.BUTTON_DROPDOWN_2;

	private BufferedImage i1;
	private RSGuiPanelScroll panel;

	private RSGuiTextLabel cc;
	private int mx;
	private int my;

	public RSGuiDropDown( int x, int y, int width ) {
		super( x, y, width, image1.getHeight() + 6 );

		cc = new RSGuiTextLabel( 3, 4, width, height, "" );
		cc.setShadow(true);

		i1 = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );

		panel = new RSGuiPanelScroll( x, y, width, (height - 4) * 10 );
		panel.add( new RSGuiOutline( Color.black,             0 ) );
		panel.add( new RSGuiOutline( new Color(154, 106, 49), 1 ) );
		panel.setScrollBarInset( 3 );
	}

	/**
	 * This method ads a choice to the dropdown list.
	 * @param choice
	 * @return RSGuiTextButton representation of the choice.
	 */
	public RSGuiTextButton addChoice(String choice) {
		final RSGuiTextButton b = new RSGuiTextButton( 3, 3, width - 20, height - 3, choice );
		b.setColor( Color.white );
		b.setSelectColor( Color.red );
		b.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseDown(int x, int y) {}

			@Override
			public boolean onMousePress(int x, int y) {
				RSGuiDropDown.this.choice = b.getText();
				close();
				return true;
			}

			@Override
			public void onMouseUpdate(int x, int y) {

			}

		});

		this.panel.add( b );
		b.setLocation(b.x, b.y + choices.size() * b.getHeight() );

		this.choices.add( b );
		if (this.choice.equals("")) {
			this.choice = choice;
		}

		return b;
	}

	/**
	 * This method returns the current string choice selected by the user.
	 * @return
	 */
	public String getCurrentChoice() {
		return this.choice;
	}

	@Override
	protected void paint(Graphics g) {
		// Check if we need to re-create our images
		if ( i1.getWidth() != width || i1.getHeight() != height ) {
			i1 = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
		}

		// Draw main box
		Graphics g1 = i1.getGraphics();
		g1.setColor(Color.black);
		g1.fillRect(0, 0, width, height);
		g1.setColor( new Color(154, 106, 49) );
		g1.fillRect(1, 1, width-2, height-2);
		g1.setColor( new Color(84, 75, 64) );
		if ( !open && getBounds().contains(mx, my) )
			g1.setColor( new Color(99,91,80) );
		g1.fillRect(2, 2, width-4, height-4);

		// Draw current choice
		cc.setText( choice );
		cc.paint(g1);

		// Draw button
		BufferedImage img = open?image2:image1;
		g1.drawImage(img, width - image1.getWidth() - 3, 3, null);

		// Draw image to parent node
		g.drawImage( i1, x, y, null );

		// Draw the choices if we're open!
		panel.setLocation(x, y + height - 1);
	}

	/**
	 * This function opens the dropdown menu.
	 */
	public void open() {
		((RSGuiPanel)parent).add(this.panel);
		open = true;
	}

	/**
	 * This function closes the dropdown menu.
	 */
	public void close() {
		((RSGuiPanel)parent).remove(this.panel);
		open = false;
	}

	@Override
	public boolean onMousePress(int x, int y) {
		if ( open ) {
			if ( !panel.isMouseOver() ) {
				this.close();
				return true;
			}
		} else {
			if ( getBounds().contains( x, y ) ) {
				this.open();
				return true;
			}
		}

		return false;
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		mx = x;
		my = y;
	}

	@Override
	public void onMouseDown(int x, int y) {
		//
	}
}
