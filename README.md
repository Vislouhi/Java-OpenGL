# Java-OpenGL

# Стартовый проект

Проект для эклипс. Загружается через import -> Gradle -> Import existing project.

https://drive.google.com/open?id=157wgpLvwQv77zVeCKuYr238O1qVcDqOy

# Максим и Илья

<h2>Дома.</h2>

Обработка событий мыши описывается в этой статье.

https://tutorialedge.net/java/lwjgl3/lwjgl-3-mouse-current-position-tutorial/

Создается класс MouseHandler.

Создается объект, ответственный за реакцию на события мыши.

private GLFWCursorPosCallback mouseCallback;

В процедуре init() указывается в какой объект будут отсылаться данные о положении курсора.

glfwSetCursorPosCallback(window, mouseCallback = new MouseHandler());

mouseCallback = new MouseHandler() эта запись означает, что прежде, чем процедура glfwSetCursorPosCallback  обработает
объект mouseCallback он инициализируется с помощью конструктора MouseHandler().

Класс можно и не создавать, а воспользоваться такой записью

glfwCursorPosCallback(window, mouseCallback = new GLFWCursorPosCallback() {

	@Override
	public void invoke(long window, double xpos, double ypos) {
		 //Здесь записывается код, который будет выполняться при изменении положения мыши
    		System.out.println("X: " + xpos + " Y: " + ypos);
   	}
	
});

Обработка нажатий на кнопки мыши  производится аналогично

https://github.com/LWJGL/lwjgl3-wiki/wiki/2.6.3-Input-handling-with-GLFW

glfwSetMouseButtonCallback(window, mouseCallback = new GLFWMouseButtonCallback() {

	@Override
	public void invoke(long window, int button, int action, int mods) {
		System.out.println("button: " + button + " action: " + action+ " mods: " + mods);
	}
});


<h2>В аудитории.</h2>

Загузить и отрисовать два объекта.

Для этого придется объявить еще объекты классов objLoader, MeshFromBuffer.

В цикле отрисовки добавить отрисовку нового объекта. Перед отрисовкой нового объекта изменить маркер шаблона.

# Александр и Максим




# Задачи.

1. Модернизировать освещение (добавить блики).

Из этой статьи изъять все необходимое для создание бликов. Полностью импортировать отсюда код не следует.

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter10/chapter10.html


Здесь аналогичная статья, но по русски.

https://habr.com/post/333932/

2. Наложить на объект текстуру.

Для этого потребуется:

 - Подгрузить из файла *.obj координаты наложения текстур.
 
 - Подгрузить из файла саму текстуру
 
 - Загрузить все это в видеопамять
 
 - отредактировать шейдеры.
 
 В помощь эта статья:
 
 https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter07/chapter7.html
 
 3.Сделать прозрачный объект
 
https://habr.com/post/343096/

http://opengl-tutorial.blogspot.com/p/10.html

4. Клик по объекту

http://www.programmersforum.ru/showthread.php?t=198877

5. Наложение теней

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter18/chapter18.html


# Было

Что такое OpenGL?

https://ru.wikipedia.org/wiki/OpenGL

OpenGL в Java.

https://www.lwjgl.org

https://ru.wikipedia.org/wiki/Lightweight_Java_Game_Library

Будем работать с Eclipse.

https://www.eclipse.org/downloads/

Подключение библиотеки.

https://www.youtube.com/watch?v=VH9KAhjXVFM

При запуске возникает ошибка связанная с отсутствием lwjgl.dll.

http://internetaccessmonitor.ru/7864-lwjgl_dll.html

# Программирование.

Запустить проект состоящий из двух классов.

https://github.com/Vislouhi/Java-OpenGL/blob/master/src/Main.java

https://github.com/Vislouhi/Java-OpenGL/blob/master/src/ShaderProgram.java

Программа написана на основе статьи:

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter04/chapter4.html

Добавить в программу отображения 3D сцены с камеры. Для этого использовать следующие статьи:

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter06/chapter6.html

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter08/chapter8.html

Все что касается текстур пока пропустить.

Классы Matrix4f, Vector3f и т.д. находятся в бибилотеке joml. Чтобы подключить эту библиотеку необходимо создать
проект Gradle. Инструкция здесь:

https://www.boraji.com/how-to-create-a-gradle-java-project-in-eclipse

Инструкция по подключению joml в Gradle здесь:

https://github.com/JOML-CI/JOML/wiki#maven-setup-for-desktop



# Загрузить и отобразить объект в формате WaveFront

О формате 

https://ru.wikipedia.org/wiki/Obj

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter09/chapter9.html

Готовая библиотека для импорта файлом obj и mtl.

https://github.com/javagl/Obj

# Добавить освещение

https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter10/chapter10.html

https://habr.com/post/333932/





