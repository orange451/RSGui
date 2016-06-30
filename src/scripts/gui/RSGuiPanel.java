package scripts.gui;

import java.awt.Graphics;
import java.util.ArrayList;

public class RSGuiPanel extends RSGuiNode implements RSGuiMouseListener {
	protected ArrayList<RSGuiNode> nodes = new ArrayList<RSGuiNode>();

	public RSGuiPanel( int x, int y, int width, int height ) {
		super(x, y, width, height);
	}

	public void add( RSGuiNode node ) {
		nodes.add( node );
		node.setParent( parent );
	}

	public void remove( RSGuiNode node ) {
		nodes.remove( node );
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
		g.translate(x, y);
		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			node.paint(g);
		}
		g.translate(-x, -y);
	}

}
