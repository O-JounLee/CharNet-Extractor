package charNetManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import MovieManager.Movie;
import MovieManager.MovieList;
import charManager.character;
import charManager.characterList;
import graphVisualizer.graphBuilder;

public class CharNet {

	public int sceneNum;
	public List<character> characterList; //Every character appears in this scene
	public int[] characterWeight;
	public List<String> speaking; // ex. {MIA, SEVASTIAN, MIA, MIA'sMOM, ... }
	
	public int[][] characterNet;
	public int[][] accumulativeCharNet;
	public boolean[] occurrence;
	
	//public int[][] lengthOfCoOccurrence;
	//public int[][] freqOfCoOccurrence;
	//public boolean[][] areTheyMet;
	//public boolean[] appearenceList;
	
	public double[] centralityCombination;
	public double[] betweenessCentrality;
	public double[] degreeCentrality;
	public double[] closenessCentrality;
	
	public double[] accCentralityCombination;
	public double[] accBetweenessCentrality;
	public double[] accDegreeCentrality;
	public double[] accClosenessCentrality;
	
	public int numOfCharacters;
	public int numOfEdges;
	public int timePoint;
	
	public CharNet(int sceneNum, int numOfCharacter){
		this.numOfCharacters=numOfCharacter;
		characterNet = new int[numOfCharacter][numOfCharacter];
		accumulativeCharNet = new int[numOfCharacter][numOfCharacter];
		//occurrence = new boolean[numOfCharacter];
		for(int i = 0;i<numOfCharacter;i++){
			for(int j = 0;j<numOfCharacter;j++){
				characterNet[i][j]=0;
				accumulativeCharNet[i][j]=0;
			}
		}
		
		this.sceneNum = sceneNum;
		
		
	}
	
	public void setCharNet(int[]key, characterList characterlist){
		int count = 0;
		while(key[count]!=0){
			
			int numOfDialogs = characterlist.characterList.get(key[count]-1).numOfDialogs[this.sceneNum];
			this.characterNet[key[count]-1][key[count]-1]=numOfDialogs;
			
			int count2=0;
			while(key[count2]!=0){
				this.characterNet[key[count]-1][key[count2]-1] = numOfDialogs;
				count2++;
			}
//			for(int i =0;key[i]!=0;i++){
//				if(i==count) continue;
//				this.characterNet[key[count]-1][key[i]-1] = numOfDialogs;
//				System.out.println("numOfDialogs = "+numOfDialogs + "key = "+key[count]);
//			}
			
			count++;
		}
	}
	
	public void print(){
		for(int i =0;i<numOfCharacters;i++){
		for(int j = 0;j<numOfCharacters;j++){
			System.out.print(characterNet[i][j]);
		}
		System.out.println();
	}
	}
	public CharNet(int sceneNum, JSONArray scriptarr, characterList charlist){
		
		this.sceneNum = sceneNum;
		this.characterWeight=new int[charlist.getNumOfCharacter()];
		Arrays.fill(characterWeight, 0);
		this.speaking=new ArrayList<String>();
		this.characterList=new ArrayList<character>();
		this.characterNet = new int[charlist.getNumOfCharacter()][charlist.getNumOfCharacter()];
		
		for(int i = 0;i<scriptarr.size();i++){
			JSONObject scriptInfo = (JSONObject) scriptarr.get(i);
			if(scriptInfo.get("Type").equals("speak")){
				speaking.add(scriptInfo.get("Subject").toString());
				
			}
		}
		
		for(int i = 0;i<speaking.size();i++){
			
			
			if(!characterList.contains(charlist.getCharacter(speaking.get(i)))) {
				
				characterList.add(charlist.getCharacter(speaking.get(i)));
				
			}
			for(int j = 0;j<characterList.size();j++){
				if(characterList.get(j).getName().equals(speaking.get(i))){
					characterWeight[j]++;
				}
			}
		
		}
				
		
	}
		
}
