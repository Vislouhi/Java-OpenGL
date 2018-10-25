glEnable(GL_STENCIL_TEST); 
            
            glClear(GL_STENCIL_BUFFER_BIT);   
       	 glClearStencil(0);
       	   // glPushAttrib(GL_STENCIL_BUFFER_BIT);
       	    glStencilFunc(GL_ALWAYS, 1, 0xFFFFFFFF);
       	    glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
             ByteBuffer buffer = BufferUtils.createByteBuffer(600*600);
            
            

            GL11.glReadPixels(0,0, 600, 600, GL_STENCIL_INDEX, GL_UNSIGNED_BYTE, buffer );
            buffer.rewind();
            
            //int get=buffer.get(0);
            int lastIndex=0;
            int newIndex=0;
            int X=600;
            int Y=600;
            float x=0f;
            float y=0f;
            float[] pos= new float[150];
            float radius=0.2;
            int j=0;
            int k=74;
            boolean f=false;
            FloatBuffer posBuffer;
            posBuffer = MemoryUtil.memAllocFloat(pos.length);
            
            for (int i=0;i<X*Y;i++)
            {
            	
            	if (newIndex!=lastIndex) {
            		y=i/X;
            		x=i%X;
            		lastIndex=newIndex;
            		
            		
            		
            		if(j<75) {
            		if (!f) {	
            		pos[j]=1f-2f*((float)(600-x))/600f;
            		j++;
            		pos[j]=1f-2f*((float)(600-y))/600f;
            		j++;
            		f=!f;
            		}else
            		{
            			pos[k]=1f-2f*((float)(600-x))/600f;
                		k++;
                		pos[k]=1f-2f*((float)(600-y))/600f;
                		k++;
            			f=!f;	
            		}
            		}
            		//System.out.println("Hello LWJGL " +pos[j-1] + "! "+ pos[j-2]);
            	}
            	newIndex=buffer.get(i);
            	
            }
            FloatBuffer posBuffer;
            posBuffer = MemoryUtil.memAllocFloat(pos.length);
            posBuffer.put(pos).flip();
            
            
            buffer.clear();
            /*
            scaleMatrix =gameItem.setScaleMatrixPosition(1.1f);
            shaderProgram.setUniform("scaleMatrix",scaleMatrix);
            shaderProgram.setUniform("frameColor",new Vector3f(1.0f,0f,1f));
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(2);
            //Отрисовка на основе используемых данных
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

            // Restore state
            glDisableVertexAttribArray(0);
            */
            glBindVertexArray(0);
            
			shaderProgram.unbind();
			
			int vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);
            int posVboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, posVboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
			
			shaderFrameProgram.bind();
			
			
			glBindVertexArray(vaoId);

			//viewMatrix = transformation.updateViewMatrix(camera);
			//scaleMatrix =gameItem.setScaleMatrixPosition(1.0f);
			//shaderFrameProgram.setUniform("scaleMatrix",scaleMatrix);
			 shaderFrameProgram.setUniform("frameColor",new Vector3f(1.0f,0f,1f));
	           // shaderFrameProgram.setUniform("translationMatrix", gameItem1.setTranslationMatrix(2f,0f,0f));
	            //shaderFrameProgram.setUniform("projectionMatrix", projectionMatrix);
	           
	            //shaderFrameProgram.setUniform("modelViewMatrix", transformation.updateViewMatrix(camera));
	            //shaderFrameProgram.setUniform("cameraPosition",camera.getPosition());
	           // glLineWidth(	1.0f);
	            glEnableVertexAttribArray(0);
	           
	            //Отрисовка на основе используемых данных
	            glDrawArrays(GL_LINES, 0, 75);

	            // Restore state
	            glDisableVertexAttribArray(0);
              
              
               String vFrameShader=
        		"#version 330 \n"+
        		//location = 0 идентификатор для отправки данных в видеоадаптер
        		//этой процедурой glVertexAttribPointer(0{location=0}, 3, GL_FLOAT, false, 0, 0);
        		//Прием позиций вершин
				"layout (location = 0) in vec2 position;"+
        		//Прием векторов нормалей к вершинам
				
        		//Так указывается переменная котороая будет засылатся в шейдер извне
				
				
				//Так объявляются переменные, которые будут отправлены в пиксельный шейдер
				
				"void main()"+
				"{"+
				//"vec3 dirFromCameraToPoint=normalize(position-cameraPosition);"+
				//Преобразование координат объекта в соответствии с положением камеры
 				
			    //Применение проекции о отправка координат на отрисовку
				"gl_Position = vec4(position.x,position.y,0.5,1.0);"+
			    //Преобразование векторов нормалей в соответствии с положением камеры
				
				"}";
	    
	    String fFrameShader=
				"#version 330 \n"+
				//Указатель на то, что для отображения цвета будет использоваться эта переменная
				"out vec4 fragColor;"+
				//Переменная для импорта положения источника света
				
				"uniform vec3 frameColor;"+
				//Так описываются переменные, которые будут приняты из вершинного шейдера
				
				
				
				
				"void main()"+
				"{"+
				
				"fragColor = vec4(frameColor, 1.0);"+
				"}";
        
         shaderFrameProgram = new ShaderProgram();
	    shaderFrameProgram.createVertexShader(vFrameShader);
	    shaderFrameProgram.createFragmentShader(fFrameShader);
	     
	    shaderFrameProgram.link();
	    
	    
	    //Создание ссылок на переменные типа uniform
	   // shaderFrameProgram.createUniform("projectionMatrix");
	   // shaderFrameProgram.createUniform("modelViewMatrix");
	    //shaderFrameProgram.createUniform("scaleMatrix");
	    //shaderFrameProgram.createUniform("cameraPosition");
	    shaderFrameProgram.createUniform("frameColor");
