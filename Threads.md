class Camera extends Thread implements SceneEventListener
{
public float currentVelocity;
public float velocity;
public float MAX_VELOCITY = 1f;
public float ACCELERATION = 0.05f;
public Vector3d direction;
public Camera()
{
  velocity = 0;
  currentVelocity=0;
}
	
  @Override
	public void run()	//Этот метод будет выполнен в побочном потоке
	{
		while(true){
      if (currentVelocity<velocity){currentVelocity+=ACCELERATION;}
      if (currentVelocity>velocity){currentVelocity-=ACCELERATION;}
      Vector3f dir = new Vector3f(direction).mul(currentVelocity);
      position.add(dir)
      Thread.sleep(20);
    }
    System.out.println("Привет из побочного потока!");
	}
  
  public void handleSceneEvent(EventObject e)    {
		 
		SceneEvent ev =(SceneEvent) e;
		if (ev.toSceneControlData().getMode()==1){
      if (ev.toSceneControlData().wolkForward()){
          velocity=MAX_VELOCITY;
      }
      if (ev.toSceneControlData().stop()){
        velocity=0f;
      }
      if (ev.toSceneControlData().wolkBackward()){
        velocity=-MAX_VELOCITY;
      }
      if (ev.toSceneControlData().strafeLeft()){
      
      }
   
    }
	 }
}

