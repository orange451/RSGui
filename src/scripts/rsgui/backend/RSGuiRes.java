package scripts.rsgui.backend;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import org.tribot.api.General;

import scripts.rsgui.AwtUtil;
import scripts.rsgui.font.RSFont;

public class RSGuiRes
{
	static
	{
		General.println("Initializing RSGui resources");
	}

	public static final RSFont FONT_BOLD = new RSFont(AwtUtil.getFont(getURL("https://firstrecon.net/public/tribot/textBold.ttf")));
	public static final RSFont FONT_REGULAR = new RSFont(AwtUtil.getFont(getURL("https://firstrecon.net/public/tribot/textRegular.ttf")));

	public static final Color BACKGROUND_COLOR = new Color(73, 64, 52);
	public static final Color BACKGROUND_COLOR_LIGHT = new Color(82, 73, 62);

	public static final BufferedImage SPRITESHEET = AwtUtil.getImage(getURL("https://firstrecon.net/public/tribot/spritesheet.png"));

	public static final BufferedImage BORDER_TOP_LEFT = AwtUtil.getImage(SPRITESHEET, 265, 0, 18, 18);
	public static final BufferedImage BORDER_TOP = AwtUtil.getImage(SPRITESHEET, 284, 0, 18, 7);
	public static final BufferedImage BORDER_TOP_RIGHT = AwtUtil.getImage(SPRITESHEET, 303, 0, 18, 18);
	public static final BufferedImage BORDER_RIGHT = AwtUtil.getImage(SPRITESHEET, 303, 19, 18, 18);
	public static final BufferedImage BORDER_BOTTOM_RIGHT = AwtUtil.getImage(SPRITESHEET, 303, 38, 18, 18);
	public static final BufferedImage BORDER_BOTTOM = AwtUtil.getImage(SPRITESHEET, 284, 49, 18, 7);
	public static final BufferedImage BORDER_SEPARATE = AwtUtil.getImage(SPRITESHEET, 284, 57, 18, 6);
	public static final BufferedImage BORDER_BOTTOM_LEFT = AwtUtil.getImage(SPRITESHEET, 265, 38, 18, 18);
	public static final BufferedImage BORDER_LEFT = AwtUtil.getImage(SPRITESHEET, 265, 19, 18, 18);
	
	public static final BufferedImage BORDER_TOP_LEFT_NEW = AwtUtil.getImage(SPRITESHEET, 265+94, 0, 18, 18);
	public static final BufferedImage BORDER_TOP_NEW = AwtUtil.getImage(SPRITESHEET, 284+94, 0, 18, 7);
	public static final BufferedImage BORDER_TOP_RIGHT_NEW = AwtUtil.getImage(SPRITESHEET, 303+94, 0, 18, 18);
	public static final BufferedImage BORDER_RIGHT_NEW = AwtUtil.getImage(SPRITESHEET, 303+94, 19, 18, 18);
	public static final BufferedImage BORDER_BOTTOM_RIGHT_NEW = AwtUtil.getImage(SPRITESHEET, 303+94, 38, 18, 18);
	public static final BufferedImage BORDER_BOTTOM_NEW = AwtUtil.getImage(SPRITESHEET, 284+94, 49, 18, 7);
	public static final BufferedImage BORDER_SEPARATE_NEW = AwtUtil.getImage(SPRITESHEET, 284+94, 57, 18, 6);
	public static final BufferedImage BORDER_BOTTOM_LEFT_NEW = AwtUtil.getImage(SPRITESHEET, 265+94, 38, 18, 18);
	public static final BufferedImage BORDER_LEFT_NEW = AwtUtil.getImage(SPRITESHEET, 265+94, 19, 18, 18);

	public static final BufferedImage BUTTON_CLOSE1 = AwtUtil.getImage(SPRITESHEET, 190, 173, 21, 21);
	public static final BufferedImage BUTTON_CLOSE2 = AwtUtil.getImage(SPRITESHEET, 190, 195, 21, 21);
	public static final BufferedImage BUTTON_CLOSE1_NEW = AwtUtil.getImage(SPRITESHEET, 190+22, 173, 21, 21);
	public static final BufferedImage BUTTON_CLOSE2_NEW = AwtUtil.getImage(SPRITESHEET, 190+22, 195, 21, 21);
	
	public static final BufferedImage BUTTON_GUI_NORMAL = AwtUtil.getImage(SPRITESHEET, 190, 36, 33, 36);
	public static final BufferedImage BUTTON_GUI_OPEN = AwtUtil.getImage(SPRITESHEET, 190, 72, 33, 36);
	public static final BufferedImage BUTTON_GUI_NOTIFY = AwtUtil.getImage(SPRITESHEET, 190, 108, 33, 36);
	public static final BufferedImage BUTTON_DROPDOWN_1 = AwtUtil.getImage(SPRITESHEET, 190, 144, 16, 16);
	public static final BufferedImage BUTTON_DROPDOWN_2 = AwtUtil.getImage(SPRITESHEET, 206, 144, 16, 15);
	public static final BufferedImage BUTTON_NORMAL_1 = AwtUtil.getImage(SPRITESHEET, 243, 0, 9, 9);
	public static final BufferedImage BUTTON_NORMAL_2 = AwtUtil.getImage(SPRITESHEET, 253, 0, 1, 3);
	public static final BufferedImage BUTTON_NORMAL_3 = AwtUtil.getImage(SPRITESHEET, 255, 0, 9, 9);
	public static final BufferedImage BUTTON_NORMAL_4 = AwtUtil.getImage(SPRITESHEET, 261, 10, 3, 1);
	public static final BufferedImage BUTTON_NORMAL_5 = AwtUtil.getImage(SPRITESHEET, 255, 12, 9, 9);
	public static final BufferedImage BUTTON_NORMAL_6 = AwtUtil.getImage(SPRITESHEET, 253, 18, 1, 3);
	public static final BufferedImage BUTTON_NORMAL_7 = AwtUtil.getImage(SPRITESHEET, 243, 12, 9, 9);
	public static final BufferedImage BUTTON_NORMAL_8 = AwtUtil.getImage(SPRITESHEET, 243, 10, 3, 1);

	public static final BufferedImage SCROLL_BAR_TOP = AwtUtil.getImage(SPRITESHEET, 190, 161, 16, 4);
	public static final BufferedImage SCROLL_BAR_MID = AwtUtil.getImage(SPRITESHEET, 190, 166, 16, 1);
	public static final BufferedImage SCROLL_BAR_BOT = AwtUtil.getImage(SPRITESHEET, 190, 168, 16, 4);
	public static final BufferedImage SCROLL_BAR_BG  = AwtUtil.getImage(SPRITESHEET, 207, 166, 16, 1);

	public static final BufferedImage BUTTON_CHECK_1 = AwtUtil.getImage(SPRITESHEET, 223, 36, 15, 15);
	public static final BufferedImage BUTTON_CHECK_2 = AwtUtil.getImage(SPRITESHEET, 223, 51, 15, 15);

	public static final BufferedImage BUTTON_TOGGLE_1 = AwtUtil.getImage(SPRITESHEET, 322, 0, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_2 = AwtUtil.getImage(SPRITESHEET, 328, 0, 24, 5);
	public static final BufferedImage BUTTON_TOGGLE_3 = AwtUtil.getImage(SPRITESHEET, 353, 0, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_4 = AwtUtil.getImage(SPRITESHEET, 353, 6, 5, 24);
	public static final BufferedImage BUTTON_TOGGLE_5 = AwtUtil.getImage(SPRITESHEET, 353, 31, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_6 = AwtUtil.getImage(SPRITESHEET, 328, 31, 24, 5);
	public static final BufferedImage BUTTON_TOGGLE_7 = AwtUtil.getImage(SPRITESHEET, 322, 31, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_8 = AwtUtil.getImage(SPRITESHEET, 322, 6, 5, 24);
	public static final BufferedImage BUTTON_TOGGLE_9 = AwtUtil.getImage(SPRITESHEET, 328, 6, 24, 24);
	public static final BufferedImage BUTTON_TOGGLE_S_1 = AwtUtil.getImage(SPRITESHEET, 322, 37, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_S_2 = AwtUtil.getImage(SPRITESHEET, 328, 37, 24, 5);
	public static final BufferedImage BUTTON_TOGGLE_S_3 = AwtUtil.getImage(SPRITESHEET, 353, 37, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_S_4 = AwtUtil.getImage(SPRITESHEET, 353, 43, 5, 24);
	public static final BufferedImage BUTTON_TOGGLE_S_5 = AwtUtil.getImage(SPRITESHEET, 353, 68, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_S_6 = AwtUtil.getImage(SPRITESHEET, 328, 68, 24, 5);
	public static final BufferedImage BUTTON_TOGGLE_S_7 = AwtUtil.getImage(SPRITESHEET, 322, 68, 5, 5);
	public static final BufferedImage BUTTON_TOGGLE_S_8 = AwtUtil.getImage(SPRITESHEET, 322, 43, 5, 24);
	public static final BufferedImage BUTTON_TOGGLE_S_9 = AwtUtil.getImage(SPRITESHEET, 328, 43, 24, 24);

	public static final BufferedImage ICONS_TOP = AwtUtil.getImage(SPRITESHEET, 0, 0, 243, 36);
	public static final BufferedImage ICONS_TOP_ALT = AwtUtil.getImage(SPRITESHEET, 0, 336, 231, 36);

	public static final BufferedImage ICONS_BOTTOM = AwtUtil.getImage(SPRITESHEET, 0, 298, 243, 37);
	public static final BufferedImage ICONS_BOTTOM_ALT = AwtUtil.getImage(SPRITESHEET, 0, 372, 231, 36);

	public static final BufferedImage INVENTORY = AwtUtil.getImage(SPRITESHEET, 0, 36, 190, 261);
	public static final BufferedImage INVENTORY_FULL = AwtUtil.getImage(SPRITESHEET, 242, 74, 190, 261);

	private static URL getURL(String string) {
		try {
			return new URL(string);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
