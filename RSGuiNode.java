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

	/**
	 * This function sets the parent node of this node.
	 * @param gui
	 */
	public void setParent( RSGuiNode gui ) {
		this.parent = gui;
	}

	/**
	 * This function returns a Rectangle that represents the bounds of the current node.
	 * @return
	 */
	public Rectangle getBounds() {
		return new Rectangle( x, y, width, height );
	}

	/**
	 * This function returns the current x position relative to the parent node.
	 * @return
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * This function returns the current y position relative to the parent node.
	 * @return
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * This function returns the width of the current node.
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * This function returns the height of the current node.
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * This function sets the x and y location relative to the parent node.
	 * @param x
	 * @param y
	 */
	public void setLocation( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * This function sets the size of the node.
	 * @param width
	 * @param height
	 */
	public void setSize( int width, int height ) {
		this.width = width;
		this.height = height;
	}
	protected abstract void paint(Graphics g);
}
