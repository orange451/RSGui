package scripts.rsgui.backend;

import java.awt.Color;

public class RSGuiOutline extends RSGuiNode
{
  private Color color = Color.black;
  private int inset = 0;
  
  public RSGuiOutline(Color color, int inset) {
    super(0, 0, 0, 0);
    
    this.color = color;
    this.inset = inset;
  }
  
  protected void paint(java.awt.Graphics g)
  {
    if (this.parent != null) {
      int offset = 0;
      if ((this.parent instanceof RSGuiPanelScroll)) {
        offset = ((RSGuiPanelScroll)this.parent).scrollY;
      }
      this.x = this.inset;
      this.y = this.inset;
      this.width = (this.parent.width - this.inset * 2);
      this.height = (this.parent.height - this.inset * 2);
      
      int padd = 0;
      if ((this.parent instanceof RSGuiBox))
        padd = ((RSGuiBox)this.parent).padding;
      this.width -= padd;
      this.height -= padd;
      
      g.setColor(this.color);
      g.drawRect(this.x, this.y + offset, this.width - 1, this.height - 1);
    }
  }
}
