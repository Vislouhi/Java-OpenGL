# Структура рендера

Первая часть рендера - это расчет карты теней. Карта теней - это картинка, которая сохраняется в видеопамяти и в последствии накладывается как текстура.

Для расчета карты теней используется упрощенная shadowShaderProgramm. Рисование в шаблон (stencil) не включается.

После этого начинается рендер сцены на экран. Для простоты алгоритма лучше использовать одну программу mainShaderProgramm. Чтобы программа отличала модель с текстурой и без нее будем передавать в нее флаг isTextured через setUniform().

Класс GameItem содержит методы renderShadow() и render().

Метод renderShadow() содержит процедуру glDrawElements(); и несколько команд setUniform(), чтобы передать rotationMatrix (матрица впащения) и translationMatrix (матрица параллельногопереноса) от данного gameItem.

Метод render() содержит все из метода renderShadow() и в дополнение имеет setUniform(); для загрузки цветов, текстур, коэффициента прозрачности тени.

Метод openShadowMapProc() создает настройки, которые применяются ко всем gameItem одинаково.

Метод closeShadowMapProc() подчищает созданные настройки.



    openShadowMapProc()  
    {
    //Некоторые настройки.
    shadowShaderProgramm.bind();
    shadowShaderProgramm.setUniform();
    }

    for(перебор всех gameItem)
    {

     gameItem.renderShadow();
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
