package graphBuilder;

import java.util.ArrayList;
import java.util.List;

import charManager.characterList;
import charNetManager.CharNet;
import charNetManager.CharNetList;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class GraphView {

	public Graph<MyNode, MyLink> g;
	public GraphView(CharNetList charNetList, characterList charlist){
		
		 
//		 g = new DirectedSparseMultigraph<MyNode, MyLink>();
//		 // Create some MyNode objects to use as vertices
//		 MyNode n1 = new MyNode(1); 
//		 
//		 MyNode n2 = new MyNode(2); 
//		 MyNode n3 = new MyNode(3);
//		 MyNode n4 = new MyNode(4); 
//		 MyNode n5 = new MyNode(5); // note n1-n5 declared elsewhere.
//		 // Add some directed edges along with the vertices to the graph
//		 g.addEdge(new MyLink(2.0, 48),n1, n2, EdgeType.DIRECTED); // This method
//		 g.addEdge(new MyLink(2.0, 48),n1, n2, EdgeType.DIRECTED); // This method
//		 g.addEdge(new MyLink(2.0, 48),n1, n2, EdgeType.DIRECTED); // This method
//		 g.addEdge(new MyLink(2.0, 48),n1, n2, EdgeType.DIRECTED); // This method
//		 g.addEdge(new MyLink(2.0, 48),n2, n3, EdgeType.DIRECTED);
//		 g.addEdge(new MyLink(3.0, 192), n3, n5, EdgeType.DIRECTED);
//		 g.addEdge(new MyLink(2.0, 48), n5, n4, EdgeType.DIRECTED); // or we can use
//		 g.addEdge(new MyLink(2.0, 48), n4, n2); // In a directed graph the
//		 g.addEdge(new MyLink(2.0, 48), n3, n1); // first node is the source
//		 g.addEdge(new MyLink(10.0, 48), n2, n5);// and the second the destination
	
		//buildOneScene(charNet);
		buildTotalScene(charNetList, charlist);
	}
	
	public void buildTotalScene(CharNetList charNetList, characterList charList){
		 g= new DirectedSparseMultigraph<MyNode, MyLink>();
			
			List<MyNode> nodelist = new ArrayList<MyNode>();
			for(int i = 0;i<charList.characterList.size();i++){
				MyNode node = new MyNode(charList.characterList.get(i), i);
				//System.out.println(charList.characterList.get(i).getName());
				nodelist.add(node);
			}
			for(int i =0;i<charNetList.totalNumOfCharacter;i++){
				for(int j=0;j<charNetList.totalNumOfCharacter;j++){
					if(charNetList.totalCharNet[i][j]!=0){
						int count=charNetList.totalCharNet[i][j];
							g.addEdge(new MyLink(count,count),nodelist.get(i), nodelist.get(j), EdgeType.DIRECTED );
			
						
					}
				}
			}
	
			
	}
	
	public void buildOneScene(CharNet charNet){
		
		 g= new DirectedSparseMultigraph<MyNode, MyLink>();
		
		List<MyNode> nodelist = new ArrayList<MyNode>();
		for(int i = 0;i<charNet.characterList.size();i++){
			MyNode node = new MyNode(charNet.characterList.get(i), i);
			System.out.println(charNet.characterList.get(i).getName());
			nodelist.add(node);
		}
		
		for(int i =0;i<charNet.speaking.size();i++){
			int thisNodeId=0;
			//MyNode thisNode;
			for(int j = 0;j<nodelist.size();j++){
				if(nodelist.get(j).character.getName().equals(charNet.speaking.get(i))){
						
						thisNodeId=nodelist.get(j).id;
					
						for(int k=0;k<nodelist.size();k++){
							if(k==thisNodeId) continue;
							g.addEdge(new MyLink(2.0, 48),nodelist.get(thisNodeId), nodelist.get(k), EdgeType.DIRECTED );
						}
				}
			}
			
		}
	}
}
