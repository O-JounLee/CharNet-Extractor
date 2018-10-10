package SceneManager;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import charManager.character;

public class scene {
	
	public int sceneNumber;
	public String sceneDesc;
	public String sceneInfo;
	
	public List<String> characterList;
	//public JSONArray scriptArr;
	
	
	public scene( int sceneNumber, String sceneDesc, String sceneInfo){
		this.sceneNumber=sceneNumber;
		this.sceneDesc = new String(sceneDesc);
		this.sceneInfo = new String(sceneInfo);
		characterList = new ArrayList<String>();
		//this.scriptArr = scriptArr;
	}
	
	public void addCharacter(String character){
		boolean isExist = false;
		for(int i = 0;i<characterList.size();i++){
			if(characterList.get(i).equals(character)){
				isExist = true;
			}
		}
		if(!isExist){
		characterList.add(character);
		}
	}
	
	
	

}
