package scripts.rsgui.backend;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RSGuiCheckbox extends RSGuiNode implements RSGuiMouseListener
{
  private boolean checked;
  private RSGuiImageButton button;
  private RSGuiTextButton text;
  private static final BufferedImage BUTTON_NONE = RSGuiRes.BUTTON_CHECK_1;
  private static final BufferedImage BUTTON_CHECK = RSGuiRes.BUTTON_CHECK_2;
  
  public RSGuiCheckbox(int x, int y, String text) {
    super(x, y, 1, 1);
    
    this.button = new RSGuiImageButton(x, y, BUTTON_NONE, BUTTON_NONE);
    this.text = new RSGuiTextButton(x, y, -1, -1, text);
  }
  

  protected void paint(Graphics g)
  {
    this.button.setLocation(this.x, this.y);
    this.text.setLocation(this.x + this.button.getWidth() + 4, this.y + 1);
    

    setChecked(this.checked);
    

    this.button.paint(g);
    this.text.paint(g);
    

    this.width = (this.text.x + 4 + this.text.getWidth() - this.x);
    this.height = this.button.getHeight();
  }
  






  public RSGuiTextButton getTextButton()
  {
    return this.text;
  }
  



  public boolean isChecked()
  {
    return this.checked;
  }
  



  public void setChecked(boolean value)
  {
    this.checked = value;
    

    BufferedImage img = this.checked ? BUTTON_CHECK : BUTTON_NONE;
    this.button.setImages(img, img);
  }
  
  public boolean onMousePress(int x, int y)
  {
    if (getBounds().contains(x, y)) {
      setChecked(!this.checked);
      
      this.button.onMouseDown(x, y);
      this.text.onMouseDown(x, y);
      for (int i = 0; i < this.listeners.size(); i++) {
        ((RSGuiMouseListener)this.listeners.get(i)).onMouseDown(x, y);
      }
      return true;
    }
    
    return false;
  }
  
  public void onMouseUpdate(int x, int y)
  {
    this.button.onMouseUpdate(x, y);
    this.text.onMouseUpdate(x, y);
    
    this.text.hover = getBounds().contains(x, y);
  }
  
  public void onMouseDown(int x, int y) {}
}
