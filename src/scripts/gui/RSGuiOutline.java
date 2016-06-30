package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;

public class RSGuiOutline extends RSGuiNode {
	private Color color = Color.black;
	private int inset = 0;

	public RSGuiOutline( Color color, int inset ) {
		super(0, 0, 0, 0);

		this.color = color;
		this.inset = inset;
	}

	@Override
	protected void paint(Graphics g) {
		if ( this.parent != null ) {
			int offset = 0;
			if ( parent instanceof RSGuiPanelScroll ) {
				offset = ((RSGuiPanelScroll)parent).scrollY;
			}
			this.x = inset;
			this.y = inset;
			this.width = parent.width - inset * 2;
			this.height = parent.height - inset * 2;

			g.setColor( color );
			g.drawRect( x, y + offset, width - 1, height - 1 );
		}
	}

}
