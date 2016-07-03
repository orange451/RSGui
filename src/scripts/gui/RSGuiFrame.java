package scripts.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.tribot.api2007.Game;
import org.tribot.script.interfaces.EventBlockingOverride;

import scripts.gui.font.RSFont;

public abstract class RSGuiFrame extends RSGuiNode {
	public static final RSFont FONT_BOLD    = new RSFont( AwtUtil.getFont("textBold.ttf") );
	public static final RSFont FONT_REGULAR = new RSFont( AwtUtil.getFont("textRegular.ttf") );
	protected static final Color BACKGROUND_COLOR = new Color( 73, 64, 52 );
	protected static final BufferedImage BORDER_LEFT   = AwtUtil.getImage("scripts/gui/res/spr_window_border_7.png");
	protected static final BufferedImage BORDER_RIGHT  = AwtUtil.getImage("scripts/gui/res/spr_window_border_3.png");
	protected static final BufferedImage BORDER_TOP    = AwtUtil.getImage("scripts/gui/res/spr_window_border_1.png");
	protected static final BufferedImage BORDER_BOTTOM = AwtUtil.getImage("scripts/gui/res/spr_window_border_5.png");
	protected static final BufferedImage BORDER_TOP_LEFT     = AwtUtil.getImage("scripts/gui/res/spr_window_border_0.png");
	protected static final BufferedImage BORDER_TOP_RIGHT    = AwtUtil.getImage("scripts/gui/res/spr_window_border_2.png");
	protected static final BufferedImage BORDER_BOTTOM_RIGHT = AwtUtil.getImage("scripts/gui/res/spr_window_border_4.png");
	protected static final BufferedImage BORDER_BOTTOM_LEFT  = AwtUtil.getImage("scripts/gui/res/spr_window_border_6.png");
	protected static final BufferedImage BUTTON_CLOSE1 = AwtUtil.getImage("scripts/gui/res/spr_button_close_0.png");
	protected static final BufferedImage BUTTON_CLOSE2 = AwtUtil.getImage("scripts/gui/res/spr_button_close_1.png");
	protected static final BufferedImage BUTTON_GUI_NORMAL = AwtUtil.getImage("scripts/gui/res/button1.png");
	protected static final BufferedImage BUTTON_GUI_OPEN   = AwtUtil.getImage("scripts/gui/res/button2.png");
	protected static final BufferedImage BUTTON_GUI_NOTIFY = AwtUtil.getImage("scripts/gui/res/button3.png");
	protected static final BufferedImage BUTTON_DROPDOWN_1 = AwtUtil.getImage("scripts/gui/res/dropdown1.png");
	protected static final BufferedImage BUTTON_DROPDOWN_2 = AwtUtil.getImage("scripts/gui/res/dropdown2.png");
	protected static final BufferedImage BUTTON_NORMAL_1 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c1.png");
	protected static final BufferedImage BUTTON_NORMAL_2 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c2.png");
	protected static final BufferedImage BUTTON_NORMAL_3 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c3.png");
	protected static final BufferedImage BUTTON_NORMAL_4 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c4.png");
	protected static final BufferedImage BUTTON_NORMAL_5 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c5.png");
	protected static final BufferedImage BUTTON_NORMAL_6 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c6.png");
	protected static final BufferedImage BUTTON_NORMAL_7 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c7.png");
	protected static final BufferedImage BUTTON_NORMAL_8 = AwtUtil.getImage("scripts/gui/res/spr_button_n_c8.png");
	protected static final BufferedImage SCROLL_BAR_TOP = AwtUtil.getImage("scripts/gui/res/gui_scrollbar_1.png");
	protected static final BufferedImage SCROLL_BAR_MID = AwtUtil.getImage("scripts/gui/res/gui_scrollbar_2.png");
	protected static final BufferedImage SCROLL_BAR_BOT = AwtUtil.getImage("scripts/gui/res/gui_scrollbar_3.png");
	protected static final BufferedImage SCROLL_BAR_BG  = AwtUtil.getImage("scripts/gui/res/gui_scrollbar_4.png");
	protected static final BufferedImage BUTTON_CHECK_1  = AwtUtil.getImage("scripts/gui/res/checkbox1.png");
	protected static final BufferedImage BUTTON_CHECK_2  = AwtUtil.getImage("scripts/gui/res/checkbox2.png");

	private ArrayList<RSGuiNode> nodes = new ArrayList<RSGuiNode>();
	private boolean open;
	private float opacity = 1.0f;
	private boolean notify;

	private RSGuiNode closeButton;
	private RSGuiTextLabel title;
	private BufferedImage frameImage;
	private BufferedImage iconImage;
	private int ticks = 0;

	public RSGuiFrame( String icon, String title ) {
		super( 0, 0, 64, 64 );
		this.center();

		// Add close button
		this.closeButton = new RSGuiImageButton( 0, 0, BUTTON_CLOSE1, BUTTON_CLOSE2 );
		this.closeButton.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseUpdate(int x, int y) { }
			@Override public void onMouseDown(int x, int y) { }

			@Override
			public boolean onMousePress(int x, int y) {
				close();
				return true;
			}
		});
		this.add( this.closeButton );

		// Add title label
		this.title = new RSGuiTextLabel( 12, 11, width, 128, title);
		this.title.setBold( true );
		this.title.setShadow( true );
		this.title.setCentered( true );
		this.title.setColor( new Color( 255, 152, 31 ) );
		this.add(this.title);

		// Load icon
		if ( icon != null )
			this.iconImage = AwtUtil.getImage(icon);
	}

	/**
	 * Sets the size of the window
	 * @param width
	 * @param height
	 */
	public void setSize( int width, int height ) {
		this.width  = width;
		this.height = height;

		this.frameImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
	}

	/**
	 * Sets the opacity of the window. Float value between (0-1).
	 * @param alpha
	 */
	public void setOpacity( float alpha ) {
		this.opacity = alpha;
	}

	/**
	 * Sets the location of the window relative to the top left corner of the runescape client window.
	 * @param x
	 * @param y
	 */
	public void setLocation( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Centers the location of the window relative to the games viewport
	 */
	public void center() {
		int vw = Game.getViewportWidth();
		int vh = Game.getViewportHeight();

		x = 3 + vw / 2 - width / 2;
		y = 3 + vh / 2 - height / 2;
	}

	/**
	 * Opens the window.
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
	 * Closes the window.
	 */
	public void close() {
		this.open = false;
	}

	/**
	 * Packs the frame to the size of its components
	 */
	public void pack() {
		Rectangle wn = new Rectangle( 0, 0, 64, 64 );
		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			Rectangle nr = new Rectangle( node.x, node.y, node.width, node.height );
			int xx = nr.x + nr.width;
			int yy = nr.y + nr.height;

			wn.width = Math.max(wn.width, xx);
			wn.height = Math.max(wn.height, yy);
		}

		this.setSize( wn.width + 6, wn.height + 6 );
	}

	/**
	 * Returns whether or not the window is open.
	 * @return
	 */
	public boolean isOpen() {
		return this.open;
	}

	public void onPaint( Graphics g2 ) {
		ticks++;

		// Draw gui button
		if (this.open) {
			g2.drawImage(BUTTON_GUI_OPEN, 484, 170, null);
		} else if (this.notify && ticks / 20 % 2 == 0) {
			g2.drawImage(BUTTON_GUI_NOTIFY, 484, 170, null);
		} else {
			g2.drawImage(BUTTON_GUI_NORMAL, 484, 170, null);
		}

		// Draw gui icon
		if ( iconImage != null ) {
			int xx = 484 + 16 - iconImage.getWidth()/2;
			int yy = 170 + 18 - iconImage.getHeight()/2;
			g2.drawImage( iconImage, xx, yy, null );
		}

		// If frame is not open, do not draw.
		if ( !open )
			return;
		notify = false;

		// Draw to frame
		AwtUtil.clearImage(frameImage);
		Graphics g = frameImage.getGraphics();

		// Draw background
		Color col = new Color( BACKGROUND_COLOR.getRed() / 255f, BACKGROUND_COLOR.getGreen() / 255f, BACKGROUND_COLOR.getBlue() / 255f, opacity );
		g.setColor( col );
		g.fillRect(0, 0, width, height );

		// Draw the border
		drawBorder( g );

		// Paint frame
		g.setColor( Color.white );
		paint(g);

		// Reposition some nodes
		closeButton.setLocation(-7 + width - closeButton.getWidth(), 7);
		title.setLocation(width/2, 11);

		// Draw child nodes
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).paint(g);
		}

		// Draw frame to screen
		g2.translate(x, y);
		g2.drawImage(frameImage, 0, 0, null);
		g2.translate(-x, -y);
	}

	public void add( RSGuiNode node ) {
		// Force the panel to fit inside the frame below title bar.
		if ( node.getClass().equals(RSGuiPanel.class) ) {
			node.setLocation(node.x + 6, node.y + 35);
		}

		// Add it to frame
		nodes.add( node );
		node.setParent( this );

		// If its a panel, and it does not have a defined size. Stretch it to my size!
		if ( node instanceof RSGuiPanel ) {
			if ( node.width == -1 )
				node.width = width;
			if ( node.height == -1 )
				node.height = height;
		}
	}

	public void remove( RSGuiNode node ) {
		nodes.remove( node );
	}

	protected abstract void paint( Graphics g );

	private void drawBorder( Graphics g ) {
		// Draw border horizontal
		int tw = BORDER_LEFT.getWidth();
		for (int i = 0; i <= width; i+= BORDER_LEFT.getWidth() ) {
			g.drawImage(BORDER_TOP,    i, 0,           null);
			g.drawImage(BORDER_BOTTOM, i, height - tw, null);
			g.drawImage(BORDER_BOTTOM, i + 5, 17, null);
		}

		// Draw border vertical
		int th = BORDER_LEFT.getHeight();
		for (int i = 0; i <= height; i+= BORDER_LEFT.getHeight() ) {
			g.drawImage(BORDER_LEFT,  0,          i, null);
			g.drawImage(BORDER_RIGHT, width - th, i, null);
		}

		// Draw corners
		g.drawImage(BORDER_TOP_LEFT,     0,          0, null );
		g.drawImage(BORDER_TOP_RIGHT,    width - 18, 0, null );
		g.drawImage(BORDER_BOTTOM_RIGHT, width - 18, height - 18, null );
		g.drawImage(BORDER_BOTTOM_LEFT,  0,          height - 18, null );
	}

	public EventBlockingOverride.OVERRIDE_RETURN keyEvent(KeyEvent arg0) {
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

	public EventBlockingOverride.OVERRIDE_RETURN mouseEvent(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();

		boolean isInWindow = open && (x > this.x && x < this.x + width && y > this.y && y < this.y + height);
		boolean isOnGuiButton = x > 484 && x < 484 + 33 && y > 170 && y < 170 + 36;
		if ( isInWindow ) {
			// Loop through each node and attempt to click
			for (int i = nodes.size() - 1; i >= 0; i--) {
				RSGuiNode node = nodes.get(i);
				// If this node implements the mouse listener, click it!
				if ( node instanceof RSGuiMouseListener ) {
					//GuiTester.plugin.println("Modifiers: " + arg0.getModifiers());
					//GuiTester.plugin.println("ID: " + arg0.getID());

					// If the left mouse was pressed down
					if ( arg0.getButton() == 1 && arg0.getID() == 501) {
						boolean res = ((RSGuiMouseListener)node).onMousePress(x - this.x, y - this.y);

						// If a node returned true for clicking, dismiss this event.
						if ( res ) {
							return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
						}
					} if ( arg0.getButton() == 1 && arg0.getID() == 506) {
						((RSGuiMouseListener)node).onMouseDown(x - this.x, y - this.y);
					} else {
						// No button was pressed. Apply mouse update to nodes.
						((RSGuiMouseListener)node).onMouseUpdate(x - this.x, y - this.y);
					}
				}
			}

			// If the left mouse is pressed in window. Do not pass it to the game
			if ( arg0.getButton() == 1 ) {
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			}

		// If the "gui" button was pressed. Open the window!
		} else if ( isOnGuiButton && arg0.getButton() == 1 ) {
			this.open();
			return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
		}

		// Otherwise, send the event to the game!
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}
}
