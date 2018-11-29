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
	//Метка окна
    private long window;
    
  
    
    //Класс загружающий в OpenGl программу, в соответствии с которой будет производиться вывод графики
    ShaderProgram shaderProgram;

    //Параметры проекционной матрицы
    //FOV угол обзора
    //Z_NEAR - ближняя плоскость отсечения
    //Z_FAR - дальняя плоскость отсечения
    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    //Проекционная марица для построения перспективы
    private Matrix4f projectionMatrix;
    //Видовая матрица, для построения изображения с камеры
    private Matrix4f viewMatrix;
    
   //Буфер индексов вершин
    private IntBuffer indicesBuf;
    //Буфер координат вершин
    private FloatBuffer verticesBuf;
    //Буфер нормалей к вершинам
    private FloatBuffer normalsBuf;
    private FloatBuffer textsBuf;
    private FloatBuffer textsBufFire;
    private IntBuffer indicesBufFire;
    //Буфер координат вершин
    private FloatBuffer verticesBufFire;
    //Буфер нормалей к вершинам
    private FloatBuffer normalsBufFire;
   
   
   
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        
       try {
            // Read an OBJ file
           //Чтение модели из файла 
    	   InputStream objInputStream = new FileInputStream("model/particle.obj");
    	   InputStream objInputStreamFire = new FileInputStream("model/particalFire.obj");
           
            //Пользуемся библиотекой для расшифровки obj файлов
            Obj obj = ObjReader.read(objInputStream);
                       
            obj = ObjUtils.convertToRenderable(obj);
            
           
            
            //Получаем готовые буферы данных для отправки в OpenGL
            indicesBuf = ObjData.getFaceVertexIndices(obj, 3);
            verticesBuf = ObjData.getVertices(obj);
            normalsBuf= ObjData.getNormals(obj);
            textsBuf=ObjData.getTexCoords(obj, 2);
            
            
            Obj obj1  = ObjReader.read(objInputStreamFire);
            
            obj1 = ObjUtils.convertToRenderable(obj1);
           
            
            //Получаем готовые буферы данных для отправки в OpenGL
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

        // Make the OpenGL context current/ Указатель на окно, в которое OenGL будет делать вывод.
        glfwMakeContextCurrent(window);
        // Enable v-sync. Синхронизация с монитором
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
     
    }

    private void loop() throws Exception{
    	
    	//aspectRatio - соотношение сторон окна
    	float aspectRatio = 1.0f;
    	
    	
    	
    	// This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
    	//Настройка всех средст OpenGL на вывод в окно к которому была совершена привязка
    	GL.createCapabilities();
    	
    	//Вершинные шейдеры - это программы, выполняемые видеочипами, которые производят 
    	//математические операции с вершинами (vertex, из них состоят 3D объекты в играх), 
    	//иначе говоря, они предоставляют возможность выполнять программируемые алгоритмы 
    	//по изменению параметров вершин и их освещению (T&L - Transform & Lighting).
    	
    	String vShader=
        		"#version 330 \n"+
        		//location = 0 идентификатор для отправки данных в видеоадаптер
        		//этой процедурой glVertexAttribPointer(0{location=0}, 3, GL_FLOAT, false, 0, 0);
        		//Прием позиций вершин
				"layout (location = 0) in vec3 position;"+
				"layout (location = 1) in vec2 texture;"+
				"out vec2 outTexCoord;"+
				
        		//Прием векторов нормалей к вершинам
				//"layout (location=2) in vec3 vertexNormal;"+
        		//Так указывается переменная котороая будет засылатся в шейдер извне
				"uniform mat4 projectionMatrix;"+
				"uniform mat4 modelViewMatrix;"+
				"uniform vec3 particlePosition;"+

				//Так объявляются переменные, которые будут отправлены в пиксельный шейдер
				
				"void main()"+
				"{"+
				//Преобразование координат объекта в соответствии с положением камеры
 				"vec4 mvPos = modelViewMatrix * vec4(position+particlePosition, 1.0);"+
			    //Применение проекции о отправка координат на отрисовку
				"gl_Position = projectionMatrix * mvPos+vec4(texture,0,1);"+
			    "  outTexCoord = texture;"+
			    //Преобразование векторов нормалей в соответствии с положением камеры
				
			    
				"}";
		
    	//Модифицоровать шейдер, чтобы он подгружал текстуры. Делать вместе с Максимом Кузовлевым
		
    	
    	String fShader=
				"#version 330 \n"+
				"in  vec2 outTexCoord;"+
				//Указатель на то, что для отображения цвета будет использоваться эта переменная
				"out vec4 fragColor;"+
				"uniform sampler2D texture_sampler;"+
				//Переменная для импорта положения источника света
				
				//Так описываются переменные, которые будут приняты из вершинного шейдера
				
				"void main()"+
				"{"+
				//Расчет направления падения света на данную вершину
				
				//Вычесление коеффициента рассеивания света 
				
				//Вычисление результирующего света
				"if(texture(texture_sampler, outTexCoord).a<0.1f) {"+
					"discard;"+
				"}"+
				"fragColor = texture(texture_sampler, outTexCoord)*0.5;"+
				"}";
		
		//Загрузка программы в видеокарту
		shaderProgram = new ShaderProgram();
	    shaderProgram.createVertexShader(vShader);
	    shaderProgram.createFragmentShader(fShader);
	     
	    shaderProgram.link();
	    
	    
	    //Создание ссылок на переменные типа uniform
	    shaderProgram.createUniform("projectionMatrix");
	    shaderProgram.createUniform("texture_sampler");
	    shaderProgram.createUniform("modelViewMatrix");
	   // shaderProgram.createUniform("lightPosition");
	    shaderProgram.createUniform("particlePosition");
	    //Этот класс заправляет данные о модели в видеопамят
	    
	    
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
	    
            
                 
        
        
        // Set the clear color/ Цвет фона
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //Указатель на то что не следует отрисовывать грани, которых не видно.
        glEnable(GL_DEPTH_TEST);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        
        //Настройка камеры
        Camera camera = new Camera();
        camera.setPosition(-2.0f,0.0f,4.0f);
        camera.setRotation(0.0f,30.0f,0.0f);
        //Инициализация класса геометрических преобразований
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
        
        //Поток в которм будет выполняться отрисовывание изображения
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffe/ очистка экрана
            glDepthMask(false);
            
            particalEmiter.addPartacal(1f);
            particalEmiterFire.addPartacal(1f);
            
            // swap the color buffers
            //Указатель на программу, по которой производить отрисовку
            shaderProgram.bind();
          
            //Вычисление проекционной матрицы
            Matrix4f projectionMatrix = transformation.updateProjectionMatrix(FOV, 100, 100, Z_NEAR, Z_FAR);
            //Отправка проекционной матрицы в шейдер
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
            //Вычисление видовой матрицы
            viewMatrix = transformation.updateViewMatrix(camera);
            shaderProgram.setUniform("modelViewMatrix", viewMatrix);
            
            //Определение положения источника света
            //Vector3f lightPos = new Vector3f(10.0f,10.0f,-10.0f);
            //shaderProgram.setUniform("lightPosition", lightPos);
            //Использование данных загруженной модели
           // glBindVertexArray(mesh.getVaoId());
            //Включение доступа к входным данным в шейдере
            glEnableVertexAttribArray(0);
           // glEnableVertexAttribArray(2);
            
            Matrix4f modelMatrix = transformation.buildModelMatrix(gameItem);
            viewMatrix.transpose3x3(modelMatrix);
            Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(modelMatrix, viewMatrix);
            modelViewMatrix.scale(gameItem.getScale());
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
           
            //Отрисовка на основе используемых данных
          
            
         
            
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




