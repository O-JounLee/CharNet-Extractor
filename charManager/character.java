/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charManager;

import java.util.Arrays;

/**
 *
 * @author user
 */
public class character {
	
	public String name;
	public int key;
	public boolean[] charOccurrenceList;
	public int []numOfDialogs;
	public int totalNumOfDialogs;
	
	public character(String name, int key, int totalLengthOfMovie){
		this.name=name.trim();
		this.key=key;
		this.charOccurrenceList = new boolean[totalLengthOfMovie+1];
		this.numOfDialogs=new int[totalLengthOfMovie+1];
		this.totalNumOfDialogs=0;
		Arrays.fill(numOfDialogs, 0);
		
		for (int i = 0; i < totalLengthOfMovie+1; i++) {
			this.charOccurrenceList[i] = false;
        }
	}
	
	public void setcharOccurenceList(int sceneNum){
		this.charOccurrenceList[sceneNum]=true;
		this.numOfDialogs[sceneNum]++;
		this.totalNumOfDialogs++;
	}
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
