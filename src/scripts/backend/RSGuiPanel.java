package scripts.gui.backend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import scripts.gui.AwtUtil;

public class RSGuiPanel extends RSGuiNode implements RSGuiMouseListener
{
	protected ArrayList<RSGuiNode> nodes = new ArrayList();
	protected BufferedImage panelImage;
	protected Color backgroundColor = null;

	public RSGuiPanel(int width, int height) {
		super(0, 0, width, height);

		this.panelImage = new BufferedImage(1, 1, 2);
	}

	public void add(RSGuiNode node) {
		this.nodes.add(node);
		node.setParent(this);

		if ((node instanceof RSGuiPanel)) {
			if (node.width == -1)
				node.width = getWidth();
			if (node.height == -1)
				node.height = getHeight();
		}
	}

	public void remove(RSGuiNode node) {
		this.nodes.remove(node);
	}

	public void setBackgroundColor(Color c) {
		this.backgroundColor = c;
	}

	public boolean onMousePress(int x, int y) {
		for (int i = this.nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = (RSGuiNode)this.nodes.get(i);
			if (((node instanceof RSGuiMouseListener)) && (node.isVisible())) {
				boolean ret = ((RSGuiMouseListener)node).onMousePress(x - this.x, y - this.y);
				if (ret) {
					return true;
				}
			}
		}
		return false;
	}

	public void onMouseDown(int x, int y) {
		for (int i = this.nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = (RSGuiNode)this.nodes.get(i);
			if (((node instanceof RSGuiMouseListener)) && (node.isVisible())) {
				((RSGuiMouseListener)node).onMouseDown(x - this.x, y - this.y);
			}
		}
	}

	public void onMouseUpdate(int x, int y) {
		for (int i = 0; i < this.nodes.size(); i++) {
			RSGuiNode node = (RSGuiNode)this.nodes.get(i);
			if (((node instanceof RSGuiMouseListener)) && (node.isVisible())) {
				((RSGuiMouseListener)node).onMouseUpdate(x - this.x, y - this.y);
			}
		}
	}

	public void paint(Graphics g) {
		if ((this.width != this.panelImage.getWidth()) || (this.height != this.panelImage.getHeight())) {
			this.panelImage = new BufferedImage(this.width, this.height, 2);
		}

		clear();
		for (int i = 0; i < this.nodes.size(); i++) {
			RSGuiNode node = (RSGuiNode)this.nodes.get(i);
			if (node.isVisible())
			{

				node.paint(this.panelImage.getGraphics()); }
		}
		g.drawImage(this.panelImage, this.x, this.y, null);
	}

	public void clear() {
		AwtUtil.clearImage(this.panelImage);

		Graphics g2 = this.panelImage.getGraphics();
		if (this.backgroundColor != null) {
			g2.setColor(this.backgroundColor);
			g2.fillRect(0, 0, this.panelImage.getWidth(), this.panelImage.getHeight());
		}
	}

	public void removeAll() {
		this.nodes.clear();
	}

	public ArrayList<RSGuiNode> getNodes() {
		return this.nodes;
	}
}
