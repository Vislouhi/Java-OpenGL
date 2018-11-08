package com;

import java.util.ArrayList;

public class GameItem{
	public String fileName;
	private String status;
	private String name;
	private String value;
	public String positionXFormula;
	public String positionYFormula;
	public String positionZFormula;
	private Vector3f position;
	
	private Vector3f rotationPoint;
	private Vector3f rotationAxis;
	private float rotationAngle;
	private float currentRotationAngle;
	public String rotationAngleFormula;

	private float transitionTime;
	private float currentValue;
	private float startValue;
	private float endValue;
	private boolean rotationAngleCalculations;
	private ArrayList<Float> valuesOnLmb = new ArrayList<Float>();
	private int currentValLmbIndex;
	
	public GameItem()
	{
		currentRotationAngle=0f;
		rotationAngleCalculations=false;
		currentValLmbIndex=0;
	}
	
	
	public void setName(String s)
	{
		this.name=s;
	}
	public String getValueKey()
	{
		return this.value;
	}
	public void bindRotationAngleCalculations() {
		rotationAngleCalculations=true;
	}
	public void setFileName(String fileName) {
		
		this.fileName=fileName;
	}
	
	public void setStatus(String status) {
		
		this.status=status;
	}
	
	public void setValue(String value) {
		
		this.value=value;
	}
	
	public void setPosition(Vector3f position) {
		
		this.position=position;
	}
	
	public void setPositionX(float position) {
		
		this.position=new Vector3f (position,this.position.y,this.position.z);
	}
	
	public void setPositionY(float position) {
		
		this.position=new Vector3f (this.position.x,position,this.position.z);
	}
	public void setPositionZ(float position) {
		
		this.position=new Vector3f (this.position.x,this.position.y,position);
	}

	
	
	
	public void setRotationPoint(Vector3f rotationPoint) {
		
		this.rotationPoint=rotationPoint;
	}
	
	public void setRotationAxis(Vector3f rotationAxis) {
		
		this.rotationAxis=rotationAxis;
	}
	
	public void setRotationAngle(float rotationAngle) {
		
		this.rotationAngle=rotationAngle;
	}
	
	
	public void setTransitionTime(float transitionTime) {
		
		this.transitionTime=transitionTime;
	}

	public Vector3f getPosition() {
	
		return position;
	}
	public void printParams()
	{
		System.out.println("Name");
		System.out.println(this.fileName);
		System.out.println("Position");
		System.out.println(getVectorString(this.position));
		System.out.println("Positio1n");
		
		System.out.println("transTime");
		System.out.println(this.transitionTime);
	}
	public String getVectorString(Vector3f v) {
		
		return "x="+v.x+" y="+v.y+" z="+v.z;
	}

	public void setRotationAngleFormula(String rotationAngleFormula) {
		this.rotationAngleFormula=rotationAngleFormula;
		
	}
	public void setPositionXFormula(String formula) {
		this.positionXFormula=formula;
		
	}
	public void setPositionYFormula(String formula) {
		this.positionYFormula=formula;
		
	}
	public void setPositionZFormula(String formula) {
		this.positionZFormula=formula;
		
	}

	public void setValue(float f) {
		
		this.currentValue=f;
		
	}
	public void setStartValue(float f) {
		
		this.startValue=f;
		
		
	}
	public void setEndValue(float f) {
		
		this.endValue=f;
		
	}
	
	public float getValue() {
		
		return this.currentValue;
		
	}
	
	public float getStartValue() {
		
		return this.startValue;
		
	}
	public float getEndValue() {
		
		return this.endValue;
		
	}
	
	public float getRotationAngle() {
		// TODO Auto-generated method stub
		return this.rotationAngle;
	}
	
	//Calculations of smooth transitions of values;
	public void startCalculations()
	{
		Thread calcThread = new Thread(new Runnable()
		{
			public void run() 
			{
				if(rotationAngleFormula!=null&&rotationAngleCalculations) {
					
					float loopTime=1f/60f;
					int steps=(int)(transitionTime/loopTime);
					float valueStep=(rotationAngle-currentRotationAngle)/(float)steps;
					for(int i=0;i<steps;i++) {
						currentRotationAngle+=valueStep;
						try {
							Thread.sleep((long)(1000f*loopTime));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						rotationAngleCalculations=false;
					System.out.println(fileName+currentRotationAngle);
					
						
					}
					}
			
			}
		});
		calcThread.start();
	}


	public void addValueOnLmb(float val) {
		valuesOnLmb.add(val);
		System.out.println("addValueOnLmb"+val);
		
	}
	public void updateValueOnLmb() {
		if (valuesOnLmb.size()>currentValLmbIndex+1) {
		currentValLmbIndex++;
		}else {
			currentValLmbIndex=0;	
		}
		if (valuesOnLmb.size()>0)
		{
		this.currentValue=valuesOnLmb.get(currentValLmbIndex);
		System.out.println("updateValueOnLmb"+this.currentValue);
		}
		
		
	}

}
