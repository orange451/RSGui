package scripts.rsgui.backend;

import java.awt.image.BufferedImage;

public class RSGuiImageButton extends RSGuiNode implements RSGuiMouseListener
{
  private BufferedImage image1;
  private BufferedImage image2;
  private BufferedImage currentImage;
  protected boolean hover;
  
  public RSGuiImageButton(int x, int y, BufferedImage img1, BufferedImage img2) {
    super(x, y, img1.getWidth(), img1.getHeight());
    this.image1 = img1;
    this.image2 = img2;
    this.currentImage = this.image1;
  }
  
  protected void paint(java.awt.Graphics g)
  {
    g.drawImage(this.currentImage, this.x, this.y, null);
  }
  
  public boolean onMousePress(int x, int y)
  {
    if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
      for (int i = 0; i < this.listeners.size(); i++) {
        ((RSGuiMouseListener)this.listeners.get(i)).onMousePress(x, y);
      }
      this.currentImage = this.image1;
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
      this.currentImage = this.image1;
    }
  }
  
  public void onMouseUpdate(int x, int y)
  {
    this.hover = getBounds().contains(x, y);
    
    this.currentImage = this.image1;
    if ((x > this.x) && (x < this.x + this.width) && (y > this.y) && (y < this.y + this.height)) {
      this.currentImage = this.image2;
    }
  }
  
  public void setImages(BufferedImage img1, BufferedImage img2) {
    this.image1 = img1;
    this.image2 = img2;
    
    this.currentImage = (this.hover ? this.image2 : this.image1);
  }
}
