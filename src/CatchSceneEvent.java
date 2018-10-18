package fps;

import java.util.EventObject;

public class CatchSceneEvent implements SceneEventListener{
	
	 public void handleSceneEvent(EventObject e)    {
		 
		SceneEvent ev =(SceneEvent) e;
		System.out.println(ev.toSceneControlData().getName());
	 }

}
