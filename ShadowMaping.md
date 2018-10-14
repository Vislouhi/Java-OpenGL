# ShadowMaping

Воспользуемся этой моделью для отладки алгоритма наложения теней.

https://github.com/Vislouhi/Java-OpenGL/blob/master/model/cubeAndPlane.obj

Сначала загрузим эту модель.

Параметры камеры следующие:

 	camera.setPosition(-9.5f,1.0f,-3.5f);
  	camera.setRotation(20.0f,120.0f,0.0f);
 
 Зададим источник света.
 
 	Vector3f lightPosition = new Vector3f(-2.0f,5.0f,-4.0f);
 
 Зададим направление источника света:
 
	    float lightAngleX = 50.0f;
            float lightAngleY = 120.0f;
            float lightAngleZ = 0;
	    Vector3f lightDir = new Vector3f(lightAngleX, lightAngleY, lightAngleZ);
 
 Создадим вектор равный вектрору положения света, домноженному на настроечную величину.
 
 Vector3f lightPosMulByFactor =new Vector3f(lightPosition.x,lightPosition.y,lightPosition.z).mul(0.1f);
 
 
 Создадим шейдеры для генерации карты теней
 
 	String vShadowShader=
 
  	"#version 330 \n"+
    	"layout (location=0) in vec3 position;"+
    	//Создает вид с положения источника света.
    	"uniform mat4 modelLightViewMatrix;"+
        //Создает ортографическую проекцию куба на плоскость экрана
    	"uniform mat4 orthoProjectionMatrix;"+
    	"void main()"+
    	"{"+
    	 "   gl_Position = orthoProjectionMatrix * modelLightViewMatrix * vec4(position, 1.0f);"+
    	"}";

	String fShadowShader=

	"#version 330" + 
        "out vec4 fragColor;"+
        "void main()" + 
        "{" + 
        " gl_FragDepth = gl_FragCoord.z;" + 
        //Эта строчка нужна для проверки карты теней, в последствии ее следует удалить.
        "fragColor =vec4(0.0,0.0, gl_FragCoord.z,1.0);}" +
        "}";
        
        
Далее прозводится загрузка шейдеров в видеокарту с помощю класса ShaderProgramm.

Теперь, перед тем как войти в цикл рендера, создадим матрицы modelLightViewMatrix и orthoProjectionMatrix.

Создадим класс Transformation. Возьмем готовый класс отсюда:

https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter18/c18-p1/src/main/java/org/lwjglb/engine/graph/Transformation.java

	 //Матрица вида с положения источника света строится исходя из положения источника света и пго направления
	 Matrix4f lightViewMatrix = transformation.updateLightViewMatrix(lightPosMulByFactor,lightDir);
          //Ортографическая матрица строится исходя из размеров кубической(в данном случае) части пространства ограниченной в трех
	  //направлениях
         Matrix4f orthoProjMatrix = transformation.updateOrthoProjectionMatrix(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f);
	 
Теперь подгрузим класс ShadowMap отсюда:

https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter18/c18-p1/src/main/java/org/lwjglb/engine/graph/ShadowMap.java

В цикле рендера все закоментируем. Кроме строчек отвечающих за обновление экрана.
		
	glfwSwapBuffers(window); 
        glfwPollEvents();

Создаем переменную класса ShadowMap.

Вызываем конструктор

	shadowMap = new ShadowMap();

И сразу же припишем деструктор, чтобы в видеопамяти не копились текстуры с картами теней.

	shadowMap.cleanup();
	
Далее работаем между конструктором и деструктором.

Bindим буффер карты теней

	glBindFramebuffer(GL_FRAMEBUFFER, shadowMap.getDepthMapFBO());
	
Создаем экран	
	
	glViewport(0, 0, ShadowMap.SHADOW_MAP_WIDTH, ShadowMap.SHADOW_MAP_HEIGHT);
        glClear(GL_DEPTH_BUFFER_BIT);
	
Подключаем, и сразу отключаем программу генерации буфера карты теней

	shaderShadowProgram.bind();
	
	shaderShadowProgram.unbind();
	
Теперь между bind и unbind Передаем в шейдер матрицы.

 	shaderShadowProgram.setUniform("orthoProjectionMatrix", orthoProjMatrix);
        Matrix4f modelLightViewMatrix = transformation.buildModelViewMatrix(gameItem, lightViewMatrix);
        shaderShadowProgram.setUniform("modelLightViewMatrix", modelLightViewMatrix);
	
И включаем рисование модели:

	glBindVertexArray(gameItem.getMesh().getVaoId());
            
            glEnableVertexAttribArray(0);
            
            glDrawElements(GL_TRIANGLES, gameItem.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);

           
            glDisableVertexAttribArray(0);
           
          glBindVertexArray(0);
	
Отключаем буффер карты теней:

	glBindFramebuffer(GL_FRAMEBUFFER, 0);
	
Теперь временно закоментируем подключение буфера карты теней

	//glBindFramebuffer(GL_FRAMEBUFFER, shadowMap.getDepthMapFBO());
	
В классе ShadowMap исправим размер буфера так чтобы он совпадал с размером окна.

	public static final int SHADOW_MAP_WIDTH = 300;
	public static final int SHADOW_MAP_HEIGHT = 300;
	
Теперь, если все сделанно правильно, должна отобразиться карта теней.

Снимем коментарий с 
	
	//glBindFramebuffer(GL_FRAMEBUFFER, shadowMap.getDepthMapFBO());	

Дополним vShader отрисовки сцены. 

Будем передавать в шейдер эти матрицы

	"uniform mat4 orthoProjectionMatrix;"+
	"uniform mat4 modelLightViewMatrix;"+
	
	//Будем передавать в fShader координаты точки расчитанные с положения источника света
	"out vec4 mlightviewVertexPos;"+
	"mlightviewVertexPos = orthoProjectionMatrix * modelLightViewMatrix * vec4(position, 1.0);"+

Добавим процедуру проверки наличия тени в данной позиции в fShader.

	"float calcShadow(vec4 position)" + 
	"{" + 
	"    float shadowFactor = 1.0;" + 
	"    vec3 projCoords = position.xyz;" + 
	  // Transform from screen coordinates to texture coordinates"
	"    projCoords = projCoords * 0.5 + 0.5;" + 
	"    if ( projCoords.z-0.12 < texture(shadowMap, projCoords.xy).r ) " + 
	"    {" + 
	 // Current fragment is not in shade 
	"        shadowFactor = 0;" + 
	"    }" + 
	"    return 1.0 - shadowFactor;" + 
	"} "
	

	

