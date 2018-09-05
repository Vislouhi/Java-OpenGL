package lwjgl_start;



import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.Version;


import static org.lwjgl.glfw.Callbacks.*;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Main {

    // The window handle
	//Метка окна
    private long window;
    
   // A Vertex Array Objects (VAOs) is an object that contains one or more VBOs which 
   //are usually called attribute lists. Each attribute list can hold one type of data: 
   //position, colour, texture, etc. You are free to store whichever you want in each slot.
    //Идентификатор множества массивов точек. 
    private int vaoId;
    
    // A VBO is just a memory buffer stored in the graphics card memory that stores vertices. 
    //This is where we will transfer our array of floats that model a triangle. As we said before, 
    //OpenGL does not know anything about our data structure. In fact it can hold not just coordinates 
    //but other information, such as textures, colour, etc.
    //Идентификатор массива точек.
    private int vboId;

    
    //Класс загружающий в OpenGl программу, в соответствии с которой будет производиться вывод графики
    ShaderProgram shaderProgram;

    public Matrix4f g;
    
    
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

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

        int WIDTH = 300;
        int HEIGHT = 300;

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
				"layout (location = 0) in vec3 position;"+
				"void main()"+
				"{"+
				"gl_Position = vec4(position, 1.0);"+
				"}";
		
    	//Пиксельный шейдер работает с фрагментами растрового изображения и с текстурами —
    	//обрабатывает данные, связанные с пикселями (например, цвет, глубина, текстурные 
    	//координаты). Пиксельный шейдер используется на последней стадии графического
    	//конвейера для формирования фрагмента изображения.
		String fShader=
				"#version 330 \n"+
						"out vec4 fragColor;"+
				"void main()"+
				"{"+
				"fragColor = vec4(0.0, 0.5, 0.5, 1.0);"+
				"}";
		
		//Загрузка программы в видеокарту
		shaderProgram = new ShaderProgram();
	    shaderProgram.createVertexShader(vShader);
	    shaderProgram.createFragmentShader(fShader);
	    shaderProgram.link();
    	
    	//Массив точек, из которых будет строиться изображение
    	float[] vertices = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
            };

            FloatBuffer verticesBuffer = null;
            try {
                verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
                verticesBuffer.put(vertices).flip();

                // Create the VAO and bind to it
                //Генерирование указателя на множества массивов точек
                vaoId = glGenVertexArrays();
               
                //Прикрепление множества массивов точек к указателю
                glBindVertexArray(vaoId);

                // Create the VBO and binв to it
                //Генерирование указателя для массива точек
                vboId = glGenBuffers();
                //Прикрепление указателя к массиву
                glBindBuffer(GL_ARRAY_BUFFER, vboId);
                //Загрузка данных в массив
                glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
                // Define structure of the data /Определение структуры данных массива
                // glVertexAttribPointer(0 - указатель на переменную position в vShader, 3 - группы по три точки, 
                //GL_FLOAT - тип данных, false - не нормализованные данные , 0, 0);
                glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

                // Unbind the VBO
                glBindBuffer(GL_ARRAY_BUFFER, 0);

                // Unbind the VAO
                glBindVertexArray(0);
            } finally {
                if (verticesBuffer != null) {
                    MemoryUtil.memFree(verticesBuffer);
                }
            }
    	
        

        
        
        
        
        // Set the clear color/ Цвет фона
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        
        //Поток в которм будет выполняться отрисовывание изображения
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffe/ очистка экрана

            // swap the color buffers
            //Указатель на программу, по которой производить отрисовку
            shaderProgram.bind();
            
            glBindVertexArray(vaoId);
            glEnableVertexAttribArray(0);

            // Draw the vertices
            glDrawArrays(GL_TRIANGLES, 0, 3);

            // Restore state
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            
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
