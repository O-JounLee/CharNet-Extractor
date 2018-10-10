package sentimentManager;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ScriptManager.ScriptList;
import sentiment.SentimentAnalysis;
import sentiment.Sentiments;

public class sentiment {

	int sceneNum;
	JSONArray sentimentList;
	ArrayList<String> charList;
	
	/*
	 * <sentimentList>
	 * from : MIA
	 * to : Sebastian
	 * polarity : Neutral
	 * polarity_confidence : 0.85567
	 *
	*/
	/*public sentiment(int sceneNum, JSONArray scriptArr){
		this.sceneNum= sceneNum;
		this.sentimentList = new JSONArray();
		this.charList = new ArrayList();
		
		for(int i = 0;i<scriptArr.size();i++){
			
			JSONObject temp = new JSONObject();
			temp = (JSONObject) scriptArr.get(i);
			if(!charList.contains(temp.get("character"))){
				charList.add(temp.get("character").toString());
			}
			
		}
		
		setSentiment(scriptArr);
		
	}*/
	
//	public static void main(String[]args){
//		ArrayList<String> charList = new ArrayList();
//		charList.add("MIA");
//		charList.add("SEBA");
//		charList.add("DIRECTOR");
//		String[][] charFromTo = new String[charList.size()*(charList.size()-1)][2];
//		
//		for(int i =0;i<charList.size();i++){
//			for(int j=0;j<charList.size();j++){
//				if(j==i) continue;
//				charFromTo[i][0]=charList.get(i);
//				charFromTo[i][1]=charList.get(j);
//				System.out.println(charFromTo[i][0]+" "+charFromTo[i][1]);
//			}
//		}
//	}
	
	public sentiment(ScriptList scriptList, ArrayList<String> charList){
		set(scriptList, charList);
	}
	private void set(ScriptList scriptList, ArrayList<String> charList){
		
		String[][] charFromTo = new String[charList.size()*(charList.size())][3];
		int count=0;
		for(int i =0;i<charFromTo.length;i=i+charList.size()){
			for(int j=0;j<charList.size();j++){
				//if(j==i) continue;
				charFromTo[i+j][0]=charList.get(count); //From
				charFromTo[i+j][1]=charList.get(j); //To
				charFromTo[i+j][2]=""; //Dialog
				//System.out.println(charFromTo[i][0]+" "+charFromTo[i][1]);
			}
			count++;
		}
		
		for(int i = 0;i<scriptList.scriptList.size();i++){
			JSONArray scriptArr = scriptList.scriptList.get(i).scriptArr;
			ArrayList<String> thisSceneCharacters = new ArrayList();
			
			for(int j = 0;j<scriptArr.size();j++){
				JSONObject temp = (JSONObject) scriptArr.get(j);
				String name = temp.get("character").toString();
				if(!thisSceneCharacters.contains(name)) thisSceneCharacters.add(name);
			} //initialize this scene characters
			
			for(int j = 0;j<scriptArr.size();j++){
				JSONObject temp = (JSONObject) scriptArr.get(j);
				String from = temp.get("character").toString();
				String dialog = temp.get("dialog").toString();
				int fromIdx = thisSceneCharacters.indexOf(from);
			
				for(int k=0;k<charFromTo.length;k++){
					for(int h=0;h<thisSceneCharacters.size();h++){
						if(h==fromIdx) continue;
						if(charFromTo[k][0].equals(from)){
							if(charFromTo[k][1].equals(thisSceneCharacters.get(h))){
							String str = charFromTo[k][2];
							str = str + dialog;
							charFromTo[k][2] = str;
							System.out.println(charFromTo[k][2]);
							}
						}
					}
				}
			}
		}
		
		for(int i=0;i<charFromTo.length;i++){
			//System.out.println(charFromTo[i][0]+ " " + charFromTo[i][1]+" "+charFromTo[i][2]);
			//System.out.println();
			
			if(charFromTo[i][2].length()>1000){
				Sentiments sent = SentimentAnalysis.getSentiment(charFromTo[i][2], "text");
				System.out.println(charFromTo[i][0] + " " + charFromTo[i][1]);
				//System.out.println(charFromTo[i][2]);
			    System.out.println(sent.getPolarity().toString()+" "+ sent.getPolarityConfidence());
			}
			
			
		}
		
	}
	@SuppressWarnings("unchecked")
	private void setSentiment(JSONArray scriptArr){
		
		ArrayList<String> scriptList = new ArrayList();
		
		for(int i =0;i<this.charList.size();i++){
			scriptList.add("");
		}
		
		for(int i = 0;i<scriptArr.size();i++){
			JSONObject temp = new JSONObject();
			temp = (JSONObject) scriptArr.get(i);
			int index = charList.indexOf(temp.get("character").toString());
			String str = new String(scriptList.get(index));
			str = str + " "+temp.get("dialog").toString();
			scriptList.set(index, str);
		}
		
		for(int i =0;i<scriptList.size();i++){
			JSONObject temp = new JSONObject();
			
			Sentiments sent = SentimentAnalysis.getSentiment(scriptList.get(i), "text");
			temp.put("character", this.charList.get(i));
			temp.put("polarity", sent.getPolarity().toString());
			temp.put("polarity_confidence", sent.getPolarityConfidence().toString());
			
			sentimentList.add(temp);
			
		    System.out.println(sent.getText());
		    System.out.println(sent.getPolarity().toString()+" "+ sent.getPolarityConfidence());
		}
	}
}
