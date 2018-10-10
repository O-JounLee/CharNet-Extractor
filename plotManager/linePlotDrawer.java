/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plotManager;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import charManager.characterList;
import graphVisualizer.graphBuilder;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

/**
 *
 * @author user
 */
public class linePlotDrawer {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void drawLinePlot(int totalLengthOfMovie, int[] data, String title, int interval){
		Stage stage =  new Stage();
		
		stage.setTitle(title);
		//defining the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time");
		//creating the chart
		final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
            
		lineChart.setTitle(title);
		//defining a series
		@SuppressWarnings("rawtypes")
		XYChart.Series series = new XYChart.Series();
		//series.setName("My portfolio");
    
		//populating the series with data        
		for (int i = interval; i < totalLengthOfMovie; i++ /*= i + interval*/) {
			//System.out.println(newXMLparser.occurrenceFreq[i]);
			if((i == totalLengthOfMovie-1) || (i == 0)) series.getData().add(new XYChart.Data(i, data[i]));
			else if(data[i] != data[i+1]) series.getData().add(new XYChart.Data(i, data[i]));
			else if(data[i] != data[i-1]) series.getData().add(new XYChart.Data(i, data[i]));
		}
    
		Scene scene  = new Scene(lineChart,1000,600);
		lineChart.setCreateSymbols(false);
		lineChart.getData().add(series);
   
		stage.setScene(scene);
		stage.show();
	}

	
}
