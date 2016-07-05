package scripts.gui.test;

import java.awt.Color;
import java.awt.Graphics;

import scripts.gui.RSGuiBox;
import scripts.gui.RSGuiButton;
import scripts.gui.RSGuiCheckbox;
import scripts.gui.RSGuiDropDown;
import scripts.gui.RSGuiFrame;
import scripts.gui.RSGuiMouseListener;
import scripts.gui.RSGuiOutline;
import scripts.gui.RSGuiPanel;
import scripts.gui.RSGuiPanelScroll;
import scripts.gui.RSGuiTextLabel;

public class TestFrame extends RSGuiFrame {

	public TestFrame() {
		super( "This is a test GUI!!!" );

		// Create a new panel. Put all child nodes inside this.
		RSGuiPanel panel = new RSGuiPanel( 400, 200 );
		this.add(panel);

		RSGuiBox box = new RSGuiBox( 0, 0, -1, -1 );
		box.setPadding(4);
		panel.add(box);

		// Create a test label
		RSGuiTextLabel label1 = new RSGuiTextLabel( 0, 0, "Dang son where'd you find that.");
		label1.setColor(Color.green);
		label1.setShadow(true);
		box.add(label1);


		// Create another label
		RSGuiTextLabel label2 = new RSGuiTextLabel( 0, 32, "This is a dropdown box:" );
		label2.setShadow(true);
		box.add(label2);


		// Add a dropdown
		RSGuiDropDown d = new RSGuiDropDown( 0, 48, 120 );
		d.addChoice("Choice1");
		d.addChoice("Hello!!!");
		d.addChoice("Third choice! :)");
		box.add(d);


		// Add a scrollable panel
		RSGuiPanelScroll scroll = new RSGuiPanelScroll( box.getWidth()/2, 0, box.getWidth()/2, box.getHeight() );
		scroll.setScrollBarInset(2);
		scroll.add( new RSGuiOutline( Color.black, 0 ) );
		scroll.add( new RSGuiTextLabel( 8, 8, "There's no text down there.").setShadow(true) );
		scroll.add( new RSGuiTextLabel( 8, 24, "Don't look.").setShadow(true) );
		scroll.add( new RSGuiTextLabel( 8, 300, "SNEAKY TEXT! O:").setShadow(true) );
		box.add(scroll);


		// Create another label
		final RSGuiTextLabel label3 = new RSGuiTextLabel( 0, 0, "" );
		label3.setShadow(true);
		label3.setColor( Color.red );
		box.add(label3);


		RSGuiCheckbox checkbox = new RSGuiCheckbox(0, 96, "This is a checkbox!");
		box.add(checkbox);


		// Add a text button
		final RSGuiButton button = new RSGuiButton( "Test Button!" );
		button.setLocation( 0, box.getHeight() - button.getHeight() );
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
		box.add(button);


		// Pack frame to panel size
		this.pack();

		// Center frame on screen
		this.center();
	}

	@Override
	protected void paint(Graphics g) {
		//
	}

}
