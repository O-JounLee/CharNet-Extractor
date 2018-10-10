package ScriptManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.aylien.textapi.responses.Sentiment;

import sentiment.SentimentAnalysis;
import sentiment.Sentiments;

public class script {

	public int sceneNum;
	public int numOfDialogs;
	//public int numOfDescriptions;
	public JSONArray scriptArr;
	public double sentiment;
	
	public script(int sceneNum){
		this.sceneNum=sceneNum;
		this.scriptArr=new JSONArray();
		this.numOfDialogs=0;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void add(JSONObject scriptInfo){
		JSONObject temp = new JSONObject();
		temp.put("character", scriptInfo.get("Subject"));
		temp.put("dialog", scriptInfo.get("Contents"));

		this.scriptArr.add(temp);
		this.numOfDialogs++;
	}
}
