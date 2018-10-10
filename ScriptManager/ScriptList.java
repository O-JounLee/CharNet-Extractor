package ScriptManager;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ScriptList {

	public String movieTitle;
	public List<script> scriptList;
	
	public ScriptList(String title){
		this.movieTitle = new String(title);
		scriptList = new ArrayList<script>();
	}
	
	public void add(int sceneNum, JSONObject scriptInfo){
		boolean isExist= false;
		
		for(int i =0;i<scriptList.size();i++){
			if(scriptList.get(i).sceneNum == sceneNum){ 
				isExist = true;
				script script = scriptList.get(i);
				script.add(scriptInfo);
			}
		}
		if(!isExist){
			script script = new script(sceneNum);
			script.add(scriptInfo);
			scriptList.add(script);
		}
		
	}
}
