package XMLManager;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.simple.JSONObject;

import MovieManager.Movie;
import ScriptManager.script;
import charManager.character;

public class ScriptXml {

	public ScriptXml(Movie movie){
		 Document doc = new Document();  
		  
		  Element root = new Element("scripts");
		  
		  
		  for(int i=0;i<movie.getScriptList().scriptList.size();i++){
			  
		  script thisScript = movie.getScriptList().scriptList.get(i);
		  
		  Element scene = new Element("scene");
		  Element sceneNum = new Element("sceneNum");
		  Element numOfDialogs = new Element("numOfDialogs");
		  Element scripts = new Element("script");
		 
		  
		  root.addContent(scene);
		  scene.addContent(sceneNum);
		  scene.addContent(numOfDialogs);
		  
		  scene.addContent(scripts);
		  
		  
		  
		  sceneNum.setText(Integer.toString(thisScript.sceneNum));
		  numOfDialogs.setText(Integer.toString(thisScript.numOfDialogs));
		  
		  for(int j = 0;j<thisScript.scriptArr.size();j++){
			  Element character = new Element("character");
			  Element dialog = new Element("dialog");
			  Element startTime = new Element("startTime");
			  Element endTime = new Element("endTime");
			  
			  scripts.addContent(character);
			  scripts.addContent(dialog);
			  scripts.addContent(startTime);
			  scripts.addContent(endTime);
			  JSONObject temp = new JSONObject();
			  temp = (JSONObject) thisScript.scriptArr.get(j);
				
			  character.setText(temp.get("character").toString());
			  dialog.setText(temp.get("dialog").toString());
		  }
		 
		  
		  doc.setRootElement(root);
		 
		 XmlWrite.writeXml(doc, movie.getTitle()+"_script.xml");
	}
	}
}
