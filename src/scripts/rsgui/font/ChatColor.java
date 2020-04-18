package scripts.rsgui.font;

import java.awt.Color;
import java.util.ArrayList;

public enum ChatColor {
	BLACK        ("1",   0,   0,   0),
	DARK_BLUE    ("2",   0,   0, 170),
	DARK_GREEN   ("3",   0, 170,   0),
	DARK_AQUA    ("4",   0, 170, 170),
	DARK_RED     ("5", 170,   0,   0),
	DARK_PURPLE  ("6", 170,   0, 170),
	GOLD         ("7", 255, 170,   0),
	GRAY         ("8", 170, 170, 170),
	DARK_GRAY    ("9",  85,  85,  85),
	BLUE         ("0",  85,  85, 255),
	GREEN        ("a",  85, 255,  85),
	AQUA         ("b",  85, 255, 255),
	RED          ("c", 255,  85,  85),
	LIGHT_PURPLE ("d", 255,  85, 255),
	YELLOW       ("e", 255, 255,  85),
	WHITE        ("f", 255, 255, 255);

	private String identifier;
	private int red;
	private int green;
	private int blue;

	private static ArrayList<ChatColor> chatColors = new ArrayList<ChatColor>();

	public static final String COLOR_CODE = "" + ((char)167);

	private ChatColor(String identifier, int red, int green, int blue) {
		this.identifier = COLOR_CODE + identifier;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public int getRed() {
		return this.red;
	}

	public int getGreen() {
		return this.green;
	}

	public int getBlue() {
		return this.blue;
	}

	public int getRGB() {
		return new Color(red, green, blue).getRGB();
	}

	public String toString() {
		return this.identifier;
	}

	public static String stripColor(String check) {
		if (check == null)
			return "";

		for (int i = 0; i < chatColors.size(); i++) {
			check = check.replaceAll(chatColors.get(i).getIdentifier(), "");
		}

		return check;
	}

	public static ChatColor getChatColor(String check) {
		for (int i = 0; i < chatColors.size(); i++) {
			if (check.equalsIgnoreCase(chatColors.get(i).toString())) {
				return chatColors.get(i);
			}
		}
		return chatColors.get(0);
	}

	static {
		for (ChatColor color : values()) {
			chatColors.add(color);
		}
	}

	public Color toColor() {
		return new Color( red, green, blue );
	}
}
