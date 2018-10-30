public class SceneLoader{

public List<GameItemData> gameItemData;

public SceneLoader(String fileName)
{
//Загрузка данных в gameItemData
}

public SceneValues makeSceneValues()
{
//загрузка всех значений с ключем values  в общий список
}

}

public class GameItemData{


//onAction=razomknutCep{setValue=0}
//в ключе находится "razomknutCep" в значении находится матеатическое выражение
public Map<String,String> onAction;
public float value;

public void makeActionsResult(String actionKey,SceneValues sceneValues)
{

  for (onAction)
  {
    if(aactionKey.equals(onAction.key)){
      value=parseMath(onAction.value,sceneValues);
    
    }

}

private void parseMath(String math,SceneValues sceneValues)
{
}

}

}
public class SceneValues{
public Map<String,float> values;
}
