package visualiser;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

public class HUDItem {
	private Texture texture;
	
	private int vaoId;
	private int posVboId;
	private int texVboId;
	private FloatBuffer vertexBuffer; 
	private FloatBuffer texCoordBuffer;
	
	
	public HUDItem(String filename,float x, float y,float w,float h)
	{
		try {
			this.texture = new Texture(filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				float startX=-1f+2f*x/100f;
				float startY=-1f+2f*(100f-y)/100f;
				float width=2f*w/100f;
				float height=2f*h/100f;
				float[] vertices = new float[]{
						startX,  startY, 0.0f,
						startX, startY-height, 0.0f,
						startX+width, startY-height, 0.0f,
						startX,  startY, 0.0f,
						startX+width, startY-height, 0.0f,
						startX+width, startY, 0.0f
					};
				
				float[] texCoords = new float[]{
						0f,  0f, 
						0f, 1f, 
						1f, 1f, 
						0f,  0f,
						1f, 1f,
						1f, 0f
					};
				vaoId = glGenVertexArrays();
	            glBindVertexArray(vaoId);

	            // Position VBO
	            posVboId = glGenBuffers();
	            vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
	            vertexBuffer.put(vertices).flip();
	            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
	            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
	            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
	            
	            texVboId = glGenBuffers();
	            texCoordBuffer = MemoryUtil.memAllocFloat(texCoords.length);
	            texCoordBuffer.put(texCoords).flip();
	            glBindBuffer(GL_ARRAY_BUFFER, texVboId);
	            glBufferData(GL_ARRAY_BUFFER, texCoordBuffer, GL_STATIC_DRAW);
	            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
	            
	            glBindBuffer(GL_ARRAY_BUFFER, 0);
	            glBindVertexArray(0);
		
	}
	public void render(ShaderProgram shaderProgramm)
	{
		
		shaderProgramm.setUniform("texture_sampler",0);
		texture.bind();
		glBindVertexArray(vaoId);
	    glEnableVertexAttribArray(0);
	    glEnableVertexAttribArray(1);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	    glBindVertexArray(0);
	}

}
