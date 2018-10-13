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
        "fragColor =vec4(1.0,1.0, gl_FragCoord.z,1.0);}" +
        "}";
        
        
Далее прозводится загрузка шейдеров в видеокарту с помощю класса ShaderProgramm.

Теперь, перед тем как войти в цикл рендера, создадим матрицы modelLightViewMatrix и orthoProjectionMatrix.

Создадим класс Transformation. Возьмем готовый класс отсюда:

https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter18/c18-p1/src/main/java/org/lwjglb/engine/graph/Transformation.java




