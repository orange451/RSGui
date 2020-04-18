package scripts.rsgui.backend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiButton extends RSGuiNode implements RSGuiMouseListener
{
  private BufferedImage buttonImage;
  private String text;
  private Color selectColor = Color.yellow;
  private Color normalColor = Color.white;
  private boolean hover = false;
  private boolean disabled;
  
  public RSGuiButton(String text) {
    super(0, 0, 1, 1);
    
    this.text = text;
    

    this.buttonImage = new BufferedImage(this.width, this.height, 2);
    GENERATE_BUTTON();
  }
  
  private void GENERATE_BUTTON() {
    int stringWid = RSGuiRes.FONT_REGULAR.getWidth(this.text);
    this.width = (stringWid + 12);
    this.height = 30;
    
    if ((this.buttonImage == null) || (this.buttonImage.getWidth() != this.width) || (this.buttonImage.getHeight() != this.height)) {
      this.buttonImage = new BufferedImage(this.width, this.height, 2);
    }
  }
  
  protected void paint(Graphics g)
  {
    scripts.rsgui.font.RSFont f = RSGuiRes.FONT_REGULAR;
    GENERATE_BUTTON();
    

    Graphics g2 = this.buttonImage.getGraphics();
    g2.setColor(new Color(78, 69, 58));
    g2.fillRect(3, 3, this.width - 6, this.height - 6);
    
    g2.setFont(f.getFont());
    int tw = RSGuiRes.BUTTON_NORMAL_1.getWidth();
    int th = RSGuiRes.BUTTON_NORMAL_1.getHeight();
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_1, 0, 0, null);
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_3, this.width - tw, 0, null);
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_5, this.width - tw, this.height - th, null);
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_7, 0, this.height - th, null);
    
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_2, tw, 0, this.width - tw * 2, 3, null);
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_6, tw, this.height - 3, this.width - tw * 2, 3, null);
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_4, this.width - 3, th, 3, this.height - th * 2, null);
    g2.drawImage(RSGuiRes.BUTTON_NORMAL_8, 0, th, 3, this.height - th * 2, null);
    

    g2.setColor(Color.BLACK);
    g2.drawString(this.text, 7, this.height / 2 + 7);
    g2.setColor(this.hover ? this.selectColor : this.disabled ? Color.gray : this.normalColor);
    g2.drawString(this.text, 6, this.height / 2 + 6);
    

    g.drawImage(this.buttonImage, this.x, this.y, null);
  }
  




  public RSGuiButton setSelectColor(Color color)
  {
    this.selectColor = color;
    return this;
  }
  




  public RSGuiButton setColor(Color color)
  {
    this.normalColor = color;
    return this;
  }
  
  public boolean onMousePress(int x, int y)
  {
    if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
      for (int i = 0; i < this.listeners.size(); i++) {
        ((RSGuiMouseListener)this.listeners.get(i)).onMousePress(x, y);
      }
      return true;
    }
    return false;
  }
  
  public void onMouseDown(int x, int y)
  {
    if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
      for (int i = 0; i < this.listeners.size(); i++) {
        ((RSGuiMouseListener)this.listeners.get(i)).onMouseDown(x, y);
      }
    }
  }
  
  public void onMouseUpdate(int x, int y)
  {
    this.hover = getBounds().contains(x, y);
    
    if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
      for (int i = 0; i < this.listeners.size(); i++) {
        ((RSGuiMouseListener)this.listeners.get(i)).onMouseUpdate(x, y);
      }
    }
  }
  
  public String getText() {
    return this.text;
  }
  
  public void setText(String string) {
    this.text = string;
  }
  
  public void setDisabled(boolean b) {
    this.disabled = b;
  }
  
  public boolean isDisabled() {
    return this.disabled;
  }
}
