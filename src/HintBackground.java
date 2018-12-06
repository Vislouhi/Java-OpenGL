
public class HintBackground{
  HUDItem hudItem;
  boolean active=true;
  
  public void setHudItem()
  {
   hudItem = new HUDItem();
  }
  
  public void activate(){
    this.active=true;
  }
  
   public void disactivate(){
    this.active=false;
  }
  
  public boolean isActive()
  {
  return this.active;
  }
  
  
}
