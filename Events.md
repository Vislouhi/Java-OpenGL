Создать  классы 

SceneControlData - класс содержащий передаваемые данные 

SceneEvent - класс события

SceneEventListener - класс слушателя

ThrowSceneEvent - клас отправляющий события.

Классы здесь:

https://github.com/Vislouhi/Java-OpenGL/tree/master/src

Т.к. GameItem Слушает события имплементируем в него интерфейс SceneEventListener.

	public class CatchSceneEvent implements SceneEventListener

Создаем источник событий, пока один на всю программу. Описываем его глобально в классе Main.

	ThrowSceneEvent thrower = new ThrowSceneEvent();

Все объекты gameItem, которые слушают события, должны быть занесены в список сразу же, как объект gameItem, был создан.

	thrower.addEventListener(gameItem);

Создаем переменную класса, который хранит передаваемые данные.

	SceneControlData data =new SceneControlData();

Задаем данные в теле методов invoke(), которые вызываются при нажатии на кнопки мыши, или ее перемещении.

	data.name="name";

	data.angle=10.0f;

И тут же вызываем событие с отправкой этих данных.

	thrower.fireEvent(data);

Сделаем эту процедуру в классе GameItem. Она будет выполняться если совершилось событие.

    public void handleSceneEvent(EventObject e)    {
		    SceneEvent ev =(SceneEvent) e;
		    System.out.println(ev.toSceneControlData().getName());
	 }

Событие передается сразу всем объектам gameItem, но действие должно происходить только с одним.

Пусть класс SceneControlData содержит переменную 

	public int id;

Тогда перед отправкой события положим в id vaoId, которое было вытащено из рендера при чтении данных шаблона.

	data.id=vaoId;
	
Теперь при приеме события можно проверить относится ли событие к конкретному gameItem.

    public void handleSceneEvent(EventObject e)    {
    		    
		    SceneEvent ev =(SceneEvent) e;
		    if (ev.toSceneControlData().getId()==vaoId){
		       //Здесь описываются изменение параметров gameItem
		    }
	 }
