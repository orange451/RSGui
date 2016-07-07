package scripts.gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.tribot.script.interfaces.EventBlockingOverride;

public abstract class RSGui {
	private ArrayList<RSGuiFrame> windows = new ArrayList<RSGuiFrame>();
	private BufferedImage iconImage;
	private BufferedImage iconImage2;
	private int ticks = 0;
	private boolean notify;
	private boolean open;
	private int repeatDraw = 0;

	private Rectangle guiBounds         = new Rectangle( 484, 168, RSGuiFrame.BUTTON_GUI_NORMAL.getWidth(), RSGuiFrame.BUTTON_GUI_NORMAL.getHeight() );
	private Rectangle iconsTopBounds    = new Rectangle( 522, 168, RSGuiFrame.ICONS_TOP.getWidth(), RSGuiFrame.ICONS_BOTTOM.getHeight() );
	private Rectangle iconsBottomBounds = new Rectangle( 522, 466, RSGuiFrame.ICONS_BOTTOM.getWidth(), RSGuiFrame.ICONS_BOTTOM.getHeight() );
	private Rectangle inventoryBounds   = new Rectangle( 547, 204, RSGuiFrame.INVENTORY.getWidth(), RSGuiFrame.INVENTORY.getHeight() );

	private RSGuiPanel botPanel;

	public RSGui( String icon ) {
		if ( icon != null ) {
			this.iconImage = AwtUtil.getImage(icon);
			this.iconImage2 = AwtUtil.generateMask( this.iconImage );
		}

		this.botPanel = new RSGuiPanel( inventoryBounds.width, inventoryBounds.height );
		this.botPanel.setLocation( inventoryBounds.x, inventoryBounds.y );
	}

	/**
	 * Registers a window with this GUI. A window cannot be used without first registering it.
	 * @param frame
	 */
	public void registerWindow( RSGuiFrame frame ) {
		this.windows.add( frame );
	}

	/**
	 * Returns whether the gui panel is open.
	 * @return
	 */
	public boolean isOpen() {
		return this.open;
	}

	/**
	 * Closes the bot panel.
	 */
	public void close() {
		this.open = false;
	}

	/**
	 * Opens the bot panel.
	 */
	public void open() {
		this.open = true;
	}

	/**
	 * Sets the notify state of the Gui.
	 */
	public void setNotification( boolean notify ) {
		this.notify = notify;
	}

	/**
	 * Returns the panel associated with this gui.
	 * @return
	 */
	public RSGuiPanel getBotPanel() {
		return this.botPanel;
	}

	public void onPaint( Graphics g ) {
		repeatDraw--;
		ticks++;

		// Draw for four frames after menu is closed
		if ( open )
			repeatDraw = 3;

		// Draw gui button
		if (this.isOpen()) {
			g.drawImage(RSGuiFrame.BUTTON_GUI_OPEN, guiBounds.x, guiBounds.y, null);
			notify = false;
		} else if (this.notify && ticks / 20 % 2 == 0) {
			g.drawImage(RSGuiFrame.BUTTON_GUI_NOTIFY, guiBounds.x, guiBounds.y, null);
		} else {
			g.drawImage(RSGuiFrame.BUTTON_GUI_NORMAL, guiBounds.x, guiBounds.y, null);
		}

		if ( open || repeatDraw > 0 ) {
			g.drawImage( RSGuiFrame.ICONS_TOP, iconsTopBounds.x, iconsTopBounds.y, null );
			g.drawImage( RSGuiFrame.ICONS_BOTTOM, iconsBottomBounds.x, iconsBottomBounds.y, null );
			g.drawImage( RSGuiFrame.INVENTORY, inventoryBounds.x, inventoryBounds.y, null );

			botPanel.paint(g);
		}

		/*
		g.setColor( Color.red );
		g.fillRect( iconsTopBounds.x, iconsTopBounds.y, iconsTopBounds.width, iconsTopBounds.height );
		g.setColor( Color.yellow );
		g.fillRect( iconsBottomBounds.x, iconsBottomBounds.y, iconsBottomBounds.width, iconsBottomBounds.height );
		g.setColor( Color.green );
		g.fillRect( inventoryBounds.x, inventoryBounds.y, inventoryBounds.width, inventoryBounds.height );
		 */

		// Draw gui icon
		if ( iconImage != null ) {
			int xx = guiBounds.x + guiBounds.width/2 - iconImage.getWidth()/2;
			int yy = guiBounds.y + guiBounds.height/2 - iconImage.getHeight()/2;
			Graphics2D g2d = (Graphics2D)g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
			g2d.drawImage( iconImage2, xx+1, yy+1, null );
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g2d.drawImage( iconImage, xx, yy, null );
		}

		// Draw frames
		for (int i = 0; i < windows.size(); i++) {
			RSGuiFrame frame = windows.get(i);
			if ( frame.isVisible() && frame.isOpen() ) {
				frame.onPaint( g );
			}
		}

		this.paint(g);
	}

	public abstract void paint(Graphics g);

	public EventBlockingOverride.OVERRIDE_RETURN keyEvent(KeyEvent arg0) {
		if ( arg0.getKeyCode() == KeyEvent.VK_ESCAPE && arg0.getID() == KeyEvent.KEY_PRESSED ) {
			this.close();
			if ( arg0.isControlDown() ) {
				this.open();
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			}
		}
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

	public EventBlockingOverride.OVERRIDE_RETURN mouseEvent(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		// Loop through each node and attempt to click
		for (int i = windows.size() - 1; i >= 0; i--) {
			RSGuiFrame node = windows.get(i);

			// If this node implements the mouse listener, click it!
			if ( node.isOpen() && node.isVisible() ) {

				EventBlockingOverride.OVERRIDE_RETURN ret = node.mouseEvent(arg0);
				if ( ret.equals( EventBlockingOverride.OVERRIDE_RETURN.DISMISS ) ) {
					return ret;
				}
			}
		}

		// Check the bot panel!
		if ( inventoryBounds.contains( x, y ) && open ) {
			if ( arg0.getButton() == 1 && arg0.getID() == 501) {
				((RSGuiMouseListener)botPanel).onMousePress(x, y);
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			} if ( arg0.getButton() == 1 && arg0.getID() == 506) {
				((RSGuiMouseListener)botPanel).onMouseDown(x, y);
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			} else {
				((RSGuiMouseListener)botPanel).onMouseUpdate(x, y);
			}
		}

		// If the left mouse button was clicked
		if ( arg0.getButton() == 1 && arg0.getID() == 501 ) {

			// If the "gui" button was pressed. Open the window!
			if ( guiBounds.contains( x, y ) ) {
				//windows.get(0).open();
				open = true;
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			}

			// If any one of the inventory icons are clicked.
			if ( iconsBottomBounds.contains(x,y) || iconsTopBounds.contains(x,y)) {
				open = false;
				return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
			}
		}

		// Otherwise, send the event to the game!
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}
}
