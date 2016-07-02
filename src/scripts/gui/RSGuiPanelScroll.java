package scripts.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class RSGuiPanelScroll extends RSGuiPanel {
	protected int scrollY;
	private int scrollBarInset = 0;

	private RSGuiImageButton scrollUp;
	private RSGuiImageButton scrollDown;
	private Rectangle scrollBounds    = new Rectangle();
	private Rectangle scrollBoundsBar = new Rectangle();
	private boolean hover;

	public RSGuiPanelScroll(int x, int y, int width, int height) {
		super(width, height);
		this.setLocation(x, y);

		this.panelImage = new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB );

		this.scrollUp = new RSGuiImageButton( 0, 0, RSGuiFrame.BUTTON_DROPDOWN_2, RSGuiFrame.BUTTON_DROPDOWN_2 );
		this.scrollUp.setLocation( width - this.scrollUp.getWidth() - 1, 1);
		this.scrollUp.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseUpdate(int x, int y) { }
			@Override public void onMouseDown(int x, int y) { }

			@Override
			public boolean onMousePress(int x, int y) {
				scrollY -= 32;
				return true;
			}
		});
		this.nodes.add( this.scrollUp );

		this.scrollDown = new RSGuiImageButton( 0, 0, RSGuiFrame.BUTTON_DROPDOWN_1, RSGuiFrame.BUTTON_DROPDOWN_1 );
		this.scrollDown.setLocation( width - this.scrollUp.getWidth() - 1, height - this.scrollDown.getHeight() - 1 );
		this.scrollDown.addMouseListener( new RSGuiMouseListener() {
			@Override public void onMouseUpdate(int x, int y) { }
			@Override public void onMouseDown(int x, int y) { }

			@Override
			public boolean onMousePress(int x, int y) {
				scrollY += 32;
				return true;
			}
		});
		this.nodes.add( this.scrollDown );
	}

	private int sOffset = 0;
	private boolean mouseDown = false;
	private void DO_SCROLLBAR( boolean firstClick, int xx, int yy ) {
		boolean clickedBar = scrollBoundsBar.contains(xx, yy);

		if ( !scrollBounds.contains(xx, yy) && !mouseDown )
			return;

		if ( !clickedBar && !mouseDown ) {
			int diff = yy - scrollBounds.y;
			scrollY = (int) ((diff / (float)scrollBounds.height) * panelImage.getHeight());
		} else {
			if ( firstClick )
				sOffset = yy - scrollBoundsBar.y;
			else
				mouseDown = true;

			int diff = yy - scrollBounds.y - sOffset;
			scrollY = (int) ((diff / (float)scrollBounds.height) * panelImage.getHeight());
		}
		scrollY = Math.min( Math.max(0, scrollY), panelImage.getHeight() - height );
	}

	@Override
	public boolean onMousePress(int x, int y) {
		mouseDown = false;
		int xx = x - this.x;
		int yy = y - this.y + scrollY;
		DO_SCROLLBAR( true, xx, yy );

		for (int i = nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener ) {
				boolean ret = ((RSGuiMouseListener)node).onMousePress(xx, yy);
				if ( ret ) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onMouseUpdate(int x, int y) {
		hover = getBounds().contains( x, y );

		for (int i = 0; i < nodes.size(); i++) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener ) {
				((RSGuiMouseListener)node).onMouseUpdate(x - this.x, y - this.y + scrollY);
			}
		}
	}

	@Override
	public void onMouseDown(int x, int y) {
		int xx = x - this.x;
		int yy = y - this.y + scrollY;
		DO_SCROLLBAR( false, xx, yy );

		for (int i = nodes.size() - 1; i >= 0; i--) {
			RSGuiNode node = nodes.get(i);
			if ( node instanceof RSGuiMouseListener ) {
				((RSGuiMouseListener)node).onMouseDown(xx, yy);
			}
		}
	}

	@Override
	protected void paint(Graphics g) {
		// Re-create the scrollable image if it's too small.
		RECALCULATE_PANEL_IMAGE();
		Graphics g2 = panelImage.getGraphics();
		g2.setColor( backgroundColor );
		g2.fillRect(0, 0, panelImage.getWidth(), panelImage.getHeight());
		g2.setColor( Color.white );

		// Paint all elements to me
		this.scrollUp.y   = scrollY + scrollBarInset;
		this.scrollUp.x   = width - scrollUp.width - scrollBarInset;
		this.scrollDown.y = height + scrollY - scrollBarInset - scrollDown.getHeight();
		this.scrollDown.x = width - scrollDown.width - scrollBarInset;
		for (int i = nodes.size() - 1; i >= 0; i-- ) {
			nodes.get(i).paint(g2);
		}

		// Render scrollbar BG
		int dist = this.scrollDown.getY() - (this.scrollUp.getY() + this.scrollUp.getHeight());
		int xx = scrollUp.x;
		int yy = scrollUp.y + scrollUp.height;
		g2.drawImage( RSGuiFrame.SCROLL_BAR_BG, xx, yy, scrollUp.width, dist, null );
		scrollBounds.setBounds( xx, yy, scrollUp.width, dist );

		// Render scrollbar
		float scrollBarRatio = height / (float)panelImage.getHeight();
		int dist2 = (int) Math.round((dist * scrollBarRatio) - 8);
		int off   = (int) Math.round( dist * ( scrollY / (float)panelImage.getHeight() ) );
		g2.drawImage( RSGuiFrame.SCROLL_BAR_MID, xx, yy + off + 4, scrollUp.width, dist2, null);
		g2.drawImage( RSGuiFrame.SCROLL_BAR_TOP, xx, yy + off, null);
		g2.drawImage( RSGuiFrame.SCROLL_BAR_BOT, xx, yy + off + 4 + dist2, null);
		scrollBoundsBar.setBounds( xx, yy + off, scrollUp.width, dist2 + 8 );


		// Draw scrolled image onto parent
		g.drawImage(panelImage, x, y, x+width, y+height, 0, scrollY, width, scrollY+height, null);
	}

	private void RECALCULATE_PANEL_IMAGE() {
		int ph = parent.getHeight();
		if ( y + height > ph ) {
			height = ph - y;
		}

		int hh = height;
		for (int i = nodes.size() - 1; i >= 0; i-- ) {
			RSGuiNode node = nodes.get(i);
			int yy = node.y + node.height;
			if ( yy > hh ) {
				hh = yy;
			}
		}
		if ( hh != panelImage.getHeight() ) {
			this.panelImage = new BufferedImage( width, hh, BufferedImage.TYPE_INT_ARGB );
		}

		scrollY = Math.min( Math.max(0, scrollY), panelImage.getHeight() - height );
	}

	/**
	 * Returns whether or not the mouse is hovering over the panel
	 * @return
	 */
	public boolean isMouseOver() {
		return this.hover;
	}

	/**
	 * Sets the amount of pixels the scrollbar is inset into the panel.
	 * @param inset
	 */
	public void setScrollBarInset( int inset ) {
		this.scrollBarInset = inset;
	}

}
