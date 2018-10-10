package MovieManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import graphBuilder.GraphBuilder;

public class MovieList {
	
	public List<Movie> movieList;
	private static MovieList thisInstance=null;
	
	
	private MovieList(){
		movieList = new ArrayList<Movie>();
	}
	
	public static MovieList getInstance(){
		if(thisInstance==null){
			thisInstance = new MovieList();
		}
		return thisInstance;
	}
	
	public void addMovie(String title, String scriptUrl) throws IOException{
		boolean isExist=false;
		for(int i = 0;i<movieList.size();i++){
			if(movieList.get(i).getTitle().equals(title)) isExist=true;
		}
		
		if(!isExist){
		int movieId=movieList.size();
		Movie movie = new Movie(movieId, title, scriptUrl);
		movieList.add(movie);
		}
		
	}
	
	public Movie getMovie(String title){
		
		for(int i = 0;i<movieList.size();i++){
			if(movieList.get(i).getTitle().equals(title)) return movieList.get(i);
		}
		return null;
	}
	public void showGraph(String title, int sceneNum){
		
		Movie movie = getMovie(title);
		//GraphBuilder test=new GraphBuilder(movie.charNetList.getCharNet(sceneNum));
	}

}
