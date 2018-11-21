package visualiser;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.Version;


import static org.lwjgl.glfw.Callbacks.*;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    private long window;

    ShaderProgram shaderProgram;
    ShaderProgram shaderProgramPp;
    ShaderProgram ForPpProgram;

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    
    private IntBuffer indicesBuf;
    private FloatBuffer verticesBuf;
    private FloatBuffer normalsBuf;
   
   
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
       
       try {

    	   InputStream objInputStream = new FileInputStream("model/monkey.obj");

            Obj obj = ObjReader.read(objInputStream);                      
            obj = ObjUtils.convertToRenderable(obj);
  
            indicesBuf = ObjData.getFaceVertexIndices(obj, 3);
            verticesBuf = ObjData.getVertices(obj);
            normalsBuf= ObjData.getNormals(obj);
    
        }catch (Exception excp) {
            excp.printStackTrace();
        } 
   
        try {
            init();
            loop();

            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        }catch (Exception excp) {
            excp.printStackTrace();
        } 
        
        finally {

            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    
    }
  
    private void init() throws Exception{

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); 
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); 
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        int WIDTH = 600;
        int HEIGHT = 600;

        window = glfwCreateWindow(WIDTH, HEIGHT, "Hell World!", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwShowWindow(window);
     
    }

    private void loop() throws Exception{
    	
    	float aspectRatio = 1.0f;
    	
    	GL.createCapabilities();

    	String vppShader=
	    		"#version 330 \n"+
				"layout (location = 0) in vec2 position;"+
	    		"out vec2 outTexCoord;"+
				"void main()"+
				"{"+				
				"gl_Position = vec4(position,0.0,1.0);"+
				"outTexCoord=((position)+vec2(1f-2f/300f,1f-5f/300f))/2;"+
				"}";
    	
    	String fppShader="#version 330\r\n" + 
				"\r\n" + 
				
				"in  vec2 outTexCoord;"+
				"uniform sampler2D texture_sampler;"+
				"uniform vec4 fragColor2;"+
				"out vec4 fragColor;\r\n" + 
				"float ofset = 2f/300f; \r\n" + 
				"void main()\r\n" + 
				"{\r\n" + 				
				//"if (texture(texture_sampler, outTexCoord).rgb==vec3(0,0,0)){ discard;}"+
				"vec2 tCoord=outTexCoord+vec2(0,ofset);"+
				//"if (texture(texture_sampler, tCoord+vec2(0,0)).rgb==vec3(0,0,0)){ discard;}"+
				"if (texture(texture_sampler, tCoord+vec2(0,ofset)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(0,-ofset)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(-ofset,0)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(ofset,0)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(ofset,ofset)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(-ofset,ofset)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(ofset,-ofset)).rgb!=vec3(0,0,0)"
				+ "&&texture(texture_sampler, tCoord+vec2(-ofset,-ofset)).rgb!=vec3(0,0,0)"				
				+ "){ discard;}"+
				"fragColor = fragColor2;" + 
				"}";
    	
    	String vShader=
        		"#version 330 \n"+
        		
				"layout (location = 0) in vec3 position;"+
				"layout (location=2) in vec3 vertexNormal;"+
				"uniform mat4 projectionMatrix;"+
				"uniform mat4 modelViewMatrix;"+
				"uniform vec3 translatoin;"+
				"out vec3 mvVertexNormal;"+
				"out vec3 mvVertexPos;"+
				"void main()"+
				"{"+
				"vec4 mvPos = modelViewMatrix * vec4(position+translatoin, 1.0);"+
				"gl_Position = projectionMatrix * mvPos;"+
				"mvVertexNormal = normalize(modelViewMatrix * vec4(vertexNormal, 0.0)).xyz;"+
			    "mvVertexPos = mvPos.xyz;"+
				"}";
		
		String fShader=
				"#version 330 \n"+
				
				"out vec4 fragColor;"+
				"uniform vec3 lightPosition;"+
				"in vec3 mvVertexNormal;"+
				"in vec3 mvVertexPos;"+
				"void main()"+
				"{"+
				"vec3 lightDir = normalize(lightPosition - mvVertexPos);"+
				"float diff = max(dot(mvVertexNormal, lightDir), 0.0);"+
				"fragColor = 0.6*diff*vec4(0.0, 0.5, 0.5, 1.0)+0.4*vec4(0.0, 0.5, 0.5, 1.0);"+
				"}";
		
		
		
		String vForPpShader=
	    		"#version 330 \n"+
				"layout (location = 0) in vec3 position;"+
				"uniform mat4 projectionMatrix;"+
				"uniform mat4 modelViewMatrix;"+
				"uniform vec3 translatoin;"+	
				"void main()"+
				"{"+
				"vec4 mvPos = modelViewMatrix * vec4(position+translatoin, 1.0);"+
				"gl_Position = projectionMatrix * mvPos;"+
				"}";
		
		String fForPpShader=
				"#version 330 \n"+
				"out vec4 fragColor;"+
				"void main()"+
				"{"+
				"fragColor = vec4(0, 0, 0, 0);"+
				"}";
	
		
		shaderProgram = new ShaderProgram();
	    shaderProgram.createVertexShader(vShader);
	    shaderProgram.createFragmentShader(fShader);
	     
	    shaderProgram.link();
	    
	    shaderProgramPp = new ShaderProgram();
	    shaderProgramPp.createVertexShader(vppShader);
	    shaderProgramPp.createFragmentShader(fppShader);
	     
	    shaderProgramPp.link();
	    
	    ForPpProgram = new ShaderProgram();
	    ForPpProgram.createFragmentShader(fForPpShader);
	    ForPpProgram.createVertexShader(vForPpShader);
	    ForPpProgram.link();
	    
	    
	    shaderProgramPp.createUniform("texture_sampler");
	    
	    shaderProgram.createUniform("projectionMatrix");
	    shaderProgram.createUniform("modelViewMatrix");
	    shaderProgram.createUniform("lightPosition");
	    shaderProgram.createUniform("translatoin");
	    shaderProgramPp.createUniform("fragColor2");
	    
	    MeshFromBuffers mesh = new MeshFromBuffers(verticesBuf, indicesBuf,  normalsBuf);
		   
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glStencilOp(GL_KEEP, GL_KEEP , GL_REPLACE);
       
        Camera camera = new Camera();
        camera.setPosition(-2.0f,0.0f,4.0f);
        camera.setRotation(0.0f,30.0f,0.0f);

        Transformation transformation = new Transformation();
        PostProcessor postProcessor = new PostProcessor();
        
        while (!glfwWindowShouldClose(window)) {
          glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); // clear the framebuffe/ очистка экрана  
        	
        	postProcessor.bindFBO();
        	          
        	shaderProgram.bind();
            
            projectionMatrix = transformation.getProjectionMatrix(FOV, 300, 300, Z_NEAR, Z_FAR);
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
            viewMatrix = transformation.getViewMatrix(camera);
            shaderProgram.setUniform("modelViewMatrix", viewMatrix);
            shaderProgram.setUniform("translatoin", new Vector3f(0f,0f,0f));
            Vector3f lightPos = new Vector3f(10.0f,10.0f,-10.0f);
            shaderProgram.setUniform("lightPosition", lightPos);
            glBindVertexArray(mesh.getVaoId());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(2);
           
            shaderProgram.setUniform("translatoin", new Vector3f(0f,0f,0f));
     
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            
            shaderProgram.setUniform("translatoin", new Vector3f(0f,1.5f,1f));
            shaderProgramPp.setUniform("fragColor2", new Vector4f(1f,0f,0f,1f));
            
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            
    		shaderProgram.unbind();

    		
			postProcessor.unBindFBO();
		
			shaderProgram.bind();
            
            projectionMatrix = transformation.getProjectionMatrix(FOV, 300, 300, Z_NEAR, Z_FAR);
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
            viewMatrix = transformation.getViewMatrix(camera);
            shaderProgram.setUniform("modelViewMatrix", viewMatrix);
            shaderProgram.setUniform("translatoin", new Vector3f(0f,0f,0f));
            lightPos = new Vector3f(10.0f,10.0f,-10.0f);
            shaderProgram.setUniform("lightPosition", lightPos);
            glBindVertexArray(mesh.getVaoId());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(2);
           
            shaderProgram.setUniform("translatoin", new Vector3f(0f,0f,0f));
            glStencilFunc(GL_ALWAYS, 1, 0xFF);
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            shaderProgram.setUniform("translatoin", new Vector3f(0f,1.5f,1f));
            glStencilFunc(GL_ALWAYS, 2, 0xFF);
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            shaderProgram.setUniform("translatoin", new Vector3f(0f,1.5f,1f));
            
            
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            
    		shaderProgram.unbind();
					
			shaderProgramPp.bind();
			glActiveTexture(GL_TEXTURE0);		
			shaderProgramPp.setUniform("texture_sampler", 0);	
		    glStencilFunc(GL_EQUAL, 1, 0xFF);

			postProcessor.render();		
			shaderProgramPp.unbind();
			
			
			
			

			
			glfwSwapBuffers(window); 
            glfwPollEvents();
        }
    }
    
   
    public static void main(String[] args) {
        new Main().run();
    }

}
