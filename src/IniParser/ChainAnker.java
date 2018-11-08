package com;


import java.util.ArrayList;

public class ChainAnker {
	private Vector3f position;
	private Vector3f step;
	private float radius;
	private ArrayList<String> availableObjects =new ArrayList<>();
	private ArrayList<String> addedObjects =new ArrayList<>();
	private Vector3f currentAnkerPoint;
	private float value;
	private String stringValue;
	private String formulaX;
	private String formulaY;
	private String formulaZ;
	
	public  ChainAnker(Vector3f position,Vector3f step,float radius)
	{
		this.position=position;
		this.currentAnkerPoint=position;
		this.step=step;
		this.radius=radius;
		value=0f;
	}
	public void setFormulaX(String s)
	{
		formulaX=s;
	}
	
	public void setFormulaY(String s)
	{
		formulaY=s;
	}
	public void setFormulaZ(String s)
	{
		formulaZ=s;
	}
	public void addAvailableObject(String object)
	{
		availableObjects.add(object);
	}
	
	public void addObjectToChain(String object)
	{
		addedObjects.add(object);
		this.currentAnkerPoint.add(this.step);
		value+=1f;
	}
	
	public void removeObjectFromChain()
	{
		addedObjects.remove(addedObjects.get(addedObjects.size()-1));
		this.currentAnkerPoint.sub(this.step);
		value-=1f;
	}
	public Vector3f getCurrentAnkerPoint()
	{
		return currentAnkerPoint;
	}
	
	public boolean positionInRadius(Vector3f position) {
		
		Vector3f temp= new Vector3f(position);
		temp.sub(currentAnkerPoint);
		
		return temp.length()<this.radius;
	}
	
	public void setStringValue(String s)
	{
		this.stringValue=s;
	}

}
