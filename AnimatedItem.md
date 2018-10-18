# Анимирование объектов

Рассмотрим случай, когда необходимо повернуть ручку электрического ключа. Пусть при нажатии на ручку, ручка должна плавно повернуться.

Сначала отработаем собственно поворот вокруг заданной точки и заданной оси. В класс GameItem добавим:

    private final Vector3f rotationPoint;
    
    private final Vector3f rotationAxis;
    
    private final Matrix4f rotationMatrix;
    
    public float rotationAngle;

    public void setRotationPoint(float x, float y, float z) {
        this.rotationPoint.x = x;
        this.rotationPoint.y = y;
        this.rotationPoint.z = z;
    }
    
    public void setRotationAxis(float x, float y, float z) {
        this.rotationAxis.x = x;
        this.rotationAxis.y = y;
        this.rotationAxis.z = z;
    }
    
    public void setRotationAngle(float angle) {
        this.rotationAngle=angle;
        
    }
    
    public Matrix4f updateRotationMatrix() {
    	
    	this.rotationMatrix.identity();
    	this.rotationMatrix.translate(rotationPoint);
    	this.rotationMatrix.rotate(rotationAngle,rotationAxis);
    	this.rotationMatrix.translate(-rotationPoint.x,-rotationPoint.y,-rotationPoint.z);
    	return rotationMatrix;
    }

Не забываем дописать новые переменные в конструктор.

После этого в vShader добавляем

	uniform mat4 rotationMatrix;

и домножаем вектор позиций и вектор нормалей на эту матрицу врашения:

	vec4 mvPos = modelViewMatrix*rotationMatrix*vec4(position, 1.0);

	mvVertexNormal = normalize(modelViewMatrix * rotationMatrix*vec4(vertexNormal, 0.0)).xyz;

После этого обычным способом настраиваем передачу матрицы врашения в шейдер предварительно ее вычислив

	    сube.setRotationAxis(0f, 1f, 0f);
            cube.setRotationAngle(rot);
            cube.setRotationPoint(0f,0f,-3f);
            Matrix4f rotationMatrix=cube.updateRotationMatrix();

Теперь должна отобразиться повернутая модель.




Предположим, что эта инструкция по созданию событий уже выполнена:

https://github.com/Vislouhi/Java-OpenGL/blob/master/Events.md

Тогда в классе gameItem имеем метод

    public void handleSceneEvent(EventObject e)    {
		    // Здесь пишем то, что должно выполняться по клику LMB     	  	
    }
    
Теперь здесь нужно запустиь асинхронный поток, который будет через промежутки времени равные 1/fps апдейтить rotationAngle.

Для этого сделаем класс

	public class DynamicMathThreads extends Thread{
	
	private GameItem gI;
	public DynamicMathThreads(GameItem gameItem){
		//Принимаем ссылку на gameItem из которого запускается поток
		this.gI=gameItem;
	}

	@Override
	public void run()	//Этот метод будет выполнен в побочном потоке
	{
		
		int i=0;
		//Апдейтим угол вращения
		while(i<20) {
			gI.rotationAngle+=0.1f;
			i++;
			try {
				Thread.sleep(20);
			}catch (InterruptedException ie) {
			}
			System.out.println("Привет из побочного потока!");
		}

	}
	}
	
Запускаем асинхронный поток так:

	DynamicMathThreads rotThread = new DynamicMathThreads(this);	//Создание потока
	rotThread.start();

Теперь по нажатию LMB должен произойти плавный поворот модели.
