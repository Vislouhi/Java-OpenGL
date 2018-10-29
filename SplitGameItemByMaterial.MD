# Деление obj файла на группы

Пусть есть obj файл в котором есть несколько материалов. Тогда gameItem должен содержать в себе столько объектов класса 
MeshFromBuffers сколько материлов есть в этом объекта.

Таким образом, нужно модифицировать клас GameItem, чтобы он содержал массив переменных класса MeshFromBuffers:

    private final MeshFromBuffers[] meshes;
    
И так же следует переписать конструктор

    public GameItem(Mesh[] meshes) {
        
        this.meshes = meshes;
    }
    
Таким образом, мы имее список файлов *.obj каждый из которых загружаем в отдельный gameItem.

Если модель состоит из нескольких материалов тогда создаем массив meshes, и в каждый элемент этого массива грузим данные для соответствующего материала.

        MeshFromBuffers[] meshes;
        for()
        {
             meshBuffers=loadBuffersForCurrentMaterial(currentObjFile);
             meshes[i] = new MeshFromBuffers(meshBuffers);
             
             materialData = loadDataForCurrentMaterial(currentObjFile); 
             meshes[i].setTexture();
             meshes[i].setColor();
        }
        currentGamItem = newGameItem(meshes);
        
Тогда метод gameItem.render() изменится следующим образом.

    public void render()
    {
        setUniform(position);
        setUniform(rotation);
        for(по всем элементам meshes)
        {
             setUniform(meshes[i].isTextured);
             setUniform(meshes[i].getTexture);
             setUniform(meshes[i].getColor);
             glEnableVertexAttribArray();
             glDrawElements();
             glDisableVertexAttribArray();
        }
    }
    
    
Дополнительно:

Рабочий код под андроид:


https://github.com/Vislouhi/Java-OpenGL/blob/master/src/GameItem.java






