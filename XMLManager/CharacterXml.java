package XMLManager;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import MovieManager.Movie;
import charManager.character;

public class CharacterXml {

	public CharacterXml(Movie movie){
		 Document doc = new Document();  
		  
		  Element root = new Element("characters");
		  
		  for(int i=0;i<movie.getCharacterList().numOfCharacters;i++){
			  
		  character thisCharacter = movie.getCharacterList().characterList.get(i);

		  if(thisCharacter.getName().length()<2) continue;
		  Element characters = new Element("character");
		  Element name = new Element("name");
		  Element key = new Element("key");
		  Element occurenceList = new Element("occurenceList");
		  Element numOfDialogs = new Element("numOfDialogs");
		  
		  root.addContent(characters);
		  characters.addContent(name);
		  characters.addContent(key);
		  characters.addContent(occurenceList);
		  characters.addContent(numOfDialogs);
		  
		  name.setText(thisCharacter.getName());
		  key.setText(Integer.toString(thisCharacter.key));
		  numOfDialogs.setText(Integer.toString(thisCharacter.totalNumOfDialogs));
		 
		  
		  String temp = new String();
		 
		
		  for(int j = 1;j<thisCharacter.charOccurrenceList.length;j++){
		
			  if(thisCharacter.charOccurrenceList[j]==true){
				 temp = temp + j+""+" ";
			  }
		  }
		  occurenceList.setText(temp);
		  
		  }
		  
		  doc.setRootElement(root);
		 
		  XmlWrite.writeXml(doc, movie.getTitle()+"_character.xml");
	}
}
