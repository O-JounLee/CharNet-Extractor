/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphVisualizer;

import java.util.ArrayList;
import java.util.List;

import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;

import charNetManager.CharNet;
import charManager.character;

/**
 *
 * @author user
 */
public class graphBuilder {
    
//    public Workspace workspace;
//    public GraphModel[] graphModels;
//    
//    public graphBuilder(int totalLengthOfMovie){
//        this.graphModels = new GraphModel[totalLengthOfMovie];
//    }

    public void buildGraph(CharNet thisCharNet, List<character> characterList) {
        //Init a project - and therefore a workspace<Character
        ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
        projectController.newProject();
        Workspace workspace = projectController.getCurrentWorkspace();

        //Get a graph model - it exists because we have a workspace
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        //AppearanceModel appearanceModel = Lookup.getDefault().lookup(AppearanceController.class).getModel();

        List<Node> ListNodes = new ArrayList<>();

        for (int i = 0; i < thisCharNet.numOfCharacters; i++) {
//        	if(thisCharNet.appearenceList[i]==true){
//                    ListNodes.add(graphModel.factory().newNode(String.valueOf(i)));
//                    ListNodes.get(i).getNodeData().setLabel(characterList.get(i).name);
//        	}
            ListNodes.add(graphModel.factory().newNode(String.valueOf(i)));
            ListNodes.get(i).getNodeData().setLabel(characterList.get(i).name);
        	
        }
        
        double[][] adjacency = new double[thisCharNet.characterNet.length][thisCharNet.characterNet.length];
        adjacency = nomarlizeMatrix(thisCharNet.characterNet);

        List<Edge> edg = new ArrayList<>();
        for (int i = 0; i < thisCharNet.numOfCharacters; i++) {
            for (int j = i + 1; j < thisCharNet.numOfCharacters; j++) {
                if (thisCharNet.characterNet[i][j] != 0) {
                    edg.add(graphModel.factory().newEdge(ListNodes.get(i), ListNodes.get(j), (float) adjacency[i][j], false));
                }
            }
        }
        
        //Get a UndirectedGraph now and count edges
        UndirectedGraph undirectedGraph = graphModel.getUndirectedGraph();
        GraphView view = undirectedGraph.getView();
        
        for (int i = 0; i < ListNodes.size(); i++) {
        	/*if(thisCharNet.appearenceList[i]==true){
        		undirectedGraph.addNode(ListNodes.get(i));
        	}*/
        	undirectedGraph.addNode(ListNodes.get(i));
        }
        
        for (int i = 0; i < edg.size(); i++) {
            undirectedGraph.addEdge(edg.get(i));
        }

        //Rank Graph
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        
        GraphDistance distance = new GraphDistance();
        distance.setDirected(false);
        distance.execute(graphModel, attributeModel);
        
        thisCharNet.betweenessCentrality = new double[thisCharNet.numOfCharacters];
        thisCharNet.degreeCentrality = new double[thisCharNet.numOfCharacters];
        thisCharNet.closenessCentrality = new double[thisCharNet.numOfCharacters];
        thisCharNet.centralityCombination = new double[thisCharNet.numOfCharacters];
        
        for (Node node : undirectedGraph.getNodes()) {
        	
            //System.out.println(node.getNodeData().getId());
            int ID = Integer.parseInt(node.getNodeData().getId());
         //   System.out.println("Node " + node.getNodeData().getLabel());
        	
            thisCharNet.betweenessCentrality[ID] = 
        			(Double) node.getAttributes().getValue("Betweenness Centrality");
          //  System.out.println("Betweennes centrality: " + (Double) node.getAttributes().getValue("Betweenness Centrality"));
            
            thisCharNet.degreeCentrality[ID] += undirectedGraph.getDegree(node);
           // System.out.println("Degree centrality: " + undirectedGraph.getDegree(node));
            
            //System.out.println("Criticality: " + (Double) node.getAttributes().getValue("Criticality"));
            thisCharNet.closenessCentrality[ID] = 
            		(Double) node.getAttributes().getValue("Closeness Centrality");
          //  System.out.println("Closeness centrality: " + (Double) node.getAttributes().getValue("Closeness Centrality"));
            //System.out.println("Eigenvector centrality: " + (Double) node.getAttributes().getValue("Eigenvector Centrality") + "\n");
            
            thisCharNet.centralityCombination[ID] = 
            		(thisCharNet.betweenessCentrality[ID] + thisCharNet.degreeCentrality[ID] + thisCharNet.closenessCentrality[ID])/3;
           // System.out.println(thisCharNet.centralityCombination[ID]);
        }

        
        //this.graphModels[timePoint] = graphModel;
        
        
        
    }
    
    
    public void buildAccumulativeGraph(CharNet thisCharNet, List<character> characterList) {
        //Init a project - and therefore a workspace<Character
        ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
        projectController.newProject();
        Workspace workspace = projectController.getCurrentWorkspace();

        //Get a graph model - it exists because we have a workspace
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        //AppearanceModel appearanceModel = Lookup.getDefault().lookup(AppearanceController.class).getModel();

        List<Node> ListNodes = new ArrayList<>();

        for (int i = 0; i < thisCharNet.numOfCharacters; i++) {
//        	if(thisCharNet.appearenceList[i]==true){
//                    ListNodes.add(graphModel.factory().newNode(String.valueOf(i)));
//                    ListNodes.get(i).getNodeData().setLabel(characterList.get(i).name);
//        	}
            ListNodes.add(graphModel.factory().newNode(String.valueOf(i)));
            ListNodes.get(i).getNodeData().setLabel(characterList.get(i).name);
        	
        }
        
        double[][] adjacency = new double[thisCharNet.characterNet.length][thisCharNet.characterNet.length];
        adjacency = nomarlizeMatrix(thisCharNet.accumulativeCharNet);

        List<Edge> edg = new ArrayList<>();
        for (int i = 0; i < thisCharNet.numOfCharacters; i++) {
            for (int j = i + 1; j < thisCharNet.numOfCharacters; j++) {
                if (thisCharNet.accumulativeCharNet[i][j] != 0) {
                    edg.add(graphModel.factory().newEdge(ListNodes.get(i), ListNodes.get(j), (float) adjacency[i][j], false));
                }
            }
        }
        
        //Get a UndirectedGraph now and count edges
        UndirectedGraph undirectedGraph = graphModel.getUndirectedGraph();
        GraphView view = undirectedGraph.getView();
        
        for (int i = 0; i < ListNodes.size(); i++) {
        	/*if(thisCharNet.appearenceList[i]==true){
        		undirectedGraph.addNode(ListNodes.get(i));
        	}*/
        	undirectedGraph.addNode(ListNodes.get(i));
        }
        
        for (int i = 0; i < edg.size(); i++) {
            undirectedGraph.addEdge(edg.get(i));
        }

        //Rank Graph
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        
        GraphDistance distance = new GraphDistance();
        distance.setDirected(false);
        distance.execute(graphModel, attributeModel);
        
        thisCharNet.accBetweenessCentrality = new double[thisCharNet.numOfCharacters];
        thisCharNet.accDegreeCentrality = new double[thisCharNet.numOfCharacters];
        thisCharNet.accClosenessCentrality = new double[thisCharNet.numOfCharacters];
        thisCharNet.accCentralityCombination = new double[thisCharNet.numOfCharacters];
        
        for (Node node : undirectedGraph.getNodes()) {
        	
            //System.out.println(node.getNodeData().getId());
            int ID = Integer.parseInt(node.getNodeData().getId());
           // System.out.println("Node " + node.getNodeData().getLabel());
        	
            thisCharNet.accBetweenessCentrality[ID] = 
        			(Double) node.getAttributes().getValue("Betweenness Centrality");
          //  System.out.println("Betweennes centrality: " + (Double) node.getAttributes().getValue("Betweenness Centrality"));
            
            thisCharNet.accDegreeCentrality[ID] += undirectedGraph.getDegree(node);
         //   System.out.println("Degree centrality: " + undirectedGraph.getDegree(node));
            
            //System.out.println("Criticality: " + (Double) node.getAttributes().getValue("Criticality"));
            thisCharNet.accClosenessCentrality[ID] = 
            		(Double) node.getAttributes().getValue("Closeness Centrality");
       //     System.out.println("Closeness centrality: " + (Double) node.getAttributes().getValue("Closeness Centrality"));
            //System.out.println("Eigenvector centrality: " + (Double) node.getAttributes().getValue("Eigenvector Centrality") + "\n");
            
            thisCharNet.accCentralityCombination[ID] = 
            		(thisCharNet.accBetweenessCentrality[ID] + thisCharNet.accDegreeCentrality[ID] + thisCharNet.accClosenessCentrality[ID])/3;
          //  System.out.println(thisCharNet.accCentralityCombination[ID]);
        }

        
        //this.graphModels[timePoint] = graphModel;
        
        
        
    }
    public double[][] nomarlizeMatrix(int[][] lengthOfCoOccurrence) {
        double max = 0;
        double[][] adjacency = new double[lengthOfCoOccurrence.length][lengthOfCoOccurrence.length];
        
        for (int i = 0; i < lengthOfCoOccurrence.length; i++) {
            for (int j = 0; j < lengthOfCoOccurrence.length; j++) {
                if (lengthOfCoOccurrence[i][j] > max) {
                    max = lengthOfCoOccurrence[i][j];
                }
            }
        }
        for (int i = 0; i < lengthOfCoOccurrence.length; i++) {
            for (int j = 0; j < lengthOfCoOccurrence.length; j++) {
            	adjacency[i][j] = (double) lengthOfCoOccurrence[i][j] / max;
            	if(adjacency[i][j] == 0) adjacency[i][j] = 0.001;
            }
        }
        return adjacency;
    }
    
}
