package scripts.gui;

import java.awt.Rectangle;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceMaster;

public class PlayerGui {
	private static final int rootInterfaceID = 163;
	private static final int viewportInterfaceID = 0;
	
	private static final int rootscreenInterfaceID = 164;
	private static final int screenInterfaceID = 0;
	
	private static final int rootInventoryInterfaceID = 7;
	private static final int inventoryInterfaceID = 0;
	
	/**
	 * Returns whether the player is currently playing OSRS in fullscreen mode.
	 * @return
	 */
	public static boolean isFullscreen() {
		RSInterfaceMaster rootInterface = Interfaces.get(rootInterfaceID);
		if ( rootInterface == null )
			return false;
		
		RSInterfaceChild viewport = rootInterface.getChild(viewportInterfaceID);
		if ( viewport == null )
			return false;
		
		Rectangle bounds = viewport.getAbsoluteBounds();
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
}
