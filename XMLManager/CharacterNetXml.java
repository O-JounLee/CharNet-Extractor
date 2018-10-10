package XMLManager;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;

import ExcelWrite.ExcelRead;
import MovieManager.Movie;
import charManager.character;
import charNetManager.CharNet;

public class CharacterNetXml {

	public CharacterNetXml(Movie movie){
		 Document doc = new Document();  
		  
		  Element root = new Element("characterNetwork");
		  
		  for(int i=0;i<movie.getCharacterNetList().totalLengthOfMovie;i++){
			  
		  CharNet thisCharNet = movie.getCharacterNetList().charNetList.get(i);
		  
		  Element charNets = new Element("characterNet");
		  
		  Element sceneNum = new Element("sceneNum");
		  Element charNet = new Element("characterNet");
		  Element accumulativeCharNet = new Element("accumulativeCharNet");
		  
		  Element degreeCentrality = new Element("degreeCentrality");
		  Element closenessCentrality = new Element("closenessCentrality");
		  Element betweennessCentrality = new Element("betweennessCentrality");
		  Element centralityCombination = new Element("centralityCombination");
		  
		  Element accDegreeCentrality = new Element("accumulativeDegreeCentrality");
		  Element accClosenessCentrality = new Element("accumulativeClosenessCentrality");
		  Element accBetweennessCentrality = new Element("accumulativeBetweennessCentrality");
		  Element accCentralityCombination = new Element("accumulativeCentralityCombination");
		  
		  
		  root.addContent(charNets);
		  charNets.addContent(sceneNum);
		  charNets.addContent(charNet);
		  charNets.addContent(accumulativeCharNet);
		  charNets.addContent(degreeCentrality);
		  charNets.addContent(closenessCentrality);
		  charNets.addContent(betweennessCentrality);
		  charNets.addContent(centralityCombination);
		  charNets.addContent(accDegreeCentrality);
		  charNets.addContent(accClosenessCentrality);
		  charNets.addContent(accBetweennessCentrality);
		  charNets.addContent(accCentralityCombination);
		  
		  sceneNum.setText(Integer.toString(thisCharNet.sceneNum));
		  charNet = this.writeCharNet(charNet, thisCharNet.characterNet);
		  accumulativeCharNet = this.writeCharNet(accumulativeCharNet, thisCharNet.accumulativeCharNet);
		  
		  degreeCentrality = this.writeCentrality(degreeCentrality, thisCharNet.degreeCentrality);
		  closenessCentrality = this.writeCentrality(closenessCentrality, thisCharNet.closenessCentrality);
		  betweennessCentrality = this.writeCentrality(betweennessCentrality, thisCharNet.betweenessCentrality);
		  centralityCombination = this.writeCentrality(centralityCombination, thisCharNet.centralityCombination);
		  
		  accDegreeCentrality = this.writeCentrality(accDegreeCentrality, thisCharNet.accDegreeCentrality);
		  accClosenessCentrality = this.writeCentrality(accClosenessCentrality, thisCharNet.accClosenessCentrality);
		  accBetweennessCentrality = this.writeCentrality(accBetweennessCentrality, thisCharNet.accBetweenessCentrality);
		  accCentralityCombination = this.writeCentrality(accCentralityCombination, thisCharNet.accCentralityCombination);
		  
//		  String charNetExcel = new String(movie.getTitle()+"_charNet.xlsx");
//		  String accCharNetExcel = new String(movie.getTitle()+"_accumulativeCharNet.xlsx");
//		  
//		  try {
//			ExcelRead.writeXLSXFile(charNetExcel, thisCharNet.characterNet);
//			ExcelRead.writeXLSXFile(accCharNetExcel, thisCharNet.accumulativeCharNet);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
//		  charNet.setText(charNetExcel);
//		  accumulativeCharNet.setText(accCharNetExcel);
//		  
		  //centrality not yet completed!
		  
		  doc.setRootElement(root);
		  
		  
		 
		  XmlWrite.writeXml(doc, movie.getTitle()+"_characterNet.xml");
	}
 }
	
	private Element writeCharNet(Element element, int[][] charNet){
		String context = new String();
		
		for(int i = 0;i<charNet.length;i++){
			for(int j=0;j<charNet.length;j++){
				context = context.concat(Integer.toString(charNet[i][j])+"  ");
			}
			context = context.concat("//");
		}
		
		element.setText(context);
		return element;
	}
	
	private Element writeCentrality(Element element, double[] centrality){
		String context = new String();
		
		for(int i = 0;i<centrality.length;i++){
			context = context.concat(Double.toString(centrality[i])+"  ");
		}
		
		element.setText(context);
		return element;
	}
}
