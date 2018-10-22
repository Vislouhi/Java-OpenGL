# Передача пармаетров материала в шейдер

Содадим класс Material

https://github.com/Vislouhi/Java-OpenGL/blob/master/src/Material.java

Этот калсс хранит в себе цвета и текстуру.

Теперь будем передавать содержимое этого класса в шейдер.

Создадим в пиксельном шейдере (fShader) структуру

    struct Material
    {
      vec4 ambient;
      vec4 diffuse;
      vec4 specular;
      int hasTexture;
      float reflectance;
    };
    
 И объявим переменную этой структуры
 
      uniform Material material;
     
В класс ShaderProgram добавим метод загрузки материалов

    public void createMaterialUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".ambient");
        createUniform(uniformName + ".diffuse");
        createUniform(uniformName + ".specular");
        createUniform(uniformName + ".hasTexture");
        createUniform(uniformName + ".reflectance");
    }
    
    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ambient", material.getAmbientColour());
        setUniform(uniformName + ".diffuse", material.getDiffuseColour());
        setUniform(uniformName + ".specular", material.getSpecularColour());
        setUniform(uniformName + ".hasTexture", material.isTextured() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
    }
    
Теперь объявим вектора компонентов света

      vec4 ambientC;
      vec4 diffuseC;
      vec4 speculrC;
      
Теперь добавим процедуру записи в эти вектора значения принятые в шейдер из класса Material

      void setupColours(Material material, vec2 textCoord)
      {  
         if (material.hasTexture == 1)
      {
        ambientC = texture(texture_sampler, textCoord);
        diffuseC = ambientC;
        speculrC = ambientC;
      }
      else
      {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        speculrC = material.specular;
      }
   }
   
  Теперь можно использовать эти цвета для рассчета fragColor.
