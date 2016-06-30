package scripts.gui.test;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.EventBlockingOverride;
import org.tribot.script.interfaces.Painting;

import scripts.gui.RSGuiFrame;

@ScriptManifest(authors = { "orange451" }, category = "GUI", name = "Gui Test", version = 1.00, description = "Used to test my GUI system", gameMode = 1)
public class GuiTester extends Script implements Painting,EventBlockingOverride {
	public static Script plugin;

	public RSGuiFrame gui = new TestGui1("scripts/gui/test/icon.png");

	@Override
	public void run() {
		plugin = this;
		while(true) {
			sleep( 50L );
			//
		}
	}

	public void onPaint(Graphics g) {
		gui.onPaint(g);
	}

	@Override
	public OVERRIDE_RETURN overrideKeyEvent(KeyEvent arg0) {
		return gui.keyEvent(arg0);
	}

	@Override
	public OVERRIDE_RETURN overrideMouseEvent(MouseEvent arg0) {
		return gui.mouseEvent(arg0);
	}

}
