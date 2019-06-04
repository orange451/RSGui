package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;

import scripts.gui.backend.RSGuiMouseListener;
import scripts.gui.backend.RSGuiPanel;
import scripts.gui.backend.RSGuiRes;
import scripts.util.misc.TabUtil;

public abstract class RSGuiTab implements RSGuiMouseListener {
	private static final int STARTX = 484;
	private static final int STARTY = 168;
	private boolean open;
	private RSGuiPanel botPanel;
	private BufferedImage iconImage;
	private BufferedImage iconImage2;
	private boolean notify;
	private int ticks = 0;
	private int repeatDraw = 0;
	private Rectangle guiBounds;
	
	private int location = 0;

	public RSGuiTab() {
		this.botPanel = new RSGuiPanel(RSGuiRes.INVENTORY.getWidth(), RSGuiRes.INVENTORY.getHeight());

		this.guiBounds = new Rectangle(484, 168, RSGuiRes.BUTTON_GUI_NORMAL.getWidth(), RSGuiRes.BUTTON_GUI_NORMAL.getHeight());
	}

	/**
	 * Sets this RSGuiTab's index location. RSGuiTabs are aligned vertically on the screen.<br>
	 * A positive number moves them downward. A negative number moves them upward.
	 * @param location
	 */
	public void setLocation(int location) {
		this.location = location;
	}

	/**
	 * Set the iucon from a URL.
	 * @param icon
	 */
	public void setIcon(URL icon) {
		this.iconImage = AwtUtil.getImage(icon);
		this.iconImage2 = AwtUtil.generateMask(this.iconImage, new Color(32, 32, 32));
	}

	/**
	 * Set the icon from a String URL. See {@link #setIcon(URL)}.
	 * @param icon
	 */
	public void setIconFromUrl(String icon) {
		try {
			setIcon(new URL(icon));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the icon from a local filepath.
	 * @param icon
	 */
	public void setIconFromFile(String icon) {
		this.iconImage = AwtUtil.getImage(icon);
		this.iconImage2 = AwtUtil.generateMask(this.iconImage, new Color(32, 32, 32));
	}

	/**
	 * Returns the root panel used for drawing to this RSGuiTab.
	 * @return
	 */
	public RSGuiPanel getBotPanel() {
		return this.botPanel;
	}

	/**
	 * Returns the bounds of the RSGuiTab. This contains it's position and size.
	 * @return
	 */
	public Rectangle getBounds() {
		return new Rectangle(this.guiBounds.x, this.guiBounds.y, this.guiBounds.width, this.guiBounds.height);
	}

	/**
	 * Returns whether the tab is currently open.
	 * @return
	 */
	public boolean isOpen() {
		return this.open;
	}

	/**
	 * Closes this RSGuiTab.
	 */
	public void close() {
		this.open = false;

		if ( PlayerGui.sidePanelsEnabled() ) {
			RSGui.ignoreTabInput = true;
			TabUtil.openTab(RSGui.getInstance().returnTab);
			RSGui.ignoreTabInput = false;
		}
	}

	/**
	 * Opens this RSGuiTab.
	 */
	public void open() {
		this.open = true;

		int x = this.botPanel.getX() + 1;
		int y = this.botPanel.getY() + 1;
		this.botPanel.onMousePress(x, y);
		this.botPanel.onMouseDown(x, y);
		this.botPanel.onMouseUpdate(x, y);
		
		// Make sure a tab is open or we get ugly drawing in side-panel mode.
		if ( PlayerGui.sidePanelsEnabled() ) {
			if (GameTab.getOpen() == null || GameTab.getOpen()==TABS.NULL) {
				RSGui.ignoreTabInput = true;
				TabUtil.openTab(TABS.OPTIONS);
				RSGui.ignoreTabInput = false;
			}
		}
	}

	/**
	 * Sets the notification flag for this RSGuiTab.
	 * @param notify
	 */
	public void setNotification(boolean notify) {
		this.notify = notify;
	}

	public void onPaint(Graphics g) {
		this.ticks += 1;
		this.repeatDraw -= 1;
		
		if ( this.open && GameTab.getOpen() == TABS.NULL ) {
			TabUtil.openTab(TABS.OPTIONS);
		}
		
		this.guiBounds = RSGui.getInstance().getTabBounds(location);
		if ( PlayerGui.sidePanelsEnabled() && GameTab.getOpen() == TABS.NULL ) {
			guiBounds.x = Game.getViewportWidth()-guiBounds.width;
		}

		if (this.open) {
			this.repeatDraw = PlayerGui.sidePanelsEnabled()?5:10;
		}

		if (isOpen()) {
			g.drawImage(RSGuiRes.BUTTON_GUI_OPEN, this.guiBounds.x, this.guiBounds.y, null);
			this.notify = false;
		} else if ((this.notify) && (this.ticks / 20 % 2 == 0)) {
			g.drawImage(RSGuiRes.BUTTON_GUI_NOTIFY, this.guiBounds.x, this.guiBounds.y, null);
		} else {
			g.drawImage(RSGuiRes.BUTTON_GUI_NORMAL, this.guiBounds.x, this.guiBounds.y, null);
		}

		if ((this.open) || (this.repeatDraw >= 2)) {
			Rectangle bottomBounds = RSGui.getInstance().getIconsBottomBounds();
			Rectangle topBounds = RSGui.getInstance().getIconsTopBounds();

			BufferedImage topGraphic = RSGuiRes.ICONS_TOP;
			BufferedImage botGraphic = RSGuiRes.ICONS_BOTTOM;
			if ( PlayerGui.sidePanelsEnabled() ) {
				topGraphic = RSGuiRes.ICONS_TOP_ALT;
				botGraphic = RSGuiRes.ICONS_BOTTOM_ALT;
			}

			g.drawImage(botGraphic, bottomBounds.x, bottomBounds.y, null);
			g.drawImage(topGraphic, topBounds.x, topBounds.y, null);
			
			// Open the return tab if we lost our tab.
			if ( !open && repeatDraw > 0 ) {
				if ( GameTab.getOpen() == null || GameTab.getOpen().equals(GameTab.TABS.NULL) ) {
					if ( RSGui.getInstance().returnTab != null && !RSGui.getInstance().returnTab.equals(GameTab.TABS.NULL)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								RSGui.ignoreTabInput = true;
								TabUtil.openTab(RSGui.getInstance().returnTab);
								RSGui.ignoreTabInput = false;
							}
						}).start();
					}
				}
			}
		}

		if ((this.open) || (this.repeatDraw > 0)) {
			BufferedImage inventoryGraphic = RSGuiRes.INVENTORY;
			if ( PlayerGui.isFullscreen() )
				inventoryGraphic = RSGuiRes.INVENTORY_FULL;
			
			Rectangle inventoryBounds = RSGui.getInstance().getInventoryBounds();
			this.botPanel.setLocation(inventoryBounds.x, inventoryBounds.y);
			
			g.drawImage(inventoryGraphic, inventoryBounds.x, inventoryBounds.y, null);
			this.botPanel.paint(g);
		}


		if (this.iconImage != null) {
			int xx = this.guiBounds.x + this.guiBounds.width / 2 - this.iconImage.getWidth() / 2;
			int yy = this.guiBounds.y + this.guiBounds.height / 2 - this.iconImage.getHeight() / 2;
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(this.iconImage2, xx + 1, yy + 1, null);
			g2d.drawImage(this.iconImage, xx, yy, null);
		}

		paint(g);
	}


	public abstract void paint(Graphics paramGraphics);


	public boolean onMousePress(int x, int y) {
		return this.botPanel.onMousePress(x, y);
	}

	public void onMouseDown(int x, int y) {
		this.botPanel.onMouseDown(x, y);
	}

	public void onMouseUpdate(int x, int y) {
		this.botPanel.onMouseUpdate(x, y);
	}
}
