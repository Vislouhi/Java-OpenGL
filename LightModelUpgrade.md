# Модернизация модели освещения для нескольких источников света

Оформим в шейдере рассчет модели освещения в виде процедуры

    vec4 calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
    {
       vec4 diffuseColour = vec4(0, 0, 0, 0);
        vec4 specColour = vec4(0, 0, 0, 0);

        // Diffuse Light
        float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
        diffuseColour = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;

        // Specular Light
        vec3 camera_direction = normalize(-position);
        vec3 from_light_dir = -to_light_dir;
        vec3 reflected_light = normalize(reflect(from_light_dir , normal));
        float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
        specularFactor = pow(specularFactor, specularPower);
        specColour = speculrC * light_intensity  * specularFactor * material.reflectance * vec4(light_colour, 1.0);

        return (diffuseColour + specColour);
   }

Тогда можно будет записать

      fragColor = calcLightColour(vec3(1.0,1.0,1.0),0.5,mvPositions,lightDir,mvNormal);
      
      
Теперь рассмотрим случай нескольких источников света. В принципе можно записать

    fragColor = calcLightColour(vec3(1.0,1.0,1.0),0.5,mvPositions,lightDir1,mvNormal)+
                calcLightColour(vec3(1.0,1.0,1.0),0.5,mvPositions,lightDir2,mvNormal);
                
Однако, такая запись не учитыват растояние до источника света. Учтем ее.

Будем принимать в шейдер положение источника света в виде такой структуры

    //Эта структура описывает параметры затухания света
    struct Attenuation
    {
        float constant;
        float linear;
        float exponent;
    };
    
    struct PointLight
    {
       vec3 colour;
       // Light position is assumed to be in view coordinates
       vec3 position;
       float intensity;
       Attenuation att;
    };
    
Для передачи в шейдер этих данных добавим в ShaderProgramm методы 


        public void createPointLightUniform(String uniformName) throws Exception {
                createUniform(uniformName + ".colour");
                createUniform(uniformName + ".position");
                createUniform(uniformName + ".intensity");
                createUniform(uniformName + ".att.constant");
                createUniform(uniformName + ".att.linear");
                createUniform(uniformName + ".att.exponent");
        }
        
        public void setUniform(String uniformName, PointLight pointLight) {
                setUniform(uniformName + ".colour", pointLight.getColor());
                setUniform(uniformName + ".position", pointLight.getPosition());
                setUniform(uniformName + ".intensity", pointLight.getIntensity());
                PointLight.Attenuation att = pointLight.getAttenuation();
                setUniform(uniformName + ".att.constant", att.getConstant());
                setUniform(uniformName + ".att.linear", att.getLinear());
                setUniform(uniformName + ".att.exponent", att.getExponent());
        }
        
Класс PointLight здесь

https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter12/c12-p1/src/main/java/org/lwjglb/engine/graph/PointLight.java

Теперь в шейдер добавим расчет модели освещения для точечного источника света

    vec4 calcPointLight(PointLight light, vec3 position, vec3 normal)
    {
        vec3 light_direction = light.position - position;
        vec3 to_light_dir  = normalize(light_direction);
        vec4 light_colour = calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);

         // Apply Attenuation
         float distance = length(light_direction);
        float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;
        return light_colour / attenuationInv;
      }
      
 Таким образом, отображение отраженного света стало зависеть от растояния до источника света.
 
 Теперь можно записать
 
    fragColor = calcPointLight(light1,mvPositions,mvNormal)+
                calcPointLight(light2,mvPositions,mvNormal);
 
 
