package scripts.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiDropDown extends RSGuiNode implements RSGuiMouseListener {
	private ArrayList<RSGuiTextLabel> choices = new ArrayList<RSGuiTextLabel>();
	private String choice = "";
	private boolean open;

	private static final BufferedImage image1 = RSGuiFrame.BUTTON_DROPDOWN_1;
	private static final BufferedImage image2 = RSGuiFrame.BUTTON_DROPDOWN_2;

	private BufferedImage i1;
	private BufferedImage i2;

	private RSGuiTextLabel cc;
	private int mx;
	private int my;

	public RSGuiDropDown( int x, int y, int width ) {
		super( x, y, width, image1.getHeight() + 6 );

		cc = new RSGuiTextLabel( 3, 4, width, height, "" );
		cc.setShadow(true);

		i1 = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
		i2 = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
	}

	public void addChoice(String choice) {
		RSGuiTextLabel c = new RSGuiTextLabel(0, 0, width - 6, height - 3, choice);
		c.setShadow(true);

		this.choices.add(c);
		if (this.choice.equals("")) {
			this.choice = choice;
		}
	}

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
		if ( open ) {
			int h1 = (height - 2);
			int hh = h1 * choices.size() + 5;
			g.setColor(Color.black);
			g.fillRect(x, y+height-1, width, hh);
			g.setColor( new Color(154, 106, 49) );
			g.fillRect(x+1, y+height, width-2, hh-2);
			g.setColor( new Color(84, 75, 64) );
			g.fillRect(x+2, y+height+1, width-4, hh-4);

			for (int i = 0; i < choices.size(); i++) {
				RSGuiNode n = choices.get(i);
				n.setLocation( x + 3, y + 2 + height + ( h1 * i ) );

				Rectangle b = n.getBounds();
				if ( b.contains(mx, my)) {
					g.setColor( new Color(99,91,80) );
					g.fillRect(b.x, b.y, b.width, b.height);
				}

				// Paint label
				n.paint(g);
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		if (!open)
			return super.getBounds();

		return new Rectangle(x, y, width, height + (height - 2) * choices.size() );
	}

	@Override
	public boolean onMousePress(int x, int y) {

		// If the mouse is inside the box then handle opening it
		if ( getBounds().contains(x, y) ) {
			if ( open ) {
				for (int i = 0; i < choices.size(); i++) {
					if ( choices.get(i).getBounds().contains(x, y)) {
						this.choice = choices.get(i).getText();
					}
				}
			}
			open = !open;
			return true;
		}

		// If we're open, and clicked outside of box, then force close and disregard this click.
		if ( open ) {
			open = false;
			return false;
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
