package fps;

import java.util.ArrayList;
import java.util.List;

public class ThrowSceneEvent {
	private SceneEventListener glistener;
	private List<SceneEventListener> listeners = new ArrayList<SceneEventListener>();
	  public  void addEventListener(SceneEventListener listener)  {
		  //glistener=listener;
		  listeners.add(listener);
	  }
	 
	  public void fireEvent(SceneControlData message){
		  SceneEvent event = new SceneEvent(this,message );
		 
		  
		  for (SceneEventListener hl : listeners) {
	            hl.handleSceneEvent(event);
	    }
	   
	    	
	    
	  }

}

