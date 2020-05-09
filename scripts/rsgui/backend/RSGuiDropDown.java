package scripts.rsgui.backend;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import scripts.rsgui.font.RSFont;

public class RSGuiDropDown extends RSGuiNode implements RSGuiMouseListener {
	private List<RSGuiNode> choices = new ArrayList<>();
	private List<RSGuiDropDownListener> dropListener = new ArrayList<>();
	private String choice = "";

	private boolean open;
	private static final BufferedImage image1 = RSGuiRes.BUTTON_DROPDOWN_1;
	private static final BufferedImage image2 = RSGuiRes.BUTTON_DROPDOWN_2;

	private BufferedImage i1;

	private RSGuiPanelScroll panel;

	private RSGuiTextLabel cc;
	private int mx;
	private int my;
	private int rows = 10;

	public RSGuiDropDown(int x, int y, int width) {
		super(x, y, width, image1.getHeight() + 6);

		this.cc = new RSGuiTextLabel(3, 4, "");
		this.cc.setShadow(true);

		this.i1 = new BufferedImage(64, 64, 2);

		createPanel();
	}

	private void createPanel() {
		this.panel = new RSGuiPanelScroll(this.x, this.y, 64, 64);
		this.panel.add(new RSGuiOutline(java.awt.Color.black, 0));
		this.panel.add(new RSGuiOutline(new java.awt.Color(154, 106, 49), 1));
		this.panel.setScrollBarInset(3);

		this.panel.setBackgroundColor(RSGuiRes.BACKGROUND_COLOR);
	}

	public RSGuiTextButton addChoice(final String choice) {
		final RSGuiTextButton b = new RSGuiTextButton(3, 3, this.width - 20, this.height - 3, choice);
		b.setFont(RSGuiRes.FONT_SMALL);
		b.setColor(java.awt.Color.white);
		b.setSelectColor(java.awt.Color.red);
		b.addMouseListener(new RSGuiMouseListener() {
			public void onMouseDown(int x, int y) {
			}

			public boolean onMousePress(int x, int y) {
				setChoice(b.getText());
				RSGuiDropDown.this.close();
				for (int i = 0; i < RSGuiDropDown.this.dropListener.size(); i++) {
					((RSGuiDropDownListener) RSGuiDropDown.this.dropListener.get(i)).onChoiceChanged(choice);
				}
				return true;
			}

			public void onMouseUpdate(int x, int y) {
			}
		});
		this.panel.add(b);
		b.setSize(this.width - 32, b.getFont().getHeight());
		b.setLocation(b.x + 4, b.y + this.choices.size() * b.getHeight());

		this.choices.add(b);
		if (this.choice.equals("")) {
			this.choice = choice;
		}

		return b;
	}
	
	public void setChoice(String choice) {
		RSGuiDropDown.this.choice = choice;
	}

	public String getCurrentChoice() {
		return this.choice;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	protected void paint(Graphics g) {
		if ( this.choices.size() > 0 )
			this.panel.setSize(this.width, choices.get(0).getHeight() * this.rows + 4);
		else
			this.panel.setSize(this.width, (this.height - 4));

		if ((this.i1.getWidth() != this.width) || (this.i1.getHeight() != this.height)) {
			this.i1 = new BufferedImage(this.width, this.height, 2);
		}

		Graphics g1 = this.i1.getGraphics();
		g1.setColor(java.awt.Color.black);
		g1.fillRect(0, 0, this.width, this.height);
		g1.setColor(new java.awt.Color(154, 106, 49));
		g1.fillRect(1, 1, this.width - 2, this.height - 2);
		g1.setColor(new java.awt.Color(84, 75, 64));
		if ((!this.open) && (getBounds().contains(this.mx, this.my)))
			g1.setColor(new java.awt.Color(99, 91, 80));
		g1.fillRect(2, 2, this.width - 4, this.height - 4);

		this.cc.setText(this.choice);
		this.cc.paint(g1);

		BufferedImage img = this.open ? image2 : image1;
		g1.drawImage(img, this.width - image1.getWidth() - 3, 3, null);

		g.drawImage(this.i1, this.x, this.y, null);

		this.panel.setLocation(this.x, this.y + this.height - 1);
	}

	public void open() {
		if (!this.open) {
			((RSGuiPanel) this.parent).add(this.panel);
			this.open = true;
		}
	}

	public void close() {
		((RSGuiPanel) this.parent).remove(this.panel);
		this.open = false;
	}

	public boolean onMousePress(int x, int y) {
		if (this.open) {
			if (!this.panel.isMouseOver()) {
				close();
			}
			return true;
		}
		if (getBounds().contains(x, y)) {
			open();
			return true;
		}

		return false;
	}

	public void onMouseUpdate(int x, int y) {
		this.mx = x;
		this.my = y;
	}

	public void onMouseDown(int x, int y) {
	}

	public void removeAll() {
		this.choices.clear();
		createPanel();
		this.choice = "";
	}
	
	public int choices() {
		return this.choices.size();
	}

	public void addDropDownListener(RSGuiDropDownListener listener) {
		this.dropListener.add(listener);
	}
}
