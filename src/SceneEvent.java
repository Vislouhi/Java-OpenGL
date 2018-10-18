package fps;

public class SceneEvent extends java.util.EventObject {
	 private SceneControlData message;
	
	public SceneEvent(Object source, SceneControlData message) {
        super(source);
        this.message = message;
    }
	public SceneControlData getMessage(){
	 	  return message;
	   }
	@Override
	   public String toString(){
	 	  return getClass().getName() + "[source = " + getSource() + ", message = " + message.name + "]";
	  }
	public SceneControlData toSceneControlData(){
	 	  return message;
	  }

}
