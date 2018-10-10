package XMLManager;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import MovieManager.Movie;
import SceneManager.scene;
import charManager.character;

public class SceneXml {
	public SceneXml(Movie movie){
		 Document doc = new Document();  
		  
		  Element root = new Element("scenes");
		  
		  for(int i = 0;i<movie.getSceneList().sceneList.size();i++){
			  scene thisScene = movie.getSceneList().sceneList.get(i);
			  
			  Element scenes = new Element("scene");
			  Element sceneNum = new Element("sceneNum");
			  Element sceneDesc = new Element("sceneDescription");
			  Element sceneInfo = new Element("sceneInfo");
			  Element characters = new Element("characters");
			
			  
			  root.addContent(scenes);
			  scenes.addContent(sceneNum);
			  scenes.addContent(sceneInfo);
			  scenes.addContent(sceneDesc);
			  scenes.addContent(characters);
			  
			  sceneNum.setText(Integer.toString(thisScene.sceneNumber));
			  sceneInfo.setText(thisScene.sceneInfo);
			  sceneDesc.setText(thisScene.sceneDesc);
			  for(int j = 0;j<thisScene.characterList.size();j++){
				  Element character= new Element("character");
				  characters.addContent(character);
				  character.setText(thisScene.characterList.get(j).toString());
			  }
			  
			  
		  }
		  
		 
		  
		  doc.setRootElement(root);
		 
		  XmlWrite.writeXml(doc, movie.getTitle()+"_scene.xml");
	}
}
