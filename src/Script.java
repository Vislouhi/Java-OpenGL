package engine.dataLoader;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import engine.items.RigidBodyEmmiter;

public class Script {
	private ArrayList<JSONObject> rigidBodyEmmitrerActions = new ArrayList<>();
	private ArrayList<JSONObject> setVariablesActions = new ArrayList<>();
	private ArrayList<JSONObject> setOutlineActions = new ArrayList<>();
	private SceneDataIni scene;
	private ArrayList<JSONObject> setCylinderActions= new ArrayList<>();
	private ArrayList<JSONObject> setItemActions= new ArrayList<>();
	private MatchParser mathParser;
	
	
	
	
	public Script(SceneDataIni scene)
	
	{
		this.scene=scene;
		this.mathParser=scene.getMathParser();
	}

	public void addLine(JSONObject scriptJson) {
		
		
		try {
			rigidBodyEmmitrerActions.add(scriptJson.getJSONObject("EmmitRigidBody"));
			
		}catch(JSONException e)
		{
			
		}
		
		try {
			setVariablesActions.add(scriptJson.getJSONObject("setVariables"));
			
		}catch(JSONException e)
		{
			
		}
		try {
			setOutlineActions.add(scriptJson.getJSONObject("setOutline"));
			
		}catch(JSONException e)
		{
			
		}
		
		try {
			setCylinderActions.add(scriptJson.getJSONObject("Cylinder"));
			
		}catch(JSONException e)
		{
			
		}
		try {
			setItemActions.add(scriptJson.getJSONObject("item"));
			
		}catch(JSONException e)
		{
			
		}
		
		
	}
	
	public void execute() {
		System.out.println("scriptExecute");
		for (JSONObject jsonObject:rigidBodyEmmitrerActions) {
		
			try {
				if (checkCondition(jsonObject.getJSONObject("condition"))){
			
			RigidBodyEmmiter cRigidBodyEmmiter=scene.rigidBodyEmmiters.get(jsonObject.getInt("emmiter")-1);
			cRigidBodyEmmiter.setNumber(jsonObject.getInt("number"));
			cRigidBodyEmmiter.setTimeStep(jsonObject.getInt("timeStep"));
			cRigidBodyEmmiter.emmiterUpdate();
				}
			}catch(JSONException e)
			{
				RigidBodyEmmiter cRigidBodyEmmiter=scene.rigidBodyEmmiters.get(jsonObject.getInt("emmiter")-1);
				cRigidBodyEmmiter.setNumber(jsonObject.getInt("number"));
				cRigidBodyEmmiter.setTimeStep(jsonObject.getInt("timeStep"));
			}
			
		}
		
		for (JSONObject jsonObject:setVariablesActions) {
			
			
			try {
				if (checkCondition(jsonObject.getJSONObject("condition"))){
						String[] variables=jsonObject.getNames(jsonObject);
							for (String name:variables) {
									scene.getMathParser().setVariable(name,jsonObject.getDouble(name));
							}
			
				}
			}catch(JSONException e)
			{
				String[] variables=jsonObject.getNames(jsonObject);
				for (String name:variables) {
						scene.getMathParser().setVariable(name,jsonObject.getDouble(name));
						
				}	
			}
				
		}
		
		for (JSONObject jsonObject:setOutlineActions) {
			
			try {
				if (checkCondition(jsonObject.getJSONObject("condition"))){
			
			if(jsonObject.getString("state").equals("on"))
			{
					scene.gameItems.get(jsonObject.getInt("item")).setOutlineOn();
				//	scene.outlineIndexes.add(jsonObject.getInt("item"));
			}
			
			if(jsonObject.getString("state").equals("off"))
			{
					scene.gameItems.get(jsonObject.getInt("item")).setOutlineOff();
				
			}
				}
			}catch(JSONException e)
			{
				if(jsonObject.getString("state").equals("on"))
				{
						scene.gameItems.get(jsonObject.getInt("item")).setOutlineOn();
					//	scene.outlineIndexes.add(jsonObject.getInt("item"));
				}
				
				if(jsonObject.getString("state").equals("off"))
				{
						scene.gameItems.get(jsonObject.getInt("item")).setOutlineOff();
					
				}	
			}
			
			
			
		}
		for (JSONObject jsonObject:setCylinderActions) {
			
			try {
				if (checkCondition(jsonObject.getJSONObject("condition"))){
			if(jsonObject.getString("visible").equals("on"))
			{
					scene.cylinders.get(jsonObject.getInt("item")-1).setVisible();
				//	scene.outlineIndexes.add(jsonObject.getInt("item"));
			}
			
			if(jsonObject.getString("visible").equals("off"))
			{
				scene.cylinders.get(jsonObject.getInt("item")-1).setInvisible();
				
			}
				}
				}catch(JSONException e)
				{
					if(jsonObject.getString("visible").equals("on"))
					{
							scene.cylinders.get(jsonObject.getInt("item")-1).setVisible();
						//	scene.outlineIndexes.add(jsonObject.getInt("item"));
					}
					
					if(jsonObject.getString("visible").equals("off"))
					{
						scene.cylinders.get(jsonObject.getInt("item")-1).setInvisible();
						
					}	
				}
			
			
			
		}
		for (JSONObject jsonObject:setItemActions) {
			try {
			if (checkCondition(jsonObject.getJSONObject("condition"))){
				
				
				if(jsonObject.getString("visible").equals("on"))
				{
						scene.gameItems.get(jsonObject.getInt("item")).setVisible();
					//	scene.outlineIndexes.add(jsonObject.getInt("item"));
				}
				
				if(jsonObject.getString("visible").equals("off"))
				{
					scene.gameItems.get(jsonObject.getInt("item")).setInvisible();
					
				}
			}
			}catch(JSONException e)
			{
				if(jsonObject.getString("visible").equals("on"))
				{
						scene.gameItems.get(jsonObject.getInt("item")).setVisible();
					//	scene.outlineIndexes.add(jsonObject.getInt("item"));
				}
				
				if(jsonObject.getString("visible").equals("off"))
				{
					scene.gameItems.get(jsonObject.getInt("item")).setInvisible();
					
				}
			}
		}
	}

	private boolean checkCondition(JSONObject conditionJson) {
		String[] variables=JSONObject.getNames(conditionJson);
		boolean result=true;
		for (String name:variables) {
			try {
				String val =conditionJson.getString(name);
				if (val.contains("=")) 
				{
					 val = val.substring(1, val.length());
					 if (!val.contains(".")) val+=".0";
					 double valDouble= Double.parseDouble(val);
					// System.out.println( "==="+scene.getMathParser().getVariable(name)+"==="+valDouble);
					 result=result&&scene.getMathParser().getVariable(name)==valDouble;
					 
				}
				if (val.contains(">")) 
				{
					 val = val.substring(1, val.length());
					 if (!val.contains(".")) val+=".0";
					 double valDouble= Double.parseDouble(val);
					 result=result&&scene.getMathParser().getVariable(name)>valDouble;
					 
				}
				if (val.contains("<")) 
				{
					 val = val.substring(1, val.length());
					 if (!val.contains(".")) val+=".0";
					 double valDouble= Double.parseDouble(val);
					 result=result&&scene.getMathParser().getVariable(name)<valDouble;
					 
				}
			}catch(NullPointerException e) { 
	             System.out.println("err:" + e); 
	             result=false;
			} 
		}
		System.out.println("res:" + result); 
		//JSONObject conditionJson = new JSONObject(string);
		return result;
	}

}
