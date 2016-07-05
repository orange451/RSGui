package scripts.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RSGuiBox extends RSGuiPanel {
	protected int actualWidth  = 0;
	protected int actualHeight = 0;
	protected int padding = 0;

	public RSGuiBox( int x, int y, int width, int height ) {
		super(width, height);
		this.setLocation(x, y);

		this.actualWidth  = width;
		this.actualHeight = height;
	}

	public void setPadding( int padding ) {
		this.padding = padding;
	}

	@Override
	public int getWidth() {
		return width - padding*2;
	}

	@Override
	public int getHeight() {
		return height - padding*2;
	}

	@Override
	public boolean onMousePress(int x, int y) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener && node.isVisible() ) {
				boolean ret = ((RSGuiMouseListener)node).onMousePress(x - this.x - padding, y - this.y - padding);
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
			if ( node instanceof RSGuiMouseListener && node.isVisible() ) {
				((RSGuiMouseListener)node).onMouseDown(x - this.x - padding, y - this.y - padding);
			}
		}
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener && node.isVisible() ) {
				((RSGuiMouseListener)node).onMouseUpdate(x - this.x - padding, y - this.y - padding);
			}
		}
	}

	@Override
	protected void paint(Graphics g) {
		this.actualWidth = width - padding * 2;
		this.actualHeight = height - padding * 2;
		if ( actualWidth != panelImage.getWidth() || actualHeight != panelImage.getHeight() ) {
			this.panelImage = new BufferedImage( actualWidth, actualHeight, BufferedImage.TYPE_INT_ARGB );
		}
		this.clear();

		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			if ( !node.isVisible() )
				continue;

			node.paint(this.panelImage.getGraphics());
		}
		g.drawImage(panelImage, x + padding, y + padding, null);
	}

}
