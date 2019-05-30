package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import scripts.gui.backend.RSGuiMouseListener;
import scripts.gui.backend.RSGuiPanel;
import scripts.gui.backend.RSGuiRes;

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

	public void setLocation(int location) {
		this.location = location;
	}

	public void setIcon(URL icon) {
		this.iconImage = AwtUtil.getImage(icon);
		this.iconImage2 = AwtUtil.generateMask(this.iconImage, new Color(32, 32, 32));
	}

	public void setIconFromUrl(String icon) {
		try {
			setIcon(new URL(icon));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void setIconFromFile(String icon) {
		this.iconImage = AwtUtil.getImage(icon);
		this.iconImage2 = AwtUtil.generateMask(this.iconImage, new Color(32, 32, 32));
	}




	public RSGuiPanel getBotPanel()
	{
		return this.botPanel;
	}

	public Rectangle getBounds() {
		return new Rectangle(this.guiBounds.x, this.guiBounds.y, this.guiBounds.width, this.guiBounds.height);
	}




	public boolean isOpen()
	{
		return this.open;
	}



	public void close() {
		this.open = false;
	}



	public void open()
	{
		this.open = true;

		int x = this.botPanel.getX() + 1;
		int y = this.botPanel.getY() + 1;
		this.botPanel.onMousePress(x, y);
		this.botPanel.onMouseDown(x, y);
		this.botPanel.onMouseUpdate(x, y);
	}



	public void setNotification(boolean notify)
	{
		this.notify = notify;
	}

	public void onPaint(Graphics g) {
		this.ticks += 1;
		this.repeatDraw -= 1;
		
		this.guiBounds = RSGui.getInstance().getTabBounds(location);


		if (this.open) {
			this.repeatDraw = 10;
		}

		if (isOpen()) {
			g.drawImage(RSGuiRes.BUTTON_GUI_OPEN, this.guiBounds.x, this.guiBounds.y, null);
			this.notify = false;
		} else if ((this.notify) && (this.ticks / 20 % 2 == 0)) {
			g.drawImage(RSGuiRes.BUTTON_GUI_NOTIFY, this.guiBounds.x, this.guiBounds.y, null);
		} else {
			g.drawImage(RSGuiRes.BUTTON_GUI_NORMAL, this.guiBounds.x, this.guiBounds.y, null);
		}

		if ((this.open) || (this.repeatDraw > 5)) {
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


	public boolean onMousePress(int x, int y)
	{
		return this.botPanel.onMousePress(x, y);
	}

	public void onMouseDown(int x, int y)
	{
		this.botPanel.onMouseDown(x, y);
	}

	public void onMouseUpdate(int x, int y)
	{
		this.botPanel.onMouseUpdate(x, y);
	}
}
