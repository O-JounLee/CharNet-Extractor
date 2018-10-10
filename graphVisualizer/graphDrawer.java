/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphVisualizer;

import charNetManager.CharNet;
import charManager.character;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;

import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantColor;
import org.gephi.preview.types.DependantOriginalColor;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;

import processing.core.PApplet;

/**
 *
 * @author user
 */
public class graphDrawer {
    public graphBuilder gBuilder;
    
    public void graphVisualizer(JPanel Panel, CharNet thisCharNet, List<character> characterList){
    	//gBuilder = graphBuilder;
    	/*//Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();*/
    	
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
        
        
        
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        
        GraphDistance distance = new GraphDistance();
        distance.setDirected(false);
        distance.execute(graphModel, attributeModel);

        //Filter, remove degree < 1
        FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
        DegreeRangeBuilder.DegreeRangeFilter degreeFilter = new DegreeRangeBuilder.DegreeRangeFilter();
        degreeFilter.init(graphModel.getGraph());
        degreeFilter.setRange(new Range(1, Integer.MAX_VALUE));     //Remove nodes with degree < 10
        Query query = filterController.createQuery(degreeFilter);
        view = filterController.filter(query);
        graphModel.setVisibleView(view);    //Set the filter result as the visible view
        
        /*//Filter, keep partition 'Blogarama'. Build partition with 'source' column in the data
        NodePartitionFilter partitionFilter = new NodePartitionFilter(graphModel.getNodeTable().getColumn("source"));
        partitionFilter.unselectAll();
        partitionFilter.addPart("Blogarama");
        Query query2 = filterController.createQuery(partitionFilter);
        GraphView view2 = filterController.filter(query2);
        graphModel.setVisibleView(view2);    //Set the filter result as the visible view
*/        
        //Rank color by Degree
        RankingController rankingController = Lookup.getDefault().lookup(RankingController.class);
        Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
        AbstractColorTransformer colorTransformer = (AbstractColorTransformer) rankingController.getModel().
        getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
        colorTransformer.setColors(new Color[]{new Color(0xFEF0D9), new Color(0xB30000)});
        rankingController.transform(degreeRanking, colorTransformer);
        
        RankingController rc = Lookup.getDefault().lookup(RankingController.class);
        AttributeColumn col = attributeModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS);
        Ranking centralityRanking = rc.getModel().getRanking(Ranking.NODE_ELEMENT, col.getId());
        AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController.getModel().
        getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
        sizeTransformer.setMinSize(5);
        sizeTransformer.setMaxSize(10);
        rankingController.transform(centralityRanking, sizeTransformer);

        //AttributeColumn col = attributeModel.getNodeTable().getColumn(GraphDistance.CLOSENESS);

        //Generate Centrality        
        /*for (Node n : undirectedGraph.getNodes()) {
            Double centrality = (Double) n.getNodeData().getAttributes().getValue(col.getIndex());
            System.out.println(centrality);
        }*/

        

        //Layout 
        //org.gephi.layout.plugin.force.quadtree
        
        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
        layout.setOptimalDistance(50f);

        layout.initAlgo();
        for (int i = 0; i < 50 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        
    	//Visualization
        PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel model = previewController.getModel(workspace);

        model.getProperties().putValue(PreviewProperty.NODE_LABEL_SHOW_BOX, Boolean.FALSE);
        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT,
        		model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(5f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.WHITE));
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.BLACK));
        model.getProperties().putValue(PreviewProperty.NODE_BORDER_COLOR, new DependantColor(Color.GRAY));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, 1f);
        //model.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10f);
        //model.getProperties().putValue(PreviewProperty.VISIBILITY_RATIO, 1f);
        model.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.TRUE);
        //model.getProperties().putValue(PreviewProperty.ARROW_SIZE, 10f);
        model.getProperties().putValue(PreviewProperty.NODE_OPACITY, 100f);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_BOX_OPACITY, 80f);
        model.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.GRAY);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_PROPORTIONAL_SIZE, Boolean.TRUE);
        
        
        //model.getTopLeftPosition().setLocation(new Point(Panel.getX(), Panel.getY()));
        
        //New Processing target, get the PApplet
        //G2DTarget target = (G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
        //final PreviewSketch previewSketch = new PreviewSketch(target);
        previewController.refreshPreview();
        
        
        //New Processing target, get the PApplet
        ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();
        
        
        applet.setSize(Panel.size().width, Panel.size().height);
        applet.init();

        //Refresh the preview and reset the zoom
        //previewController.render(target);
        target.refresh();
        target.resetZoom();

        //Add the applet to a JFrame and display
        //JFrame frame = new JFrame("Test Preview");
        //Panel.setLayout(new BorderLayout());

        //Panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel.add(applet, BorderLayout.CENTER);

        //Panel.pack();
        Panel.setVisible(true);

        
        //model.getTopLeftPosition().setLocation(50, 50);
        //New Processing target, get the PApplet
        /*ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();*/
        
        
        //OKapplet.setSize(500,500);
        /*applet.init();
        target.refresh();
        target.resetZoom();*/

        //Refresh the preview and reset the zoom
        /*try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
        previewController.render(target);
        target.refresh();
        target.resetZoom();*/

        //Add the applet to a JFrame and display
        //applet.setPreferredSize(500, 500);
        /*applet.setSize(1500, 1000);
        Panel.add(applet, BorderLayout.CENTER);
        Panel.setVisible(true);
        Panel.repaint();*/
    }
    
    
    public void graphVisualizer2(JPanel Panel, CharNet thisCharNet, List<character> characterList){
    	//gBuilder = graphBuilder;
    	/*//Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();*/
    	
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
        
        double[][] adjacency = new double[thisCharNet.accumulativeCharNet.length][thisCharNet.accumulativeCharNet.length];
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
        
        
        
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        
        GraphDistance distance = new GraphDistance();
        distance.setDirected(false);
        distance.execute(graphModel, attributeModel);

        //Filter, remove degree < 1
        FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
        DegreeRangeBuilder.DegreeRangeFilter degreeFilter = new DegreeRangeBuilder.DegreeRangeFilter();
        degreeFilter.init(graphModel.getGraph());
        degreeFilter.setRange(new Range(1, Integer.MAX_VALUE));     //Remove nodes with degree < 10
        Query query = filterController.createQuery(degreeFilter);
        view = filterController.filter(query);
        graphModel.setVisibleView(view);    //Set the filter result as the visible view
        
        /*//Filter, keep partition 'Blogarama'. Build partition with 'source' column in the data
        NodePartitionFilter partitionFilter = new NodePartitionFilter(graphModel.getNodeTable().getColumn("source"));
        partitionFilter.unselectAll();
        partitionFilter.addPart("Blogarama");
        Query query2 = filterController.createQuery(partitionFilter);
        GraphView view2 = filterController.filter(query2);
        graphModel.setVisibleView(view2);    //Set the filter result as the visible view
*/        
        //Rank color by Degree
        RankingController rankingController = Lookup.getDefault().lookup(RankingController.class);
        Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
        AbstractColorTransformer colorTransformer = (AbstractColorTransformer) rankingController.getModel().
        getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
        colorTransformer.setColors(new Color[]{new Color(0xFEF0D9), new Color(0xB30000)});
        rankingController.transform(degreeRanking, colorTransformer);
        
        RankingController rc = Lookup.getDefault().lookup(RankingController.class);
        AttributeColumn col = attributeModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS);
        Ranking centralityRanking = rc.getModel().getRanking(Ranking.NODE_ELEMENT, col.getId());
        AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController.getModel().
        getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
        sizeTransformer.setMinSize(5);
        sizeTransformer.setMaxSize(10);
        rankingController.transform(centralityRanking, sizeTransformer);

        //AttributeColumn col = attributeModel.getNodeTable().getColumn(GraphDistance.CLOSENESS);

        //Generate Centrality        
        /*for (Node n : undirectedGraph.getNodes()) {
            Double centrality = (Double) n.getNodeData().getAttributes().getValue(col.getIndex());
            System.out.println(centrality);
        }*/

        

        //Layout 
        //org.gephi.layout.plugin.force.quadtree
        
        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
        layout.setOptimalDistance(50f);

        layout.initAlgo();
        for (int i = 0; i < 50 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        
    	//Visualization
        PreviewController previewController = Lookup.getDefault().lookup(PreviewController.class);
        PreviewModel model = previewController.getModel(workspace);

        model.getProperties().putValue(PreviewProperty.NODE_LABEL_SHOW_BOX, Boolean.FALSE);
        model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT,
        		model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(5f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.WHITE));
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.BLACK));
        model.getProperties().putValue(PreviewProperty.NODE_BORDER_COLOR, new DependantColor(Color.GRAY));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, 1f);
        //model.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10f);
        //model.getProperties().putValue(PreviewProperty.VISIBILITY_RATIO, 1f);
        model.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.TRUE);
        //model.getProperties().putValue(PreviewProperty.ARROW_SIZE, 10f);
        model.getProperties().putValue(PreviewProperty.NODE_OPACITY, 100f);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_BOX_OPACITY, 80f);
        model.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.GRAY);
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_PROPORTIONAL_SIZE, Boolean.TRUE);
        
        
        //model.getTopLeftPosition().setLocation(new Point(Panel.getX(), Panel.getY()));
        
        //New Processing target, get the PApplet
        //G2DTarget target = (G2DTarget) previewController.getRenderTarget(RenderTarget.G2D_TARGET);
        //final PreviewSketch previewSketch = new PreviewSketch(target);
        previewController.refreshPreview();
        
        
        //New Processing target, get the PApplet
        ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();
        
        
        applet.setSize(Panel.size().width, Panel.size().height);
        applet.init();

        //Refresh the preview and reset the zoom
        //previewController.render(target);
        target.refresh();
        target.resetZoom();

        //Add the applet to a JFrame and display
        //JFrame frame = new JFrame("Test Preview");
        //Panel.setLayout(new BorderLayout());

        //Panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel.add(applet, BorderLayout.CENTER);

        //Panel.pack();
        Panel.setVisible(true);

        
        //model.getTopLeftPosition().setLocation(50, 50);
        //New Processing target, get the PApplet
        /*ProcessingTarget target = (ProcessingTarget) previewController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
        PApplet applet = target.getApplet();*/
        
        
        //OKapplet.setSize(500,500);
        /*applet.init();
        target.refresh();
        target.resetZoom();*/

        //Refresh the preview and reset the zoom
        /*try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }
        previewController.render(target);
        target.refresh();
        target.resetZoom();*/

        //Add the applet to a JFrame and display
        //applet.setPreferredSize(500, 500);
        /*applet.setSize(1500, 1000);
        Panel.add(applet, BorderLayout.CENTER);
        Panel.setVisible(true);
        Panel.repaint();*/
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
