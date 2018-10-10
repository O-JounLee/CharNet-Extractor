package graphBuilder;

import charManager.character;
public class MyNode {

	public character character;
	int id; 
	
	 public MyNode(character character, int id) {
		 this.character=character;
		 this.id = id;
	 }
	 public MyNode(int id){
		 this.id=id;
	 }
	 public MyNode(){
		 
	 }
	 public void setCharacter(character character){
		 this.character=character;
	 }
	 public character getCharacter(){
		 return this.character;
	 }
	 public int getId(){
		 return this.id;
	 }
	 public String getCharacterName(){
		 String name = this.character.getName();
		 return name;
	 }
	 public String toString() { 
	 return "V"+id; 
	 } 
}
