package visualiser;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.EXTFramebufferObject.*;

public class PostProcessor {
	private int framebufferID;
	private int colorTextureID;
	private int vaoId;
	public PostProcessor() throws Exception{
		
		framebufferID = glGenFramebuffersEXT();                                         // create a new framebuffer
         colorTextureID = glGenTextures();                                               // and a new texture used as a color buffer
        // depthRenderBufferID = glGenRenderbuffersEXT();                                  // And finally a new depthbuffer
  
         glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);                        // switch to the new framebuffer
  
         // initialize color texture
         glBindTexture(GL_TEXTURE_2D, colorTextureID);                                   // Bind the colorbuffer texture
         glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);               // make it linear filterd
         glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 300, 300, 0,GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);  // Create the texture data
         glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0);
         
         // attach it to the framebuffer
  glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
  
  float[] quad = {
		  -1f,-1f,
		  -1f,1f,
		  1f,1f,
		  1f,-1f
		  
		  };
  FloatBuffer posBuffer = null;
  
  vaoId = glGenVertexArrays();
  glBindVertexArray(vaoId);

  // Position VBO
  int posVboId = glGenBuffers();
  posBuffer = MemoryUtil.memAllocFloat(quad.length);
  posBuffer.put(quad).flip();
  glBindBuffer(GL_ARRAY_BUFFER, posVboId);
  glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
  glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
           
  glBindBuffer(GL_ARRAY_BUFFER, 0);
  glBindVertexArray(0);   

    }
	
	public void bindFBO()
	{
	glViewport (0, 0, 300, 300);                                    // set The Current Viewport to the fbo size
	 
    glBindTexture(GL_TEXTURE_2D, colorTextureID);                                // unlink textures because if we dont it all is gonna fail
    glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);
	}
	
	public void unBindFBO()
	{
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
	}
	
	public void render() {
		
		
		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, colorTextureID);
		glBindVertexArray(vaoId);
		
	    glEnableVertexAttribArray(0);

	    // Draw the vertices
	    glDrawArrays(GL_QUADS, 0, 4);

	    // Restore state
	    glDisableVertexAttribArray(0);
	    glBindVertexArray(0);
		
	}


}
