package com;

public class Vector3f {
	public float x;
	public float y;
	public float z;
	public Vector3f(float x,float y,float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public Vector3f(Vector3f v)
	{
		this.x=v.x;
		this.y=v.y;
		this.z=v.z;
		
	}
	public void add(Vector3f step) {
		this.x+=step.x;
		this.y+=step.y;
		this.z+=step.z;
		
	}
	public void sub(Vector3f step) {
		this.x-=step.x;
		this.y-=step.y;
		this.z-=step.z;
		
	}
	public float length() {
		// TODO Auto-generated method stub
		return (float)Math.sqrt(this.x*this.x+this.y*this.y+this.z*this.z);
	}

}
