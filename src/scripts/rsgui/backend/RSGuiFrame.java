package scripts.rsgui.backend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.tribot.script.interfaces.EventBlockingOverride;

import scripts.rsgui.AwtUtil;
import scripts.rsgui.PlayerGui;


public abstract class RSGuiFrame extends RSGuiNode {
	private ArrayList<RSGuiNode> nodes = new ArrayList<RSGuiNode>();
	private boolean open;
	private boolean canClose = true;
	private float opacity = 1.0F;
	private RSGuiNode closeButton;
	private RSGuiTextLabel title;
	private BufferedImage frameImage;

	public RSGuiFrame(String title) {
		super(0, 0, 64, 64);
		center();

		this.closeButton = new RSGuiImageButton(0, 0, RSGuiRes.BUTTON_CLOSE1, RSGuiRes.BUTTON_CLOSE2);
		this.closeButton.addMouseListener(new RSGuiMouseListener() {
			public void onMouseUpdate(int x, int y) {}

			public void onMouseDown(int x, int y) {}

			public boolean onMousePress(int x, int y) {
				if (RSGuiFrame.this.canClose)
					RSGuiFrame.this.close();
				return true;
			}
		});
		add(this.closeButton);


		this.title = new RSGuiTextLabel(12, 11, title);
		this.title.setBold(true);
		this.title.setShadow(true);
		this.title.setCentered(true);
		this.title.setColor(new Color(255, 152, 31));
		add(this.title);
	}





	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;

		this.frameImage = new BufferedImage(width, height, 2);
	}




	public void setOpacity(float alpha)
	{
		this.opacity = alpha;
	}





	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}




	public void setCanClose(boolean b) {
		this.canClose = b;
	}

	public void center() {
		Rectangle viewport = PlayerGui.getInternalViewportInterfaceBounds();

		this.x = viewport.x + (viewport.width / 2 - this.width / 2);
		this.y = viewport.y + (viewport.height / 2 - this.height / 2);
	}

	public void open() {
		this.open = true;
	}

	public void close() {
		this.open = false;
	}

	public void pack() {
		Rectangle wn = new Rectangle(0, 0, 64, 64);
		for (int i = 0; i < this.nodes.size(); i++) {
			RSGuiNode node = (RSGuiNode)this.nodes.get(i);
			Rectangle nr = new Rectangle(node.getX(), node.getY(), node.getWidth(), node.getHeight());
			int xx = nr.x + nr.width;
			int yy = nr.y + nr.height;

			wn.width = Math.max(wn.width, xx);
			wn.height = Math.max(wn.height, yy);
		}

		setSize(wn.width + 6, wn.height + 6);
	}

	public boolean isOpen() {
		return this.open;
	}

	public void onPaint(Graphics g2) {
		if (!this.open) {
			return;
		}

		AwtUtil.clearImage(this.frameImage);
		Graphics g = this.frameImage.getGraphics();


		Color col = new Color(RSGuiRes.BACKGROUND_COLOR.getRed() / 255.0F, RSGuiRes.BACKGROUND_COLOR.getGreen() / 255.0F, RSGuiRes.BACKGROUND_COLOR.getBlue() / 255.0F, this.opacity);
		g.setColor(col);
		g.fillRect(0, 0, this.width, this.height);


		drawBorder(g);


		g.setColor(Color.white);
		paint(g);


		this.closeButton.setLocation(-7 + this.width - this.closeButton.getWidth(), 7);
		this.title.setLocation(this.width / 2, 11);


		for (int i = 0; i < this.nodes.size(); i++) {
			if (((RSGuiNode)this.nodes.get(i)).isVisible())
			{

				((RSGuiNode)this.nodes.get(i)).paint(g);
			}
		}

		g2.translate(this.x, this.y);
		g2.drawImage(this.frameImage, 0, 0, null);
		g2.translate(-this.x, -this.y);
	}





	public void add(RSGuiNode node)
	{
		if (node.getClass().equals(RSGuiPanel.class)) {
			node.setLocation(node.x + 6, node.y + 35);
		}


		this.nodes.add(node);
		node.setParent(this);


		if ((node instanceof RSGuiPanel)) {
			if (node.width == -1)
				node.width = this.width;
			if (node.height == -1) {
				node.height = this.height;
			}
		}
	}



	public void remove(RSGuiNode node)
	{
		this.nodes.remove(node);
	}

	protected abstract void paint(Graphics paramGraphics);

	private void drawBorder(Graphics g)
	{
		int tw = RSGuiRes.BORDER_LEFT.getWidth();
		for (int i = 0; i <= this.width; i += RSGuiRes.BORDER_LEFT.getWidth()) {
			g.drawImage(RSGuiRes.BORDER_TOP, i, 0, null);
			g.drawImage(RSGuiRes.BORDER_BOTTOM, i, this.height - tw, null);
			g.drawImage(RSGuiRes.BORDER_BOTTOM, i + 5, 17, null);
		}


		int th = RSGuiRes.BORDER_LEFT.getHeight();
		for (int i = 0; i <= this.height; i += RSGuiRes.BORDER_LEFT.getHeight()) {
			g.drawImage(RSGuiRes.BORDER_LEFT, 0, i, null);
			g.drawImage(RSGuiRes.BORDER_RIGHT, this.width - th, i, null);
		}


		g.drawImage(RSGuiRes.BORDER_TOP_LEFT, 0, 0, null);
		g.drawImage(RSGuiRes.BORDER_TOP_RIGHT, this.width - 18, 0, null);
		g.drawImage(RSGuiRes.BORDER_BOTTOM_RIGHT, this.width - 18, this.height - 18, null);
		g.drawImage(RSGuiRes.BORDER_BOTTOM_LEFT, 0, this.height - 18, null);
	}

	public EventBlockingOverride.OVERRIDE_RETURN keyEvent(KeyEvent arg0) {
		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}

	public EventBlockingOverride.OVERRIDE_RETURN mouseEvent(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();

		boolean isInWindow = (this.open) && (x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height);
		if (isInWindow)
		{
			for (int i = this.nodes.size() - 1; i >= 0; i--) {
				RSGuiNode node = (RSGuiNode)this.nodes.get(i);

				if (((node instanceof RSGuiMouseListener)) && (node.isVisible()))
				{



					if ((arg0.getButton() == 1) && (arg0.getID() == 501)) {
						boolean res = ((RSGuiMouseListener)node).onMousePress(x - this.x, y - this.y);


						if (res)
							return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
					}
					if ((arg0.getButton() == 1) && (arg0.getID() == 506)) {
						((RSGuiMouseListener)node).onMouseDown(x - this.x, y - this.y);
					}
					else {
						((RSGuiMouseListener)node).onMouseUpdate(x - this.x, y - this.y);
					}
				}
			}


			if (arg0.getButton() == 1) {
				return EventBlockingOverride.OVERRIDE_RETURN.DISMISS;
			}
		}


		return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
	}
}
