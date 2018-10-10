package htmlGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParse {

	String url;
	String script;
	
	ArrayList<String> arr;
	ArrayList<String> charList;
	ArrayList<String> sceneList;
	JSONObject jsonObject = new JSONObject();
	JSONArray scriptArr = new JSONArray();
	JSONObject scriptInfo = new JSONObject();
	JSONArray sceneArr = new JSONArray();
	JSONObject sceneInfo = new JSONObject();
	JSONArray jsonArr = new JSONArray();
	int sceneStart = 0;
	int sceneEnd = 0;


	Iterator<Element> iterElem = null;
	
	public HtmlParse(String url){
		this.url=url;
		script = new String();
		arr = new ArrayList<String>();
		charList = new ArrayList<String>();
		sceneList = new ArrayList<String>();
		
		parse(url);
		

		
		
	}
	

	
	private void parse(String url){
		
		try {
			Document doc =  Jsoup.connect(url).get();
			//script is in <pre> tag
			Elements scripts=doc.select("pre");
			script=scripts.toString();
			//System.out.print(script);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		script = script.replaceAll("</b>", "");
		
		String[] linarr=script.split("\n");
		JSONArray scriptarr = new JSONArray();
		int count=0;
		sceneInfo = new JSONObject();
		sceneInfo.put("SceneNum", Integer.toString(count));
		sceneInfo.put("SceneInfo", "");
		sceneInfo.put("SceneDescription","");
		
		for(int i =0;i<linarr.length;i++){
			
			// when speaking
			if(linarr[i].startsWith("<b>") && linarr[i+1].trim().length()>3 && !linarr[i+1].contains("<b>")){
				
				linarr[i]=linarr[i].replace("<b>", "");
				String temp = new String(getBracket(linarr[i]));
				scriptInfo = new JSONObject();
				String contents = new String();
				String subject = new String();
				subject= linarr[i].replaceAll("\\("+temp+"\\)", "");
				
				subject = subject.trim();
				String bracket = new String();
				int linecount=i+1;
				
				while(linecount<linarr.length &&linarr[linecount].trim().length()>3){
					if(linarr[linecount].contains("(") && linarr[linecount].contains(")")){
						bracket = bracket.concat(getBracket(linarr[linecount])+". ");
						linarr[linecount] = linarr[linecount].replaceAll("\\("+bracket+"\\)", "");
					}
					contents = contents.concat(linarr[linecount]);
					linecount++;
				
				}
				scriptInfo.put("Type", "speak");
				scriptInfo.put("Subject", subject);
				scriptInfo.put("Contents", contents);
				scriptInfo.put("Description", bracket);
				scriptarr.add(scriptInfo);
				
				i = linecount;
				continue;
			}else if(linarr[i].startsWith("<b>")){
				jsonObject = new JSONObject();
				jsonObject.put("scene", sceneInfo);
				jsonObject.put("script", scriptarr);
				jsonArr.add(jsonObject);
				
				scriptarr = new JSONArray();
				sceneInfo = new JSONObject();
				sceneInfo.put("SceneNum", Integer.toString(count));
				linarr[i] = linarr[i].trim();
				sceneInfo.put("SceneInfo", linarr[i].replaceAll("<b>", ""));
				count++;
				
				int linecount = i+1;
				String description = new String();
				while(linecount<linarr.length && !linarr[linecount].contains("<b>")){
					description = description.concat(linarr[linecount].trim());
					linecount++;
				}
				sceneInfo.put("SceneDescription", description);
				
				i=linecount;
				continue;
			}
		}
	 
	}
	
	private String getBracket(String line){
		String contents = new String();
		String result = new String();
		if(line.contains("(") && line.contains(")")){
		int start = line.indexOf("(");
		int end = line.indexOf(")");
		contents = line.substring(start+1, end);
		System.out.println(contents);

		int temp = contents.indexOf("(");
		if(temp!=-1){
			result = result.concat(contents.substring(0, temp));
			result = result.concat(contents.substring(temp+1, contents.length()));
		}else result=contents;
		//System.out.println(result);
		return result;
		}
		
		return result;
	}

	
		

	
	public JSONArray getSceneInfo(){
		return this.jsonArr;
	}
	public ArrayList<String> getSceneList(){
		return this.sceneList;
	}
	
	public ArrayList<String> getCharList(){
		return this.charList;
	}
	
	public String script(){
		return this.script;
}
}
