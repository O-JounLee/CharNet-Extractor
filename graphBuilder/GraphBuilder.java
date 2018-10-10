package graphBuilder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import charManager.characterList;
import charNetManager.CharNet;
import charNetManager.CharNetList;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class GraphBuilder {
	
	public GraphBuilder(CharNetList charNet, characterList characterlist){
	GraphView gv=new GraphView(charNet, characterlist);
	
	//SimpleGraphView sgv = new SimpleGraphView(); //We create our graph in here
	 // The Layout<V, E> is parameterized by the vertex and edge types
	 Layout<Integer, String> layout = new CircleLayout(gv.g);
	 layout.setSize(new Dimension(300,300)); // sets the initial size of the space
	 // The BasicVisualizationServer<V,E> is parameterized by the edge types
	 BasicVisualizationServer<Integer,String> vv =
	 new BasicVisualizationServer<Integer,String>(layout);
	 vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size

	 
//	 Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
//		 public Paint transform(Integer i) {
//		 return Color.GREEN;
//		 }
//		 };
//		 
//	 vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	
	 JFrame frame = new JFrame("Simple Graph View");
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 frame.getContentPane().add(vv);
	 frame.pack();
	 frame.setVisible(true); 
	 
	 BetweennessCentrality ranker=new BetweennessCentrality(gv.g);
	  ranker.evaluate();
	  ranker.printRankings(true,true);
	}
	
	
	
	public GraphBuilder(String title){
		
	}
}
