# ParticleSystem

Начать с чистого проекта.

Создать в блендере плоский круг. И импортировать его в Particle.obj. Загрузить его и вывести на экран.

Настроить вид так, чтобы круг был виден немного под углом.

Обновить классы GameItem и Transformations

https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter21/c21-p1/src/main/java/org/lwjglb/engine/graph/Transformation.java

https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter21/c21-p1/src/main/java/org/lwjglb/engine/items/GameItem.java

В классе GameItem все объекты Mesh заменить на MeshFromBuffers.

Отредактировать шейдеры.

  String vShader=
        		"#version 330 \n"+
        		
				"layout (location = 0) in vec3 position;"+
      			"uniform mat4 projectionMatrix;"+
				"uniform mat4 modelViewMatrix;"+
				"uniform vec3 particlePosition;"+
			
				"void main()"+
				"{"+
				"vec4 mvPos = modelViewMatrix * vec4(position.x+particlePosition.x,position.y+particlePosition.y,position.z+particlePosition.z, 1.0);"+
				"gl_Position = projectionMatrix * mvPos;"+
				"}";
        	
    	String fShader=
    			
    			"#version 330  \n"+

    	    	"out vec4 fragColor;"+

    	    	"void main()"+
    	    	"{"+
    	    	   "fragColor = vec4(1.0, 1.0, 1.0, 0.7);"+
    	    	"}";
