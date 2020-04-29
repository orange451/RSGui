package scripts.rsgui;

import java.awt.Point;
import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceMaster;

public class PlayerGui {
	private static final int rootInterfaceID = 163;
	private static final int viewportInterfaceID = 0;
	
	private static final int rootscreenInterfaceID = 164;
	private static final int screenInterfaceID = 0;
	
	private static final int rootInventoryInterfaceID = 182;
	private static final int inventoryInterfaceID = 0;
	
	public static Point getUpTextLocation() {
		int baseY = 13;
		if ( Game.getUptext() != null && Game.getUptext().length() > 1 && !Game.getUptext().equals("Cancel ") )
			baseY += 17;
		
		Rectangle viewport = PlayerGui.getInternalViewportInterfaceBounds();
		return new Point(viewport.x + 4, viewport.y + 4 + baseY);
	}
	
	/**
	 * Returns whether the player is currently playing OSRS in fullscreen mode.
	 * @return
	 */
	public static boolean isFullscreen() {
		Rectangle bounds = getInternalViewportInterfaceBounds();
		if ( bounds.getX() == 4 && bounds.getY() == 4 )
			return false;
		 
		return true;
	}
	
	/**
	 * Returns whether the player has Side-Panels turned on or off.<br>
	 * If the player is not in fullscreen, this will always return false.
	 * @return
	 */
	public static boolean sidePanelsEnabled() {
		if ( !isFullscreen() )
			return false;
		
		RSInterfaceMaster rootScreenInterface = Interfaces.get(rootscreenInterfaceID);
		if ( rootScreenInterface == null )
			return false;
		
		RSInterfaceChild screen = rootScreenInterface.getChild(screenInterfaceID);
		if ( screen == null )
			return false;
		
		RSInterfaceMaster rootInventory = Interfaces.get(rootInventoryInterfaceID);
		if ( rootInventory == null )
			return false;
		
		RSInterfaceChild inventory = rootInventory.getChild(inventoryInterfaceID);
		if ( inventory == null )
			return false;
		
		Rectangle bounds = inventory.getAbsoluteBounds();
		if ( bounds.getX() + bounds.getWidth()+16 < screen.getAbsoluteBounds().getWidth() )
			return false;
		
		return true;
	}
	
	/**
	 * Returns whether the player has expanded tabs enabled. Expanded tabs are active when:<br>
	 * 1) In Fullscreen<br>
	 * 2) In Side Panels Mode<br>
	 * 3) Screen width is large enough to not stack the tabs ontop of eachother.<br>
	 * @return
	 */
	public static boolean inExpandedTabsMode() {
		if ( !PlayerGui.isFullscreen() )
			return false;
		
		if ( !PlayerGui.sidePanelsEnabled() )
			return false;
		
		RSInterfaceMaster rootInventory = Interfaces.get(rootInventoryInterfaceID);
		if ( rootInventory == null )
			return false;
		
		RSInterfaceChild inventory = rootInventory.getChild(inventoryInterfaceID);
		if ( inventory == null )
			return false;
		
		Rectangle t = inventory.getAbsoluteBounds();
		if ( t.y+t.height + 60 > Game.getViewportHeight() )
			return true;
		
		return false;
	}

	/**
	 * Returns the viewport. If non fullscreen this returns {@link Game#getViewportWidth()} and {@link Game#getViewportHeight()}.<br>
	 * If in fullscreen this returns the absolte puts of: Interface(122,0).
	 * @return 
	 */
	public static Rectangle getInternalViewportInterfaceBounds() {
		Rectangle normal = new Rectangle(4,4,Game.getViewportWidth(),Game.getViewportHeight());
		
		RSInterfaceMaster rootInterface = Interfaces.get(rootInterfaceID);
		if ( rootInterface == null )
			return normal;
		
		RSInterfaceChild viewport = rootInterface.getChild(viewportInterfaceID);
		if ( viewport == null )
			return normal;
		
		Rectangle r = viewport.getAbsoluteBounds();
		if ( r.getX() == 0 )
			r.height -= 165; // Chatbox height
		
		return r;
	}
}
