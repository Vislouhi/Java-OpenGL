# Java-OpenGL
# CHALLENGE

## Условия победы

Должен быть представлен интерактивный эксперимент "Исследование удельного сопротивления проводников"

### Визуализация

Должно работать отображение цветов и текстур, отбражение теней. Сцена освещена несколькими точечными источниками света. Свет имеет три компоненты: ambient, diffuse, specular.

### Нваигация

Персонаж находится в комнате со столом, на котором расположена экспериментальная установка. Вид от первого лица. Перемещение по комнате осуществляется с помощью WSAD. При наведении прицела на объект, под прицелом появляется надпись, говорящая о том какие действия могут быть произведены с объектом. В режиме соединения проводов клеммы должны подсвечиваться. Замыкание ключа должно происходить плавно. При замкнутой цепи стрелка амперметра должна отклоняться соответственно ситуации. При перемещении крокодила вдоль провода, провод должен вести себя реалистично.

# Александр Бугуев

План работы будет дополняться здесь.



https://github.com/Vislouhi/Java-OpenGL/blob/master/TODO/AlexandrBuguev.md

# Максим Кузовлев

Создать алгоритм обработки теней. Инструкция здесь:

https://github.com/Vislouhi/Java-OpenGL/blob/master/ShadowMaping.md

Вывод HUD

https://github.com/Vislouhi/Java-OpenGL/blob/master/HUD.md

# Мария Трушникова

Здесь таймер:

https://github.com/Vislouhi/Java-OpenGL/blob/master/src/FpsMain.java

Возможна ситуация, когда компьютер не тянет заданный FPS. Таким образом, необходимо настроить пропуск рендера, если предыдущий рендер не успел вовремя завершиться.

Создать файл загрузки модели (в блокноте). Загрузить его в java и отпрасить. Создать класс для хранения данных и занести все изъятые данные в него.

Файл здесь:


https://github.com/Vislouhi/Java-OpenGL/blob/master/sceneConfigFile.md






# Стартовый проект

Проект для эклипс. Загружается через import -> Gradle -> Import existing project.

https://drive.google.com/open?id=157wgpLvwQv77zVeCKuYr238O1qVcDqOy

Для запуска нужно установить JDK11. И положить файл lwjgl.dll в папку C:/program files/java/jdk-11/bin.

Файл lwjgl.dll взять здесь:
https://www.lwjgl.org/browse/stable/windows/x64

Система консультирования выглядит так. В телеграмм пишется вопрос и прилагаются скриншоты или фотографии экрана, связанные с вопросом. Будет удобно, если ваши свежие коды будут выложены в github. Так будет легче разобраться с вопросом если ответ на него не очевиден.

Проект будем хранить на github. Сохранять и загружать проект будем через эклипс.

Справка по загрузке проекта на github здесь:

http://wiki.eclipse.org/EGit/User_Guide#Basic_Tutorial:_Adding_a_project_to_version_control


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





