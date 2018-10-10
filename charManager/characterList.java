/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charManager;

/**
 *
 * @author user
 */
import java.util.ArrayList;
import java.util.List;


public class characterList {
	
	private String movieTitle;
	public ArrayList<character> characterList;
	//public int totalLengthOfMovie;
	public int numOfCharacters=0;
	
	public characterList(String movieTitle){
		this.characterList=new ArrayList<character>();
		this.movieTitle=movieTitle;
	}
	
	public int getNumOfCharacter(){
		return this.characterList.size();
	}
	
	public void add(String name, int totalLengthOfMovie, int sceneNum){
		boolean isExist=false;
		
		for(int i = 0; i < characterList.size();i++){
			
			if(characterList.get(i).getName().equals(name)) {
				isExist = true;
				character thisCharacter = characterList.get(i);
				thisCharacter.setcharOccurenceList(sceneNum);
				}
			
			
		}
		
		if(!isExist){
			int key = characterList.size()+1;
			character character = new character(name, key, totalLengthOfMovie);
			character.setcharOccurenceList(sceneNum);
			characterList.add(character);
			numOfCharacters++;
		}
	}
	
	public character getCharacter(String name){
		for(int i = 0;i<characterList.size();i++){
			if(characterList.get(i).getName().equals(name)) return characterList.get(i);
		}
		return null;
	}
	
	
}
