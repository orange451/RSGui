package scripts.gui.test;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.EventBlockingOverride;
import org.tribot.script.interfaces.Painting;

import scripts.gui.RSGui;
import scripts.gui.RSGuiTab;

@ScriptManifest(authors={"orange451"}, category="GUI", name="Gui Test", version=1.0D, description="Used to test my GUI system", gameMode=1)
public class GuiTester extends Script implements Painting, EventBlockingOverride {
	public static Script plugin;
	public RSGuiTab gui1;
	public RSGuiTab gui2;

	public void run() {
		plugin = this;

		// Initialize RSGUI
		RSGui.initialize();
		
		// Add a tab
		this.gui1 = new TestGui("http://firstrecon.net/public/tribot/iconSword.png");
		RSGui.addTab(this.gui1);
		
		// Add another tab
		this.gui2 = new TestGui2("http://firstrecon.net/public/tribot/iconGear.png");
		this.gui2.setLocation(1);
		RSGui.addTab(this.gui2);

		// Infinite
		while(true) {
			sleep(50L);
		}
	}

	// Paint RSGUI
	public void onPaint(Graphics g) {
		RSGui.getInstance().onPaint(g);
	}

	// Pass key events to RSGUI
	public EventBlockingOverride.OVERRIDE_RETURN overrideKeyEvent(KeyEvent arg0) {
		return RSGui.getInstance().keyEvent(arg0);
	}

	// Pass mouse events to RSGUI
	public EventBlockingOverride.OVERRIDE_RETURN overrideMouseEvent(MouseEvent arg0) {
		return RSGui.getInstance().mouseEvent(arg0);
	}
}
