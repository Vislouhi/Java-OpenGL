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

public class EditForm extends RenderForm
{
 String text;
 int availableTextSize;
 
 public void setText()
 {
 
 }
 
 public void setAvailableTextSize()
 {
 
 }
 
 public String getText()
 {
 
 }
 
}

public class Page{
 Texture texture;
 ArrayList<EditForm> editForms;
 ArrayList<TabButton> tabButtons;
 
}



