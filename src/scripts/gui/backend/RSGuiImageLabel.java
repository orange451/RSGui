package scripts.gui.backend;

import java.awt.image.BufferedImage;

public class RSGuiImageLabel extends RSGuiNode
{
  private BufferedImage image;
  
  public RSGuiImageLabel(int x, int y, BufferedImage image) {
    super(x, y, 0, 0);
    
    this.image = image;
    this.width = image.getWidth();
    this.height = image.getHeight();
  }
  

  protected void paint(java.awt.Graphics g)
  {
    g.drawImage(this.image, this.x, this.y, null);
  }
}
