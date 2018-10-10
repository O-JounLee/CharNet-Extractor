package SVD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByCentrality {

	
	public SortByCentrality(){

	}
	
	public int[][] sort(int[][] origin){
		int[][]result = new int[origin[0].length][origin[0].length];
		int[] row = new int[origin[0].length];
		int[] col = new int[origin[0].length];
		int[] rowcol = new int[origin[0].length];
		Arrays.fill(row, 0);
		Arrays.fill(col, 0);
		Arrays.fill(rowcol, 0);
		
		for(int i =0;i<origin[0].length;i++){
			for(int j =0;j<origin[0].length;j++){
				row[i]+=origin[i][j];
				col[j]+=origin[j][j];
			}
		}
		
		ArrayList<Integer> rowcolarr = new ArrayList<Integer>();
		for(int i=0;i<row.length;i++){
			rowcol[i]+=row[i]+col[i];
			rowcolarr.add(rowcol[i]);
			//System.out.println(rowcol[i]);
		}
		
		ArrayList<Integer> idx = (ArrayList<Integer>) sortIndex(rowcolarr);
		
		for(int i=0;i<idx.size();i++){
			for(int j =0;j<idx.size();j++){
				result[i][j]=origin[idx.get(idx.size()-1-i)][idx.get(idx.size()-1-j)];
			}
		}
		
		for(int i =0;i<result[0].length;i++){
			for(int j =0;j<result[0].length;j++){
				System.out.print(result[i][j]+" ");
			}
			
			System.out.println();
		}
		
		return result;
	}
	
	public <T extends Comparable<T>> List<Integer> sortIndex(List<T> in) {
	    ArrayList<Integer> index = new ArrayList<>();
	    for (int i = 0; i < in.size(); i++) {
	        index.add(i);
	    }

	    Collections.sort(index, new Comparator<Integer>() {
	        @Override
	        public int compare(Integer idx1, Integer idx2) {
	            return in.get(idx1).compareTo(in.get(idx2));
	        }
	    });

	    return index;
	}
	
	
}
