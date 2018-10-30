Camera camera = new Camera();

//Так запускается поток
camera.start();




void invoke()
{

if (w_key_pressed){  
data.mode=1;
data.walkForvard=ture;
  thrower.fireEvent(data);
}

if (w_key_released){  
data.mode=1;
data.walkForvard=ture;
  
thrower.fireEvent(data);
}

}
