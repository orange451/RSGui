package scripts.gui.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import scripts.gui.AwtUtil;
import scripts.gui.RSGui;
import scripts.gui.RSGuiTab;
import scripts.gui.backend.RSGuiBox;
import scripts.gui.backend.RSGuiButton;
import scripts.gui.backend.RSGuiCheckbox;
import scripts.gui.backend.RSGuiFrame;
import scripts.gui.backend.RSGuiImageLabel;
import scripts.gui.backend.RSGuiMouseListener;
import scripts.gui.backend.RSGuiPanel;
import scripts.gui.backend.RSGuiPanelScroll;
import scripts.gui.backend.RSGuiTextLabel;

public class TestGui extends RSGuiTab {
	public static final BufferedImage WOW_IMAGE = AwtUtil.getImage("scripts/gui/test/wow.png");

	public RSGuiFrame frame = new TestFrame();

	public TestGui(String icon) {
		setIconFromUrl(icon);

		RSGui.registerWindow(this.frame);

		RSGuiPanel panel = getBotPanel();

		RSGuiBox box = new RSGuiBox(0, 0, panel.getWidth(), panel.getHeight());
		box.setPadding(4);
		panel.add(box);

		box.add(new RSGuiTextLabel(0, 0, "This is your bot panel!").setShadow(true));
		box.add(new RSGuiTextLabel(0, 16, "You can add any RSGui elements!").setShadow(true));

		box.add(new RSGuiCheckbox(0, 112, "This is cool!"));
		box.add(new RSGuiCheckbox(0, 128, "Neat!"));

		RSGuiPanelScroll scroll = new RSGuiPanelScroll(0, 32, box.getWidth(), 64);
		scroll.add(new RSGuiImageLabel(0, 0, WOW_IMAGE));
		box.add(scroll);

		RSGuiButton button = new RSGuiButton("Click me!");
		button.addMouseListener(new RSGuiMouseListener() {
			@Override
			public boolean onMousePress(int paramInt1, int paramInt2) {
				frame.open();
				return true;
			}

			@Override
			public void onMouseDown(int paramInt1, int paramInt2) {
				//
			}

			@Override
			public void onMouseUpdate(int paramInt1, int paramInt2) {
				//
			}
		});

		button.setLocation(box.getWidth() / 2 - button.getWidth() / 2, box.getHeight() - button.getHeight());
		box.add(button);
	}

	public void paint(Graphics g) {
		//
	}
}
