package scripts.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.script.interfaces.EventBlockingOverride;
import scripts.gui.backend.RSGuiFrame;
import scripts.gui.backend.RSGuiRes;

public class RSGui {
	private ArrayList<RSGuiFrame> windows = new ArrayList<RSGuiFrame>();
	public ArrayList<RSGuiTab> tabs = new ArrayList<RSGuiTab>();
	
	private static RSGui gui;
	protected static boolean ignoreTabInput;

	private RSGui() {
		//
	}
	
	public static void initialize() {
		gui = new RSGui();
	}

	public static RSGui getInstance() {
		return gui;
	}

	public static void registerWindow(RSGuiFrame frame) {
		gui.windows.add(frame);
	}

	public static void deregisterWindow(RSGuiFrame frame) {
		gui.windows.remove(frame);
	}

	public void onPaint(Graphics g) {
		for (int i = 0; i < gui.tabs.size(); i++) {
			RSGuiTab t = (RSGuiTab)this.tabs.get(i);
			t.onPaint(g);
		}


		for (int i = 0; i < gui.windows.size(); i++) {
			RSGuiFrame frame = (RSGuiFrame)this.windows.get(i);
			if ((frame.isVisible()) && (frame.isOpen())) {
				frame.onPaint(g);
			}
		}
	}

	public EventBlockingOverride.OVERRIDE_RETURN keyEvent(KeyEvent arg0) {
		for (int i = 0; i < this.tabs.size(); i++) {
			RSGuiTab tab = (RSGuiTab)this.tabs.get(i);
			if (tab.isOpen()) {

				// Test closing/opening tabs
				if ( !ignoreTabInput ) {
					if (arg0.getID() == 401) {
						if (isKeyCloseKey(arg0.getKeyCode())) {
							tab.close();
						}
	
						if (arg0.getKeyCode() == 192) {
							tab.open();
							return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
						}
					}
				}
			}
		}
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

	private boolean isKeyCloseKey(int keyCode) {
		switch (keyCode) {
			case 27:  return true;
			case 112:  return true;
			case 113:  return true;
			case 114:  return true;
			case 115:  return true;
			case 116:  return true;
			case 117:  return true;
			case 118:  return true;
			case 119:  return true;
			case 120:  return true;
			case 121:  return true;
			case 122:  return true;
			case 123:  return true;
		}
		return false;
	}
	
	protected TABS returnTab;

	public EventBlockingOverride.OVERRIDE_RETURN mouseEvent(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();

		boolean openWindow = false;


		for (int i = this.windows.size() - 1; i >= 0; i--) {
			RSGuiFrame node = (RSGuiFrame)this.windows.get(i);


			if ((node.isOpen()) && (node.isVisible())) {
				openWindow = true;
				EventBlockingOverride.OVERRIDE_RETURN ret = node.mouseEvent(arg0);
				if (ret.equals(EventBlockingOverride.OVERRIDE_RETURN.DISMISS)) {
					return ret;
				}
			}
		}


		for (int i = 0; i < this.tabs.size(); i++) {
			RSGuiTab tab = (RSGuiTab)this.tabs.get(i);
			if (tab.isOpen()) {
				if (getInventoryBounds().contains(x, y)) {
					if ((arg0.getButton() == 1) && (arg0.getID() == 501) && (!openWindow)) {
						tab.onMousePress(x, y);
						return EventBlockingOverride.OVERRIDE_RETURN.DISMISS; }
					if ((arg0.getID() == 506) && (!openWindow)) {
						tab.onMouseDown(x, y);
						return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
					}
					tab.onMouseUpdate(x, y);
				}
			}
		}
		
		// Handle clicking tabs
		if ((arg0.getButton() == 1) && (arg0.getID() == 501)) {
			for (int i = 0; i < this.tabs.size(); i++) {
				RSGuiTab tab = (RSGuiTab)this.tabs.get(i);

				// Close all OTHER tabs, open this one if clicked.
				if (tab.getBounds().contains(x, y)) {
					if (!openWindow) {
						for (int a = 0; a < this.tabs.size(); a++) {
							RSGuiTab tt = (RSGuiTab)this.tabs.get(a);
							tt.close();
						}
						tab.open();
					}
					return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
				}

				// If we click on the regular icons, close.
				if (((getIconsBottomBounds().contains(x, y)) || (getIconsTopBounds().contains(x, y))) && (!openWindow)) {
					returnTab = GameTab.getOpen();
					tab.close();
				}
			}
		}

		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

	public static void addTab(RSGuiTab tab) {
		gui.tabs.add(tab);
	}
	
	protected Rectangle getTabBounds(int location) {
		Rectangle temp = getIconsTopBounds();
		if ( PlayerGui.sidePanelsEnabled() ) {
			temp = getInventoryBounds();
			
			temp.x -= 7;
			temp.y -= 7;
		}
		
		if ( !PlayerGui.isFullscreen() )
			temp.x -= 6;
		
		temp.width = 33;
		temp.height = 36;
		
		temp.y += location*temp.height;
		temp.x -= temp.width;
		
		return temp;
	}

	protected Rectangle getInventoryBounds() {
		if ( !PlayerGui.isFullscreen() )
			return new Rectangle(547, 204, RSGuiRes.INVENTORY.getWidth(), RSGuiRes.INVENTORY.getHeight());
	
		return Interfaces.get(7, 0).getAbsoluteBounds();
	}
	
	protected Rectangle getIconsTopBounds() {
		if ( !PlayerGui.isFullscreen() )
			return new Rectangle(522, 168, RSGuiRes.ICONS_TOP.getWidth(), RSGuiRes.ICONS_BOTTOM.getHeight());
		
		Rectangle b = getInventoryBounds();
		
		if ( PlayerGui.sidePanelsEnabled() ) {
			b.x -= 34;
			b.y += b.height+7;
			b.width = RSGuiRes.ICONS_BOTTOM.getWidth();
			b.height = RSGuiRes.ICONS_BOTTOM.getHeight();
			
			if ( PlayerGui.inExpandedTabsMode() ) {
				b.x -= b.width-45;
			}
		} else {
			b.x -= 25;
			b.y -= 37;
			b.width = RSGuiRes.ICONS_BOTTOM.getWidth();
			b.height = RSGuiRes.ICONS_BOTTOM.getHeight();
		}
		
		return b;
	}
	
	protected Rectangle getIconsBottomBounds() {
		if ( !PlayerGui.isFullscreen() )
			return new Rectangle(522, 466, RSGuiRes.ICONS_BOTTOM.getWidth(), RSGuiRes.ICONS_BOTTOM.getHeight());

		Rectangle b = getInventoryBounds();
		
		if ( PlayerGui.sidePanelsEnabled() ) {
			b = getIconsTopBounds();
			
			if ( !PlayerGui.inExpandedTabsMode() ) {
				b.y += b.height-1;
			} else {
				b.x += b.width-45;
			}
			
		} else {
			b.x -= 25;
			b.y += b.height;
			b.width = RSGuiRes.ICONS_BOTTOM.getWidth();
			b.height = RSGuiRes.ICONS_BOTTOM.getHeight();
		}
		
		return b;
	}
}
