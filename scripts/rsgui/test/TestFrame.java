package scripts.rsgui.test;

import java.awt.Color;
import java.awt.Graphics;

import scripts.rsgui.backend.RSGuiBox;
import scripts.rsgui.backend.RSGuiButton;
import scripts.rsgui.backend.RSGuiCheckbox;
import scripts.rsgui.backend.RSGuiDropDown;
import scripts.rsgui.backend.RSGuiFrame;
import scripts.rsgui.backend.RSGuiMouseListener;
import scripts.rsgui.backend.RSGuiOutline;
import scripts.rsgui.backend.RSGuiPanel;
import scripts.rsgui.backend.RSGuiPanelScroll;
import scripts.rsgui.backend.RSGuiTextLabel;

public class TestFrame extends RSGuiFrame {
	public TestFrame() {
		super("This is a test GUI!!!");
		
		RSGuiPanel panel = new RSGuiPanel(400, 200);
		add(panel);

		RSGuiBox box = new RSGuiBox(0, 0, -1, -1);
		box.setPadding(4);
		panel.add(box);

		RSGuiTextLabel label1 = new RSGuiTextLabel(0, 0, "Dang son where'd you find that.");
		label1.setColor(Color.green);
		label1.setShadow(true);
		box.add(label1);

		RSGuiTextLabel label2 = new RSGuiTextLabel(0, 32, "This is a dropdown box:");
		label2.setShadow(true);
		box.add(label2);

		RSGuiDropDown d = new RSGuiDropDown(0, 48, 120);
		d.addChoice("Choice1");
		d.addChoice("Hello!!!");
		d.addChoice("Third choice! :)");
		box.add(d);

		RSGuiPanelScroll scroll = new RSGuiPanelScroll(box.getWidth() / 2, 0, box.getWidth() / 2, box.getHeight());
		scroll.setScrollBarInset(2);
		scroll.add(new RSGuiOutline(Color.black, 0));
		scroll.add(new RSGuiTextLabel(8, 8, "There's no text down there.").setShadow(true));
		scroll.add(new RSGuiTextLabel(8, 24, "Don't look.").setShadow(true));
		scroll.add(new RSGuiTextLabel(8, 300, "SNEAKY TEXT! O:").setShadow(true));
		box.add(scroll);

		RSGuiTextLabel label3 = new RSGuiTextLabel(0, 0, "");
		label3.setShadow(true);
		label3.setColor(Color.red);
		box.add(label3);

		RSGuiCheckbox checkbox = new RSGuiCheckbox(0, 96, "This is a checkbox!");
		box.add(checkbox);

		RSGuiButton button = new RSGuiButton("Test Button!");
		button.setLocation(0, box.getHeight() - button.getHeight());
		button.setColor(Color.LIGHT_GRAY);
		button.setSelectColor(Color.green);
		button.addMouseListener(new RSGuiMouseListener() {

			@Override
			public boolean onMousePress(int paramInt1, int paramInt2) {
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

		box.add(button);

		pack();
		center();
	}

	protected void paint(Graphics g) {
		//
	}
}
