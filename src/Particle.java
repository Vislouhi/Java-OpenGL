package visualiser;

import org.joml.Vector3f;

public class Particle {

	private float x;
	private float y;
	private float z;
	private int ttl; 
	private boolean died;
	
	public Particle()
	{
		this.x=0.0f;
		this.y=0.0f;
		this.z=0.0f;
		this.ttl=30;
		this.died=false;
	}
	
	public void setPosition(float x,float y,float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;	
	}
	public void updateTtl()
	{
		this.ttl--;
		if (this.ttl==0) {this.died=true;}
		
	}
	public boolean isDied()
	{
		return this.died;
		
	}
	
	public  Vector3f getPosition()
	{
		return new Vector3f(this.x,this.y,this.z);	
	}


}
