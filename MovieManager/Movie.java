package MovieManager;

import sentimentManager.sentiment;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ExcelWrite.ExcelRead;
import GUIManager.mainFrameHandler;
import SVD.SVD;
import SVD.SortByCentrality;
import SceneManager.sceneList;
import ScriptManager.ScriptList;
import XMLManager.CharacterNetXml;
import XMLManager.CharacterXml;
import XMLManager.SceneXml;
import XMLManager.ScriptXml;
import charManager.character;
import charManager.characterList;
import charNetManager.CharNet;
import charNetManager.CharNetList;
import graphBuilder.GraphBuilder;
import graphVisualizer.graphBuilder;
import graphVisualizer.graphDrawer;
import htmlGetter.HtmlParse;


public class Movie {
	
	private int movieID;
	public String title;
	public String scriptUrl;
	public int totalLengthOfMovie;
	public characterList characterList;
	public sceneList sceneList;
	public CharNetList charNetList;
	public ScriptList scriptList;
	public graphDrawer gd;
	public graphBuilder gb;
	
	@SuppressWarnings("unchecked")
	public Movie(int movieID, String title, String scriptUrl) throws IOException{
		this.movieID = movieID;
		this.title=title;
		this.scriptUrl=scriptUrl;
		this.characterList=new characterList(title);
		this.sceneList = new sceneList(title);
		this.scriptList=new ScriptList(title);
		
		
		HtmlParse test = new HtmlParse(this.scriptUrl);
		JSONArray jsonarr = new JSONArray();
		jsonarr =test.getSceneInfo();
		getScripts(jsonarr);
		

	    CharacterXml cxml=new CharacterXml(this);
	
		
		this.charNetList = new CharNetList(title, this.characterList.numOfCharacters);
		this.charNetList.setCharNetwork(this.characterList, this.totalLengthOfMovie);
		this.charNetList.setTotalCharNet();
		graphBuilder gb = new graphBuilder();
		for(int i = 0;i<this.charNetList.charNetList.size();i++){
			gb.buildGraph(this.charNetList.charNetList.get(i), this.characterList.characterList);
			gb.buildAccumulativeGraph(this.charNetList.charNetList.get(i), this.characterList.characterList);
		}
		
		CharacterNetXml netxml = new CharacterNetXml(this);
		SceneXml scenexml = new SceneXml(this);
		ScriptXml scriptxml = new ScriptXml(this);

		SortByCentrality sbc = new SortByCentrality();
		int[][] sortedCharNet = sbc.sort(this.charNetList.totalCharNet);
		ExcelRead.writeXLSXFile(this.title+".xlsx", sortedCharNet);
			
		this.gd = new graphDrawer();
		mainFrameHandler mf = new mainFrameHandler(this);
		mf.setVisible(true);

		
		
		
	}
	
	private void getScripts(JSONArray jsonarr){
		
		JSONObject tempJsonObj = (JSONObject)jsonarr.get(jsonarr.size()-1);
		JSONObject tempSceneInfo = (JSONObject) tempJsonObj.get("scene");
		
		int totalSceneNum = Integer.parseInt( tempSceneInfo.get("SceneNum").toString());
		
		this.totalLengthOfMovie=totalSceneNum;
		int sceneNum=0;
		
    	for(int i = 0; i < jsonarr.size(); i++){
    		JSONObject jsonObj = (JSONObject)jsonarr.get(i);
    		JSONObject sceneInfo = (JSONObject) jsonObj.get("scene");
    		JSONArray scriptArr = (JSONArray) jsonObj.get("script");
    		sceneNum = Integer.parseInt( sceneInfo.get("SceneNum").toString());
    		System.out.println(sceneInfo);
    		String sceneDesc = new String();
    		String sceneInformation = new String();
    		sceneDesc = sceneInfo.get("SceneDescription").toString();
    		sceneInformation = sceneInfo.get("SceneInfo").toString();
    		sceneList.add(sceneNum, sceneDesc, sceneInformation);
    		
    		for(int j = 0;j<scriptArr.size(); j++){
    			JSONObject scriptInfo = (JSONObject)scriptArr.get(j);
    			if(scriptInfo.get("Type").equals("speak") && scriptInfo.get("Subject").toString().length()>0){
    				characterList.add(scriptInfo.get("Subject").toString(), this.totalLengthOfMovie, sceneNum);
    				scriptList.add(sceneNum, scriptInfo);
    				sceneList.getScene(sceneNum).addCharacter(scriptInfo.get("Subject").toString());

    			}
    		}

    	}

    	
    }

	public characterList getCharacterList(){
		return this.characterList;
	}
	
	public CharNetList getCharacterNetList(){
		return this.charNetList;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public ScriptList getScriptList(){
		return this.scriptList;
	}
	
	public sceneList getSceneList(){
		return this.sceneList;
	}
}
