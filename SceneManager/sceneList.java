package SceneManager;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import charManager.character;


public class sceneList {
	
	public String movieTitle;
	public List<scene> sceneList;
	
	public sceneList(String title){
		this.movieTitle=title;
		sceneList = new ArrayList<scene>();
	}
	
	public void add(int sceneNum, String sceneDesc, String sceneInfo){
		boolean isExist = false;
		for(int i =0;i<sceneList.size();i++){
			if(sceneList.get(i).sceneNumber == sceneNum) {
				isExist = true;
				scene scene = sceneList.get(i);
	
			}
		}
		
		if(!isExist){
			scene scene = new scene(sceneNum, sceneDesc, sceneInfo);
			sceneList.add(scene);
		}
		
	}
	
	public scene getScene(int sceneNum){
		for(int i =0;i<sceneList.size();i++){
			if(sceneList.get(i).sceneNumber == sceneNum) {
				
				return sceneList.get(i);
			}
		}
		return null;
	}
	


}
