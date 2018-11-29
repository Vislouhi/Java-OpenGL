package visualiser;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import java.util.ArrayList;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;


import static org.lwjgl.glfw.Callbacks.*;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
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

    // The window handle
	//����� ����
    private long window;
    
  
    
    //����� ����������� � OpenGl ���������, � ������������ � ������� ����� ������������� ����� �������
    ShaderProgram shaderProgram;

    //��������� ������������ �������
    //FOV ���� ������
    //Z_NEAR - ������� ��������� ���������
    //Z_FAR - ������� ��������� ���������
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    //������������ ������ ��� ���������� �����������
    private Matrix4f projectionMatrix;
    //������� �������, ��� ���������� ����������� � ������
    private Matrix4f viewMatrix;
    
   //����� �������� ������
    private IntBuffer indicesBuf;
    //����� ��������� ������
    private FloatBuffer verticesBuf;
    //����� �������� � ��������
    private FloatBuffer normalsBuf;
    private FloatBuffer textsBuf;
    private FloatBuffer textsBufFire;
    private IntBuffer indicesBufFire;
    //����� ��������� ������
    private FloatBuffer verticesBufFire;
    //����� �������� � ��������
    private FloatBuffer normalsBufFire;
   
   
   
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        
       try {
            // Read an OBJ file
           //������ ������ �� ����� 
    	   InputStream objInputStream = new FileInputStream("model/particle.obj");
    	   InputStream objInputStreamFire = new FileInputStream("model/particalFire.obj");
           
            //���������� ����������� ��� ����������� obj ������
            Obj obj = ObjReader.read(objInputStream);
                       
            obj = ObjUtils.convertToRenderable(obj);
            
           
            
            //�������� ������� ������ ������ ��� �������� � OpenGL
            indicesBuf = ObjData.getFaceVertexIndices(obj, 3);
            verticesBuf = ObjData.getVertices(obj);
            normalsBuf= ObjData.getNormals(obj);
            textsBuf=ObjData.getTexCoords(obj, 2);
            
            
            Obj obj1  = ObjReader.read(objInputStreamFire);
            
            obj1 = ObjUtils.convertToRenderable(obj1);
           
            
            //�������� ������� ������ ������ ��� �������� � OpenGL
            indicesBufFire = ObjData.getFaceVertexIndices(obj1, 3);
            verticesBufFire = ObjData.getVertices(obj1);
            normalsBufFire= ObjData.getNormals(obj1);
            textsBufFire=ObjData.getTexCoords(obj1, 2);
            
           
           
            
            
    
        }catch (Exception excp) {
            excp.printStackTrace();
        } 
   
        try {
            init();
            loop();

            // Release window and window callbacks
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        }catch (Exception excp) {
            excp.printStackTrace();
        } 
        
        finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    
    }

    
  
    private void init() throws Exception{
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        int WIDTH = 500;
        int HEIGHT = 500;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                window,
                (vidmode.width() - WIDTH) / 2,
                (vidmode.height() - HEIGHT) / 2
        );

        // Make the OpenGL context current/ ��������� �� ����, � ������� OenGL ����� ������ �����.
        glfwMakeContextCurrent(window);
        // Enable v-sync. ������������� � ���������
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
     
    }

    private void loop() throws Exception{
    	
    	//aspectRatio - ����������� ������ ����
    	float aspectRatio = 1.0f;
    	
    	
    	
    	// This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
    	//��������� ���� ������ OpenGL �� ����� � ���� � �������� ���� ��������� ��������
    	GL.createCapabilities();
    	
    	//��������� ������� - ��� ���������, ����������� �����������, ������� ���������� 
    	//�������������� �������� � ��������� (vertex, �� ��� ������� 3D ������� � �����), 
    	//����� ������, ��� ������������� ����������� ��������� ��������������� ��������� 
    	//�� ��������� ���������� ������ � �� ��������� (T&L - Transform & Lighting).
    	
    	String vShader=
        		"#version 330 \n"+
        		//location = 0 ������������� ��� �������� ������ � ������������
        		//���� ���������� glVertexAttribPointer(0{location=0}, 3, GL_FLOAT, false, 0, 0);
        		//����� ������� ������
				"layout (location = 0) in vec3 position;"+
				"layout (location = 1) in vec2 texture;"+
				"out vec2 outTexCoord;"+
				
        		//����� �������� �������� � ��������
				//"layout (location=2) in vec3 vertexNormal;"+
        		//��� ����������� ���������� �������� ����� ��������� � ������ �����
				"uniform mat4 projectionMatrix;"+
				"uniform mat4 modelViewMatrix;"+
				"uniform vec3 particlePosition;"+

				//��� ����������� ����������, ������� ����� ���������� � ���������� ������
				
				"void main()"+
				"{"+
				//�������������� ��������� ������� � ������������ � ���������� ������
 				"vec4 mvPos = modelViewMatrix * vec4(position+particlePosition, 1.0);"+
			    //���������� �������� � �������� ��������� �� ���������
				"gl_Position = projectionMatrix * mvPos+vec4(texture,0,1);"+
			    "  outTexCoord = texture;"+
			    //�������������� �������� �������� � ������������ � ���������� ������
				
			    
				"}";
		
    	//�������������� ������, ����� �� ��������� ��������. ������ ������ � �������� ����������
		
    	
    	String fShader=
				"#version 330 \n"+
				"in  vec2 outTexCoord;"+
				//��������� �� ��, ��� ��� ����������� ����� ����� �������������� ��� ����������
				"out vec4 fragColor;"+
				"uniform sampler2D texture_sampler;"+
				//���������� ��� ������� ��������� ��������� �����
				
				//��� ����������� ����������, ������� ����� ������� �� ���������� �������
				
				"void main()"+
				"{"+
				//������ ����������� ������� ����� �� ������ �������
				
				//���������� ������������ ����������� ����� 
				
				//���������� ��������������� �����
				"if(texture(texture_sampler, outTexCoord).a<0.1f) {"+
					"discard;"+
				"}"+
				"fragColor = texture(texture_sampler, outTexCoord)*0.5;"+
				"}";
		
		//�������� ��������� � ����������
		shaderProgram = new ShaderProgram();
	    shaderProgram.createVertexShader(vShader);
	    shaderProgram.createFragmentShader(fShader);
	     
	    shaderProgram.link();
	    
	    
	    //�������� ������ �� ���������� ���� uniform
	    shaderProgram.createUniform("projectionMatrix");
	    shaderProgram.createUniform("texture_sampler");
	    shaderProgram.createUniform("modelViewMatrix");
	   // shaderProgram.createUniform("lightPosition");
	    shaderProgram.createUniform("particlePosition");
	    //���� ����� ���������� ������ � ������ � ����������
	    
	    
	    MeshFromBuffers mesh = new MeshFromBuffers(verticesBuf, indicesBuf,  normalsBuf, textsBuf);
	    
	    MeshFromBuffers meshFire = new MeshFromBuffers(verticesBufFire, indicesBufFire,  normalsBufFire, textsBufFire);
	    
	    String[] textListH20= {"h2opng.png"};
	  
	    ParticalEmiter particalEmiter=new ParticalEmiter(mesh,new Vector3f(0.0f,0.0f,-80.0f),new Vector3f(0.0f,-0.4f,0.0f),textListH20,100);
	    String[] textList= {"fire1.png", "fire3.png","fire2.png"};
	    
	    
	    ParticalEmiter particalEmiterFire=new ParticalEmiter(meshFire,new Vector3f(0.0f,0.0f,-15.0f),new Vector3f(0.0f, 0.1f,0.0f),textList,10);
	    
	    GameItem gameItem = new GameItem(mesh); 
	    
	    //Triangle t1= new Triangle(new Vector3f(1f,0.5f,0f),new Vector3f(1f,-0.5f,0f),new Vector3f(-1f,0f,0f));
	   // Triangle t2= new Triangle(new Vector3f(0f,0f,1f),new Vector3f(0f,0f,-0.5f),new Vector3f(0.1f,0.1f,1f));
	   // CollisionDetection colDet = new CollisionDetection(t1,t2);
	   // ParticalEmiter partical = new ParticalEmiter(partical); 
	    
            
                 
        
        
        // Set the clear color/ ���� ����
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //��������� �� �� ��� �� ������� ������������ �����, ������� �� �����.
        glEnable(GL_DEPTH_TEST);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        
        //��������� ������
        Camera camera = new Camera();
        camera.setPosition(-2.0f,0.0f,4.0f);
        camera.setRotation(0.0f,30.0f,0.0f);
        //������������� ������ �������������� ��������������
        Transformation transformation = new Transformation();
        glEnable(GL_BLEND);
        glBlendFunc( GL_ONE_MINUS_DST_ALPHA , GL_ONE	);
    
      // 
        float x1 = 0.0f;
        float y1 = 0.0f;
        float z1 = 0.0f;
        ArrayList<Partical> particals = new ArrayList<Partical>();
        for (int i = 0; i < 9; i = i + 1) {
        	x1+=0.2f;
        	y1+=0.01f;
        	z1+=0.01f;
        	
        //Partical carrentPartical = new Partical(x1,z1,y1);
      //  particals.add(carrentPartical);
        }
        
        //����� � ������ ����� ����������� ������������� �����������
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffe/ ������� ������
            glDepthMask(false);
            
            particalEmiter.addPartacal(1f);
            particalEmiterFire.addPartacal(1f);
            
            // swap the color buffers
            //��������� �� ���������, �� ������� ����������� ���������
            shaderProgram.bind();
          
            //���������� ������������ �������
            Matrix4f projectionMatrix = transformation.updateProjectionMatrix(FOV, 100, 100, Z_NEAR, Z_FAR);
            //�������� ������������ ������� � ������
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
            //���������� ������� �������
            viewMatrix = transformation.updateViewMatrix(camera);
            shaderProgram.setUniform("modelViewMatrix", viewMatrix);
            
            //����������� ��������� ��������� �����
            //Vector3f lightPos = new Vector3f(10.0f,10.0f,-10.0f);
            //shaderProgram.setUniform("lightPosition", lightPos);
            //������������� ������ ����������� ������
           // glBindVertexArray(mesh.getVaoId());
            //��������� ������� � ������� ������ � �������
            glEnableVertexAttribArray(0);
           // glEnableVertexAttribArray(2);
            
            Matrix4f modelMatrix = transformation.buildModelMatrix(gameItem);
            viewMatrix.transpose3x3(modelMatrix);
            Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(modelMatrix, viewMatrix);
            modelViewMatrix.scale(gameItem.getScale());
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
           
            //��������� �� ������ ������������ ������
          
            
         
            
            //particlePosition = new Vector3f(particals.get(1).getPosition());
            //shaderProgram.setUniform("particlePosition",particlePosition);
            //glDrawElements(GL_TRIANGLES, gameItem.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
            
            
           
            particalEmiter.drawPartical(shaderProgram);
            
            
            particalEmiterFire.drawPartical(shaderProgram);
           
           
           
            // Restore state
            
            
			shaderProgram.unbind();
			
            // Poll for window events. The key callback above will only be
            // invoked during this call.
			glfwSwapBuffers(window); 
            glfwPollEvents();
        }
    }
    
   
    public static void main(String[] args) {
        new Main().run();
    }

}




