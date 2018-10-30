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

public void setDirection(float angle)
{
	this direction =  new Vector3f(0f,0f,1f).mul(new Matrix3f().identity().rotate(angle*314/180,new Vector3f(0f,1f,0f)));

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
    
 }
  
  public void handleSceneEvent(EventObject e)    {
		 
   SceneEvent ev =(SceneEvent) e;
   if (ev.toSceneControlData().getMode()==1){
      if (ev.toSceneControlData().wolkForward()){
          velocity=MAX_VELOCITY;
	  setDirection(0);
      }
      if (ev.toSceneControlData().stop()){
        velocity=0f;
	setDirection(0);
      }
      if (ev.toSceneControlData().wolkBackward()){
        velocity=MAX_VELOCITY;
	setDirection(180);
      }
      if (ev.toSceneControlData().strafeLeft()){
      setDirection(270);
      velocity=MAX_VELOCITY;
      }
       if (ev.toSceneControlData().strafeRight()){
      setDirection(90);
      velocity=MAX_VELOCITY;
      }
      if (ev.toSceneControlData().strafeRight())&&(ev.toSceneControlData().wolkForward()){
      velocity=MAX_VELOCITY;
      setDirection(45);
      }
      //etc...
   
    }
 }
}

