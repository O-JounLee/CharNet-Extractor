package charNetManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;

import charManager.character;
import charManager.characterList;

public class CharNetList {
	
	public String movieTitle;
	public List<CharNet> charNetList;
	public int totalNumOfCharacter;
	public int totalLengthOfMovie;
	public int[][] totalCharNet;
	
	public CharNetList(String title, int totalNumOfCharacter){
		this.movieTitle=title;
		this.totalNumOfCharacter=totalNumOfCharacter;
		charNetList = new ArrayList<CharNet>();
		this.totalCharNet=new int[totalNumOfCharacter][totalNumOfCharacter];
		for(int i =0;i<totalNumOfCharacter;i++){
			for(int j = 0;j<totalNumOfCharacter;j++){
				totalCharNet[i][j]=0;
			}
		}
	}
	
	public void setCharNetwork(characterList characterList, int totalLengthOfMovie){
		this.totalNumOfCharacter=characterList.characterList.size();
		this.totalLengthOfMovie = totalLengthOfMovie;
		
		for(int i = 1;i<totalLengthOfMovie+1;i++){
			CharNet charNet = new CharNet(i, this.totalNumOfCharacter);
			int key[]=new int[this.totalNumOfCharacter];
			int count = 0;
			Arrays.fill(key, 0);
			for(int j = 0;j<this.totalNumOfCharacter;j++){
				character thisCharacter= characterList.characterList.get(j);
				
				
				if(thisCharacter.numOfDialogs[i]>0){
					key[count]=thisCharacter.key;
					count++;
				}
			}
			charNet.setCharNet(key, characterList);
			
			charNetList.add(charNet);
		}
		//set accumulative character network
		for(int i = 0;i<totalLengthOfMovie;i++){
			CharNet thisCharNet= charNetList.get(i);
			if(i!=0){
				CharNet prevCharNet = charNetList.get(i-1);
				for(int j = 0; j<thisCharNet.numOfCharacters;j++){
					for(int k = 0 ;k<thisCharNet.numOfCharacters;k++){
						thisCharNet.accumulativeCharNet[j][k]  = thisCharNet.characterNet[j][k]+prevCharNet.accumulativeCharNet[j][k];
					}
				}
			}else{
				for(int j = 0; j<thisCharNet.numOfCharacters;j++){
					for(int k = 0 ;k<thisCharNet.numOfCharacters;k++){
						thisCharNet.accumulativeCharNet[j][k]=thisCharNet.characterNet[j][k];
					}
				}
			}
		}
	}
	
	public void setTotalCharNet(){
		for(int i = 0;i<charNetList.size();i++){
			for(int j =0;j<totalNumOfCharacter;j++){
				for(int k = 0;k<totalNumOfCharacter;k++){
					totalCharNet[j][k]+=charNetList.get(i).characterNet[j][k];
				}
			}
		}
		
		for(int i =0;i<totalNumOfCharacter;i++){
			this.totalCharNet[i][i]=0;
		}
	}
	
	public int[][] getCharNet(){
		for(int i =0;i<totalNumOfCharacter;i++){
			this.totalCharNet[i][i]=0;
		}
		return this.totalCharNet;
	}
	public void print(){
		for(int i =0;i<totalNumOfCharacter;i++){
			for(int j =0;j<totalNumOfCharacter;j++){
				System.out.print(totalCharNet[i][j]+" ");
			}
			System.out.println();
		}
	}
	public void add(int sceneNum, JSONArray scriptarr, characterList charlist){
		CharNet charNet = new CharNet(sceneNum, scriptarr, charlist);
		charNetList.add(charNet);
	}
	
	public CharNet getCharNet(int sceneNum){
		for(int i = 0;i<charNetList.size();i++){
			if(charNetList.get(i).sceneNum==sceneNum) return charNetList.get(i);
		}
		return null;
	}
}
