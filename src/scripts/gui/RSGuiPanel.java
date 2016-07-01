package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiPanel extends RSGuiNode implements RSGuiMouseListener {
	protected ArrayList<RSGuiNode> nodes = new ArrayList<RSGuiNode>();
	protected BufferedImage panelImage;
	protected Color backgroundColor = RSGuiFrame.BACKGROUND_COLOR;

	public RSGuiPanel( int x, int y, int width, int height ) {
		super(x, y, width, height);

		this.panelImage = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );
	}

	public void add( RSGuiNode node ) {
		nodes.add( node );
		node.setParent( this );

		// If its a panel, and it does not have a defined size. Stretch it to my size!
		if ( node instanceof RSGuiPanel ) {
			if ( node.width == -1 )
				node.width = width;
			if ( node.height == -1 )
				node.height = height;
		}
	}

	public void remove( RSGuiNode node ) {
		nodes.remove( node );
	}

	public void setBackgroundColor( Color c ) {
		this.backgroundColor = c;
	}

	@Override
	public boolean onMousePress(int x, int y) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener ) {
				boolean ret = ((RSGuiMouseListener)node).onMousePress(x - this.x, y - this.y);
				if ( ret ) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onMouseDown(int x, int y) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener ) {
				((RSGuiMouseListener)node).onMouseDown(x - this.x, y - this.y);
			}
		}
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener ) {
				((RSGuiMouseListener)node).onMouseUpdate(x - this.x, y - this.y);
			}
		}
	}

	@Override
	protected void paint(Graphics g) {
		if ( width != panelImage.getWidth() || height != panelImage.getHeight() ) {
			this.panelImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
		}

		// Clear
		Graphics g2 = panelImage.getGraphics();
		g2.setColor( backgroundColor );
		g2.fillRect(0, 0, panelImage.getWidth(), panelImage.getHeight());

		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			node.paint(this.panelImage.getGraphics());
		}
		g.drawImage(panelImage, x, y, null);
	}

}
