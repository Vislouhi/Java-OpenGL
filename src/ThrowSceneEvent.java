package fps;

public class ThrowSceneEvent {
	private SceneEventListener glistener;
	  public  void addEventListener(SceneEventListener listener)  {
		  glistener=listener;
	  }
	 // public synchronized void removeEventListener(SceneEventListener listener)   {
	  //  _listeners.remove(listener);
	  //}
	 
	  // call this method whenever you want to notify
	  //the event listeners of the particular event
	  public void fireEvent(SceneControlData message){
		  SceneEvent event = new SceneEvent(this,message );
	    
	   
	    	glistener.handleSceneEvent(event);
	    
	  }

}

