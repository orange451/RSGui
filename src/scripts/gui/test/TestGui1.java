package scripts.gui.test;

import java.awt.Color;
import java.awt.Graphics;

import scripts.gui.RSGuiButton;
import scripts.gui.RSGuiDropDown;
import scripts.gui.RSGuiFrame;
import scripts.gui.RSGuiMouseListener;
import scripts.gui.RSGuiPanel;
import scripts.gui.RSGuiPanelScroll;
import scripts.gui.RSGuiTextLabel;

public class TestGui1 extends RSGuiFrame {

	public TestGui1(String icon) {
		super( icon, "This is a test GUI!!!" );

		// Create a new panel. Put all child nodes inside this.
		RSGuiPanel panel = new RSGuiPanel( 0, 0, 400, 200 );
		this.add(panel);

		// Create a test label
		RSGuiTextLabel label1 = new RSGuiTextLabel( 2, 2, 128, 24, "Dang son where'd you find that.");
		label1.setColor(Color.green);
		label1.setShadow(true);
		panel.add(label1);


		// Create another label
		RSGuiTextLabel label2 = new RSGuiTextLabel( 2, 32, 128, 16, "This is a dropdown box:" );
		label2.setShadow(true);
		panel.add(label2);


		// Add a dropdown
		RSGuiDropDown d = new RSGuiDropDown( 2, 48, 120 );
		d.addChoice("Choice1");
		d.addChoice("Hello!!!");
		d.addChoice("Third choice! :)");
		panel.add(d);


		// Add a scrollable panel
		RSGuiPanelScroll scroll = new RSGuiPanelScroll( 200, 0, 200, 200 );
		scroll.add( new RSGuiTextLabel( 8, 8, 64, 16, "There's no text down there.").setShadow(true) );
		scroll.add( new RSGuiTextLabel( 8, 24, 64, 16, "Don't look.").setShadow(true) );
		scroll.add( new RSGuiTextLabel( 8, 300, 64, 16, "SNEAKY TEXT! O:").setShadow(true) );
		panel.add(scroll);


		// Create another label
		final RSGuiTextLabel label3 = new RSGuiTextLabel( 0, 0, 0, 0, "" );
		label3.setShadow(true);
		label3.setColor( Color.red );
		panel.add(label3);


		// Add a text button
		final RSGuiButton button = new RSGuiButton( "Test Button!" );
		button.setLocation( 4, panel.getHeight() - button.getHeight() - 4 );
		button.setColor( Color.LIGHT_GRAY );
		button.setSelectColor( Color.green );
		button.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseDown(int x, int y) {}
			@Override public void onMouseUpdate(int x, int y) {}

			@Override
			public boolean onMousePress(int x, int y) {
				label3.setLocation( button.getX() + button.getWidth() + 4, button.getY() + button.getHeight() - 16 );
				label3.setText("Button pressed!");
				return true;
			}
		});
		panel.add(button);


		// Pack frame to panel size
		this.pack();

		// Center frame on screen
		this.center();

		// Tell user theres a notification
		this.setNotification( true );
	}

	@Override
	protected void paint(Graphics g) {
		//
	}

}
