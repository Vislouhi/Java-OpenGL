#  Обводка объекта

## Шейдеры

	String vppShader=
        		"#version 330 \n"+
        		//location = 0 идентификатор для отправки данных в видеоадаптер
        		//этой процедурой glVertexAttribPointer(0{location=0}, 3, GL_FLOAT, false, 0, 0);
        		//Прием позиций вершин
				"layout (location = 0) in vec2 position;"+
        		//Прием векторов нормалей к вершинам
        		"out vec2 outTexCoord;"+
				"void main()"+
				"{"+
				//Преобразование координат объекта в соответствии с положением камеры
 				
			    //Применение проекции о отправка координат на отрисовку
				"gl_Position = vec4(position,0.0,1.0);"+
			    //Преобразование векторов нормалей в соответствии с положением камеры
				"outTexCoord=((position)+vec2(1f-2f/300f,1f-5f/300f))/2;"+
				"}";
    	String fppShader="#version 330\r\n" + 
    			"\r\n" + 
    			"out vec4 fragColor;\r\n" + 
    			"in  vec2 outTexCoord;"+
    			"uniform sampler2D texture_sampler;"+
    			"float ofset = 3f/300f; \r\n" + 
    			"void main()\r\n" + 
    			"{\r\n" + 
    			
    			//"if (texture(texture_sampler, outTexCoord).rgb==vec3(0,0,0)){ discard;}"+
    			"vec2 tCoord=outTexCoord+vec2(0,ofset);"+
    			"if (texture(texture_sampler, tCoord+vec2(0,0)).rgb==vec3(0,0,0)){ discard;}"+
    			"if (texture(texture_sampler, tCoord+vec2(0,ofset)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(0,-ofset)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(-ofset,0)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(ofset,0)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(ofset,ofset)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(-ofset,ofset)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(ofset,-ofset)).rgb!=vec3(0,0,0)"
    			+ "&&texture(texture_sampler, tCoord+vec2(-ofset,-ofset)).rgb!=vec3(0,0,0)"
    			
    			+ "){ discard;}"+
    			 "  fragColor = vec4(1,1,1,1);  \r\n" + 
    			"}";
          
## Рисуем две обезьяны

В основной шейдер vShader добавим

	"uniform vec3 translatoin;"+

и заменим

	"vec4 mvPos = modelViewMatrix * vec4(position+translatoin, 1.0);"+

добавим

 shaderProgram.createUniform("translatoin");
 
 В рендере 
 
 	shaderProgram.bind();
	          
            //Вычисление проекционной матрицы
            projectionMatrix = transformation.getProjectionMatrix(FOV, 300, 300, Z_NEAR, Z_FAR);
            //Отправка проекционной матрицы в шейдер
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
            //Вычисление видовой матрицы
            viewMatrix = transformation.getViewMatrix(camera);
            shaderProgram.setUniform("modelViewMatrix", viewMatrix);
            shaderProgram.setUniform("translatoin", new Vector3f(0f,0f,0f));
            //Определение положения источника света
            lightPos = new Vector3f(10.0f,10.0f,-10.0f);
            shaderProgram.setUniform("lightPosition", lightPos);
            //Использование данных загруженной модели
            glBindVertexArray(mesh.getVaoId());
            //Включение доступа к входным данным в шейдере
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(2);
           
            shaderProgram.setUniform("translatoin", new Vector3f(0f,0f,0f));
           
            glStencilFunc(GL_ALWAYS, 1, 0xFF);
  
            
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
            shaderProgram.setUniform("translatoin", new Vector3f(0f,1.5f,1f));
            
            glStencilFunc(GL_ALWAYS, 2, 0xFF);
        	
        	
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
    
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
            
			shaderProgram.unbind();
      
      Перед рисованием контура говорим рисовать контур тольк на фоне шаблона с индексом 1
      
      glStencilFunc(GL_EQUAL, 1, 0xFF);
      
## Упрошение шейдера для загрузки в FBO


	String vForPpShader=
        		"#version 330 \n"+
        		//location = 0 идентификатор для отправки данных в видеоадаптер
        		//этой процедурой glVertexAttribPointer(0{location=0}, 3, GL_FLOAT, false, 0, 0);
        		//Прием позиций вершин
				"layout (location = 0) in vec3 position;"+
        		//Прием векторов нормалей к вершинам
				
        		//Так указывается переменная котороая будет засылатся в шейдер извне
				"uniform mat4 projectionMatrix;"+
				"uniform mat4 modelViewMatrix;"+
				"uniform vec3 translatoin;"+
				//Так объявляются переменные, которые будут отправлены в пиксельный шейдер
				
				"void main()"+
				"{"+
				//Преобразование координат объекта в соответствии с положением камеры
 				"vec4 mvPos = modelViewMatrix * vec4(position+translatoin, 1.0);"+
			    //Применение проекции о отправка координат на отрисовку
				"gl_Position = projectionMatrix * mvPos;"+
			    //Преобразование векторов нормалей в соответствии с положением камеры
				
				"}";
    	
    	String fForPpShader=
				"#version 330 \n"+
				//Указатель на то, что для отображения цвета будет использоваться эта переменная
				"out vec4 fragColor;"+
				//Переменная для импорта положения источника света
				
				"void main()"+
				"{"+
				//Расчет направления падения света на данную вершину
			
				//Вычисление результирующего света
				"fragColor = vec4(1.0, 1.0, 1.0, 1.0);"+
				"}";
				
 				
