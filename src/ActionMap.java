public class ActionMap{

 ArrayList<Action> actionList;
 Tab tab;
 
 public void addAction(Color color,Action action)
 {
 
 }
 
 public Action getAction(Color color)
 {
 
 }
 
}
 public class Action{
 String actionType ="";//Может принимать значения button, edit.
 
 EditForm editForm;
 TabButton tabButton;
 Tab tab;
  
  public Action(Tab tab)
  {
  
  }
  
  
  private void setActionColor(Color color)
  {
  
  }
  
  private void setActionType(String actionType){
   
  }
  
  private void performAction()
  {
   //В зависимости от значения type
    tab.executeButtonScript();
    tab.focusOnEditForm();
    
  }
 }

public class EditForm extends RenderForm implements SceneEventListener
{
 String text;
 int availableTextSize;
 boolean focus;
 Texture texture;
 
 public void setText()
 {
  texture.updateWithText(text);
 }
 
 public void setAvailableTextSize(int availableTextSize)
 {
 
 }
 
 public String getText()
 {
 
 }
 
 public void focusOn()
 {
 
 }
 
  public void focusOff()
 {
 
 }
 
 
 
 public void handleSceneEvent(EventObject e){
   //Здесь принимаем нажатие клавиши, апдейтим переменную текст
   setText();
 }
 
}

public class TabButton extends RenderForm implements SceneEventListener
{
  Texture texture;
  Color visualMask;

public void handleSceneEvent(EventObject e){
   if (e.lmbDown)
   {
     this.onPressVisualMask=true;
   }
   
   if (e.lmbDown)
   {
    this.onPressVisualMask=false;
   }
   
 }
}

public class Page{
 Texture texture;
 ArrayList<EditForm> editForms;
 ArrayList<TabButton> tabButtons;
 
}



