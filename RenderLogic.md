# Структура рендера




    openShadowMapProc()  
    {
    //Некоторые настройки.
    shadowShaderProgramm.bind();
    shadowShaderProgramm.setUniform();
    }

    for(перебор всех gameItem)
    {

     gameItem.render();
    {
            glEnableVertexAttribArray();
            glDrawElements();
            glDisableVertexAttribArray();
    }

    }

    closeShadowMapProc()  
    {

    shadowShaderProgramm.unbind();
     //Некоторые настройки.
     }

      openMainRenderProc()  
      {
      //Некоторые настройки.
      mainShaderProgramm.bind();
      mainShaderProgramm.setUniform();
     }


      for(перебор всех gameItem)
      {

      gameItem.render();
      {
            
            setUniform("isTextured",gameItem.isTextured);
            При этом в шейдере прописываем
                  if (isTextured){
                    fragColor=makeLighting(texture(sampler,position));
                  }else{
                    fragColor=makeLighting(color);
                  }
            
            
            glEnableVertexAttribArray();
            glDrawElements();
            glDisableVertexAttribArray();
      }

      }


      closeMainRenderProc()  
      {
      mainShaderProgramm.unbind();
      //Некоторые настройки.
      }
