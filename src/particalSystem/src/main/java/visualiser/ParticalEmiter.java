package visualiser;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

import org.joml.Vector3f;




public class ParticalEmiter {
	private Texture texture;  
	private Vector3f position;
	private Vector3f speed;
   	private MeshFromBuffers mesh;
	private ArrayList<Partical> particals = new ArrayList<Partical>();
	private int ttl;
	private Texture[] textureList; 
	

	public void drawPartical(ShaderProgram shaderProgram) {
		glBindVertexArray(mesh.getVaoId());
	     glEnableVertexAttribArray(0);
         glEnableVertexAttribArray(1);
        
    
		//System.out.println(mesh.getVaoId());
		glActiveTexture(GL_TEXTURE0);
		
		glBindTexture(GL_TEXTURE_2D, textureList[(int)(Math.random()*((float)textureList.length-0.1f))].getId());
		shaderProgram.setUniform("texture_sampler", 0);
	     for (int i = 0; i < particals.size(); i = i + 1) { //Цикл должен быть по всем элементам ArrayList 
	    	 	
	            Vector3f particlePosition = new Vector3f(particals.get(i).getPosition());
	            shaderProgram.setUniform("particlePosition",particlePosition);
	            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
	            
	            if(particals.get(i).updatePartical()) {
	            	removePartacal(particals.get(i));
	            	
	            }
	            
	            }
	     glDisableVertexAttribArray(0);
         glDisableVertexAttribArray(1);
         glBindVertexArray(0);
	}
	 
	public ParticalEmiter(MeshFromBuffers partical, Vector3f position, Vector3f speed, String fileName, int i) {
	       	this.ttl=i;
	        this.mesh=partical;
	        this.position=position;
	    	this.speed=speed;
	    	try {
				texture = new Texture(fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	public ParticalEmiter(MeshFromBuffers partical, Vector3f position, Vector3f speed, String[] textList, int i) {
	  	this.ttl=i;
        this.mesh=partical;
        this.position=position;
    	this.speed=speed;
    
    	try {
    		textureList = new Texture[textList.length];
    		for (int j = 0; j < textList.length; j = j + 1) {
    			
    			textureList[j] = new Texture(textList[j]);
    			
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	public void addPartacal(Partical partical)
	{
		
		particals.add(partical);
	}
	public void removePartacal(Partical partical)
	{
		
		particals.remove(partical);
	}
	
	public void addPartacal(float veroyatnost)
	{
		float random =(float) Math.random();
		if(random<veroyatnost) {
			for (int i = 0; i < 7; i = i + 1) {
				particals.add(new Partical(position,speed,1f,ttl));
				 }
			
			
			
		}
		
		
	}
	

}
