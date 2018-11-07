package com;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class SceneDataIni {
	
	public ArrayList<GameItem> gameItems = new ArrayList<>();
	public Map<String,Float> values = new HashMap<>();
	public SpringOscilator springOscilator;
	MatchParser parser = new MatchParser();
	
	public SceneDataIni(String fileName)
	{
		 
			try {
				
				//Ini file parsing with org.ini4j.Wini library
				Wini ini;
				ini = new Wini(new File(fileName));
			
				
				
				
				//Loading data to oscilator
				springOscilator = new SpringOscilator(
						ini.get("springOscilator", "mass", float.class),
						ini.get("springOscilator", "gravity", float.class),
						ini.get("springOscilator", "attenuation", float.class),
						ini.get("springOscilator", "stiffness", float.class),
						ini.get("springOscilator", "initialSpeed", float.class)
						);
				
				
				if (ini.get("springOscilator", "value")!=null) {
					//Loadin the key string to spring oscilator
					springOscilator.setValue(ini.get("springOscilator", "value"));
					//Loading value key to the keylist of math parser
					parser.setVariable(ini.get("springOscilator", "value"), 0d);
				
			}

				
				GameItem currentGameItem = new GameItem();
				
				
				//Setting gameItem data of static scene
				currentGameItem.setFileName(ini.get("scene", "fileName")); 
				currentGameItem.setStatus(ini.get("scene", "status")); 
				
				currentGameItem.setPosition(new Vector3f(
						ini.get("scene", "positionX", float.class),
						ini.get("scene", "positionY", float.class),
						ini.get("scene", "positionZ", float.class)));
				
				currentGameItem.setRotationPoint(new Vector3f(
						ini.get("scene", "rotationPointX", float.class),
						ini.get("scene", "rotationPointY", float.class),
						ini.get("scene", "rotationPointZ", float.class)));
				currentGameItem.setRotationAxis(new Vector3f(
						ini.get("scene", "rotationAxisX", float.class),
						ini.get("scene", "rotationAxisY", float.class),
						ini.get("scene", "rotationAxisZ", float.class)));
				currentGameItem.setRotationAngle(ini.get("scene", "rotationAngle", float.class)); 
				
				currentGameItem.setTransitionTime(ini.get("scene", "transitionTime", float.class)); 
				currentGameItem.printParams();
				//Adding game Item to game items container
				gameItems.add(currentGameItem);
				String currentObject;
				int i=1;
				currentObject="object"+i;
				//Loop through the dynamic scene objects
				while(ini.get(currentObject, "fileName")!=null)
				{
					
					currentGameItem = new GameItem();
					currentGameItem.setFileName(ini.get(currentObject, "fileName")); 
					currentGameItem.setStatus(ini.get(currentObject, "status")); 
					currentGameItem.setValue(ini.get(currentObject, "value")); 
				
					float posX=0f;
					//Loading to the position value or formula
					if (ini.get(currentObject, "positionXFormula")==null) {
						posX=ini.get(currentObject, "positionX", float.class);
						
					}else {
						currentGameItem.setPositionXFormula(ini.get(currentObject, "positionX"));
					}
					
					float posY=0f;
					if (ini.get(currentObject, "positionYFormula")==null) {
						posY=ini.get(currentObject, "positionY", float.class);
						
					}else {
						currentGameItem.setPositionYFormula(ini.get(currentObject, "positionY"));
					}
					
					float posZ=0f;
					if (ini.get(currentObject, "positionZFormula")==null) {
						posZ=ini.get(currentObject, "positionZ", float.class);
						
					}else {
						currentGameItem.setPositionZFormula(ini.get(currentObject, "positionZ"));
					}
					
					
					
					currentGameItem.setPosition(new Vector3f(
							posX,
							posY,
							posZ));
					
					currentGameItem.setRotationPoint(new Vector3f(
							ini.get(currentObject, "rotationPointX", float.class),
							ini.get(currentObject, "rotationPointY", float.class),
							ini.get(currentObject, "rotationPointZ", float.class)));
					currentGameItem.setRotationAxis(new Vector3f(
							ini.get(currentObject, "rotationAxisX", float.class),
							ini.get(currentObject, "rotationAxisY", float.class),
							ini.get(currentObject, "rotationAxisZ", float.class)));
					
					if (ini.get(currentObject, "rotationAngleFormula")==null) {
						currentGameItem.setRotationAngle(ini.get(currentObject, "rotationAngle", float.class));
					}else {
						currentGameItem.setRotationAngleFormula(ini.get(currentObject, "rotationAngle"));
					}
					
					
					currentGameItem.setTransitionTime(ini.get(currentObject, "transitionTime", float.class)); 
					
					//Printing resalts to console
					currentGameItem.printParams();
					
					currentGameItem.setStartValue(ini.get(currentObject, "startValue", float.class));
					currentGameItem.setValue(currentGameItem.getStartValue());
					
					
					if (ini.get(currentObject, "value")!=null) {
							currentGameItem.setValue(ini.get(currentObject, "value"));
							//Adding variables to the parser list
							parser.setVariable(ini.get(currentObject, "value"), (double)currentGameItem.getValue());
						
					}
					
					//Adding values which a changing due to the LMB click
					int j=1;
					while(ini.get(currentObject, "valueOnLmb"+j)!=null) {
						
						currentGameItem.addValueOnLmb(ini.get(currentObject, "valueOnLmb"+j,float.class));
						j++;
					}
					//Adding game item to the container
					gameItems.add(currentGameItem);
					i++;
					currentObject="object"+i;
				}
				System.out.println( currentGameItem.getPosition().x );
			} catch (InvalidFileFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void calculateFormulas(GameItem gameItem) {
		//Renewing variables of math parser
		if (this.springOscilator.getStatus()) {
			
			parser.setVariable(springOscilator.getValueKey(), (double)springOscilator.getValue());
		}
		parser.setVariable(gameItem.getValueKey(), (double)gameItem.getValue());
		//System.out.println("!!!"+gameItem.getValueKey());
		//Setting the rotationAngle  due to the rotationAngle formula
		if (gameItem.rotationAngleFormula!=null) {
			
			try {
				
				gameItem.setRotationAngle((float)parser.Parse(gameItem.rotationAngleFormula));
				System.out.println("Parser");
				System.out.println(gameItem.getRotationAngle());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		if (gameItem.positionXFormula!=null) {
			
			try {
				
				gameItem.setPositionX((float)parser.Parse(gameItem.positionXFormula));
				System.out.println("Parse r");
				System.out.println(parser.Parse(gameItem.positionXFormula));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if (gameItem.positionYFormula!=null) {
			
			try {
				
				gameItem.setPositionY((float)parser.Parse(gameItem.positionYFormula));
				System.out.println("Parse r "+gameItem.positionYFormula);
				//gameItem.printParams();
				System.out.println(parser.Parse(gameItem.positionYFormula));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if (gameItem.positionZFormula!=null) {
			
			try {
				
				gameItem.setPositionZ((float)parser.Parse(gameItem.positionZFormula));
				System.out.println("Parse r "+gameItem.positionZFormula);
				//gameItem.printParams();
				System.out.println(parser.Parse(gameItem.positionZFormula));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
}
