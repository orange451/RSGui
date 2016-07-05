package scripts.gui.test;

import java.awt.image.BufferedImage;

import scripts.gui.AwtUtil;
import scripts.gui.RSGui;
import scripts.gui.RSGuiBox;
import scripts.gui.RSGuiButton;
import scripts.gui.RSGuiCheckbox;
import scripts.gui.RSGuiFrame;
import scripts.gui.RSGuiImageLabel;
import scripts.gui.RSGuiMouseListener;
import scripts.gui.RSGuiPanel;
import scripts.gui.RSGuiPanelScroll;
import scripts.gui.RSGuiTextButton;
import scripts.gui.RSGuiTextLabel;

public class TestGui extends RSGui {
	public static final BufferedImage WOW_IMAGE = AwtUtil.getImage("scripts/gui/test/wow.png");

	public RSGuiFrame frame = new TestFrame();

	public TestGui(String icon) {
		super(icon);

		// Register this frame to our gui
		this.registerWindow( frame );


		// Get the botting panel
		RSGuiPanel panel = this.getBotPanel();


		// Add a box to the panel
		RSGuiBox box = new RSGuiBox( 0, 0, panel.getWidth(), panel.getHeight() );
		box.setPadding(4);
		panel.add(box);


		// Add some text
		box.add( new RSGuiTextLabel(0, 0, "This is your bot panel!").setShadow(true) );
		box.add( new RSGuiTextLabel(0, 16, "You can add any RSGui elements!").setShadow(true) );


		// Add some checkboxes
		box.add( new RSGuiCheckbox( 0, 112, "This is cool!" ) );
		box.add( new RSGuiCheckbox( 0, 128, "Neat!" ) );


		// Create a scrollable panel
		RSGuiPanelScroll scroll = new RSGuiPanelScroll(0, 32, box.getWidth(), 64 );
		scroll.add( new RSGuiImageLabel( 0, 0, WOW_IMAGE ) );
		box.add(scroll);


		// Create a button
		RSGuiButton button = new RSGuiButton( "Click me!" );
		button.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseDown(int x, int y) { }
			@Override public void onMouseUpdate(int x, int y) { }

			@Override
			public boolean onMousePress(int x, int y) {
				frame.open();
				return true;
			}
		});
		button.setLocation( box.getWidth()/2 - button.getWidth()/2, box.getHeight()-button.getHeight());
		box.add(button);
	}

}
