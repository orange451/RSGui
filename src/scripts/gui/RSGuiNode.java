package scripts.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class RSGuiNode {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected RSGuiNode parent;
	protected ArrayList<RSGuiMouseListener> listeners = new ArrayList<RSGuiMouseListener>();

	public RSGuiNode( int x, int y, int width, int height ) {
		this.setLocation( x, y );
		this.setSize( width, height );
	}

	public void addMouseListener( RSGuiMouseListener listener ) {
		listeners.add( listener );
	}

	public void setParent( RSGuiNode gui ) {
		this.parent = gui;
	}

	public Rectangle getBounds() {
		return new Rectangle( x, y, width, height );
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setLocation( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	public void setSize( int width, int height ) {
		this.width = width;
		this.height = height;
	}
	protected abstract void paint(Graphics g);
}
