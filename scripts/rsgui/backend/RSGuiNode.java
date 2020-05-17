package scripts.rsgui.backend;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public abstract class RSGuiNode {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible = true;
	protected RSGuiNode parent;
	protected List<RSGuiMouseListener> listeners = new ArrayList<RSGuiMouseListener>();

	public RSGuiNode(int x, int y, int width, int height) {
		setLocation(x, y);
		setSize(width, height);
	}

	public void addMouseListener(RSGuiMouseListener listener) {
		this.listeners.add(listener);
	}

	public void setParent(RSGuiNode gui) {
		this.parent = gui;
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, this.width, this.height);
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

	public RSGuiNode setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public void setVisible(boolean b) {
		this.visible = b;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	protected abstract void paint(java.awt.Graphics paramGraphics);
}
