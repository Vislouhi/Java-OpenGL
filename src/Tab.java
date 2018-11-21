
public class Tab {
	
	private static String shaderProgram="";
	
	private Texture activeTabTexture;
	
	private ActionMap activeTabActionMap;
	
	private List<EditForm> editForms;
	
	private List<GLButton> buttons;
	//Классы Texture, ActionMap, EditForm, GLButton следует создать
	
	public Tab(String iniFile) {
	
		//Загрузка ini
		//https://www.programcreek.com/java-api-examples/?api=org.ini4j.Wini
		//https://mvnrepository.com/artifact/org.ini4j/ini4j/0.5.1
		/* В ini
		 * [html1]
		 * html=instruction.html
		 * html=instruction.map
		 * link=#FF00FF - указатель на данную страницу
		 * 
		 * [html2]
		 * html=instruction.html
		 * html=instruction.map
		 * 
		 * instruction.map - , картинка, где поля ввода и кнопки отмечены особыми цветами
		 * */
	
		
		
		//Преобразование html в ByteBuffer
		
		//Создание списка текстур
		
		//Создание списка карт взаимодействия
		
	}
	
	public void linkProgramm()
	{
		
	}
	
	public void render() 
	{
		
	}
	
	public int getMapColor(int x, int y)
	{
		//Определяем цвет из текущей карты взаимодействий в заданном положении
		return 1;
	}
	
	public void loadPage(int mapColor)
	{
		//Загрузка в текущую текстуру новую, соответствующую mapColor
		//Загрузка в текущую карту взаимодействий новую, соответствующую mapColor
	}
	
	public void changeEditFormText(int mapColor, String text)
	{
		
	}
	
	public void placeCursorToEditForm(int mapColor)
	{
		
	}
	
	public GLButton getButtonByMapColor(int mapColor)
	{
		
	}
	

}
