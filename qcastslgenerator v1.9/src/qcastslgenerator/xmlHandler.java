package qcastslgenerator;

import java.util.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class xmlHandler extends Thread {
	
	private Vector<game> xmlgames_;
	private String fname_;
	
	public Vector<game> getGameList() {
		return xmlgames_;
	}
	
	private String getTagValue (String sTag, Element eElement) {
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 		Node nValue = (Node) nlList.item(0); 
 
 		return nValue.getNodeValue();    
 	}
	
	public void run() {
		xmlgames_ = new Vector<game>();
		try {
			// This is standard Java XML file reading. 
			// Seems too easy, but that was what the sample code says. 
			// and it works!.
		    File fXmlFile = new File(fname_);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(fXmlFile);
		    doc.getDocumentElement().normalize();
		 
		    // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    NodeList nList = doc.getElementsByTagName("DATA_RECORD");
		 
		    for (int temp = 0; temp < nList.getLength(); temp++) {
		    	sleep(100);	// multi-threaded
		       Node nNode = nList.item(temp);	    
		       if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
		          Element eElement = (Element) nNode;
			      
		          // Generate game object
		          game tempgame = new game(getTagValue("ITEM_NAME",eElement), 
		        		  getTagValue("MANUFACTURER",eElement),
		        		  getTagValue("STATUS",eElement),
		        		  getTagValue("APPROVAL_NUMBER",eElement),
		        		  getTagValue("IMAGE_FILE_NAME",eElement),
		        		  getTagValue("SIG_TYPE",eElement),
		        		  getTagValue("DISPLAY_TYPE",eElement));
		          
		          System.out.println("TSL entry generated for: " + tempgame.getTSLentry());
		          if (tempgame != null )	// why are we checking for null entries? i have no idea. 
		        	  xmlgames_.add(tempgame);
		        }
		    }
		    
		  } catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	// Main Constructor
	public xmlHandler(String str) {
		fname_ = str;
	}
}