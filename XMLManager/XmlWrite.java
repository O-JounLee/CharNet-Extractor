package XMLManager;

import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class XmlWrite {

	
	public XmlWrite(){
			 
	}
	
	public static void writeXml(Document doc, String fileName){
		 try {                                                             
		      FileOutputStream out = new FileOutputStream(fileName); 
		      //xml 파일을 떨구기 위한 경로와 파일 이름 지정해 주기
		      XMLOutputter serializer = new XMLOutputter();                 
		                                                                    
		      Format f = serializer.getFormat();                            
		      f.setEncoding("UTF-8");
		      //encoding 타입을 UTF-8 로 설정
		      f.setIndent(" ");                                             
		      f.setLineSeparator("\r\n");                                   
		      f.setTextMode(Format.TextMode.TRIM);                          
		      serializer.setFormat(f);                                      
		                                                                    
		      serializer.output(doc, out);                                  
		      out.flush();                                                  
		      out.close();    
		      
		      //String 으로 xml 출력
		     // XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("UTF-8")) ;
		     // System.out.println(outputter.outputString(doc));
		  } catch (IOException e) {                                         
		      System.err.println(e);                                        
		  }                                                   

	}
}
