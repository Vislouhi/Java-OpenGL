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
		
		
Перед циклом рендера включить смешивание цветов

	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE);

Отключаем режим проверки глубины

	glDepthMask(false);
	
Теперь внутри цикла рендера расчитать матрицу вида на частицу
	
 	Matrix4f modelMatrix = transformation.buildModelMatrix(gameItme);
        viewMatrix.transpose3x3(modelMatrix);
        Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(modelMatrix, viewMatrix);
        modelViewMatrix.scale(Podstavkablend.getScale());

И забросить эту матрицу в видеокарту

        shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
	
Рисуем две частицы
	
	   glBindVertexArray(gameItem.getMesh().getVaoId());
          
           glEnableVertexAttribArray(0);
               
           Vector3f particlePosition = new Vector3f(0.0f,0.0f,0.0f);
           shaderProgram.setUniform("particlePosition",particlePosition);
           
           glDrawElements(GL_TRIANGLES, Podstavkablend.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
           
           particlePosition = new Vector3f(0.5f,0.0f,0.0f);
           shaderProgram.setUniform("particlePosition",particlePosition);
           
           glDrawElements(GL_TRIANGLES, Podstavkablend.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
           
           glDisableVertexAttribArray(0);
           glBindVertexArray(0);



