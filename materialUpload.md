# Передача пармаетров материала в шейдер

Содадим класс Material

https://github.com/Vislouhi/Java-OpenGL/blob/master/src/Material.java

Этот калсс хранит в себе цвета и текстуру.

Теперь будем передавать содержимое этого класса в шейдер.

Создадим в шейдере структуру

   struct Material
    {
      vec4 ambient;
      vec4 diffuse;
      vec4 specular;
      int hasTexture;
      float reflectance;
    };
