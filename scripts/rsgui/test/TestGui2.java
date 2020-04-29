package scripts.rsgui.test;

import java.awt.Graphics;

import scripts.rsgui.RSGuiTab;
import scripts.rsgui.backend.RSGuiBox;
import scripts.rsgui.backend.RSGuiPanel;
import scripts.rsgui.backend.RSGuiTextLabel;


public class TestGui2 extends RSGuiTab {
	public TestGui2(String icon) {
		setIconFromUrl(icon);

		RSGuiPanel panel = getBotPanel();

		RSGuiBox box = new RSGuiBox(0, 0, panel.getWidth(), panel.getHeight());
		box.setPadding(4);
		panel.add(box);

		box.add(new RSGuiTextLabel(0, 0, "Blank gui panel!").setShadow(true));
	}

	public void paint(Graphics g) {
		//
	}
}
