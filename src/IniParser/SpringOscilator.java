package com;

public class SpringOscilator {
			private float mass;
			private float gravity;
			private float attenuation;
			private float stiffness;
			private float xNew;
			private float xOld;
			private float xVeryOld;
			private float dt;
			private boolean status;
			private String stringValue;
	
			
			
	public SpringOscilator(float mass,float gravity,float attenuation,float stiffness,float initialSpeed)
	{
		this.mass=mass;
		this.gravity=gravity;
		this.attenuation=attenuation;
		this.stiffness=stiffness;
		this.xOld=0f;
		this.xVeryOld=-initialSpeed*dt+xOld;
		this.status=false;
		dt=1f/25f;
	}
	public void start()
	{	status=true;
	
		Thread calcThread = new Thread(new Runnable()
		{
			public void run() 
			{
				while(status) {
					//System.out.println(xNew);
					float speed=(xOld-xVeryOld)/dt;
					xNew =(-stiffness*xOld-mass*gravity-attenuation*speed)/mass*(dt*dt)+2f*xOld-xVeryOld;
					xVeryOld=xOld;
					xOld=xNew;
					try {
						Thread.sleep((long)(1000f*dt));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			
			}
		});
		calcThread.start();
	}
	public void stop()
	{
	status=false;
	}
	public boolean getStatus()
	{
		return this.status;
	}
	public void setValue(String string) {
		// TODO Auto-generated method stub
		this.stringValue=string;
	}
	public float getValue() {
		// TODO Auto-generated method stub
		return xNew;
	}
	
	public String getValueKey() {
		// TODO Auto-generated method stub
		return this.stringValue;
	}
}
