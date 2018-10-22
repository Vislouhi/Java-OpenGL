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

    struct PointLight
    {
       vec3 colour;
       // Light position is assumed to be in view coordinates
       vec3 position;
        float intensity;
       Attenuation att;
    };
    
Для передачи в шейдер этих данных добавим в ShaderProgramm методы    
