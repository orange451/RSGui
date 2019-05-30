package scripts.gui.backend;

import java.util.ArrayList;

public class RSGuiBox extends RSGuiPanel
{
  protected int actualWidth = 0;
  protected int actualHeight = 0;
  protected int padding = 0;
  
  public RSGuiBox(int x, int y, int width, int height) {
    super(width, height);
    setLocation(x, y);
    
    this.actualWidth = width;
    this.actualHeight = height;
  }
  
  public void setPadding(int padding) {
    this.padding = padding;
  }
  
  public int getWidth()
  {
    return this.width - this.padding * 2;
  }
  
  public int getHeight()
  {
    return this.height - this.padding * 2;
  }
  
  public boolean onMousePress(int x, int y)
  {
    for (int i = this.nodes.size() - 1; i >= 0; i--) {
      RSGuiNode node = (RSGuiNode)this.nodes.get(i);
      if (((node instanceof RSGuiMouseListener)) && (node.isVisible())) {
        boolean ret = ((RSGuiMouseListener)node).onMousePress(x - this.x - this.padding, y - this.y - this.padding);
        if (ret) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void onMouseDown(int x, int y)
  {
    for (int i = this.nodes.size() - 1; i >= 0; i--) {
      RSGuiNode node = (RSGuiNode)this.nodes.get(i);
      if (((node instanceof RSGuiMouseListener)) && (node.isVisible())) {
        ((RSGuiMouseListener)node).onMouseDown(x - this.x - this.padding, y - this.y - this.padding);
      }
    }
  }
  
  public void onMouseUpdate(int x, int y)
  {
    for (int i = 0; i < this.nodes.size(); i++) {
      RSGuiNode node = (RSGuiNode)this.nodes.get(i);
      if (((node instanceof RSGuiMouseListener)) && (node.isVisible())) {
        ((RSGuiMouseListener)node).onMouseUpdate(x - this.x - this.padding, y - this.y - this.padding);
      }
    }
  }
  
  public void paint(java.awt.Graphics g)
  {
    this.actualWidth = (this.width - this.padding * 2);
    this.actualHeight = (this.height - this.padding * 2);
    if ((this.actualWidth != this.panelImage.getWidth()) || (this.actualHeight != this.panelImage.getHeight())) {
      this.panelImage = new java.awt.image.BufferedImage(this.actualWidth, this.actualHeight, 2);
    }
    clear();
    
    for (int i = 0; i < this.nodes.size(); i++) {
      RSGuiNode node = (RSGuiNode)this.nodes.get(i);
      if (node.isVisible())
      {

        node.paint(this.panelImage.getGraphics()); }
    }
    g.drawImage(this.panelImage, this.x + this.padding, this.y + this.padding, null);
  }
}
