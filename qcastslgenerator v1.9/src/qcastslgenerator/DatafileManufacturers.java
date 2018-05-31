/**
 * 
 */
package qcastslgenerator;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


 
/**
 * @author jracer
 *	
 *	Reads XML formatted file with Manufacturers string names in them
 *		Has manufacturer specific code, i.e. 00 - Ari, 01 - IGT, etc.  
 *		Previously called, i.e. ShuffleMaster, used to be called Stargames and previously to that VIDCO
 *		Short cut names, i.e. Aristocrat - ARI, ALI, Aruze - ARU, PAC, etc. 
 */
public class DatafileManufacturers extends Thread {

	// 1. Read in XML file
	//		1.1 If no XML file exist generate a default template based on ARI
	// 2. Process Manufacturers in XML file
	//		2.1 Only if XML file exist then process the manufacturer's XML file. 
	// 3. Generate the Object "Manufacturer" for current.
	
	private static String DEFAULT_FNAME = "bin/manufacturers.xml";
	
	private Vector<MyManufacturers> manufacturers_vector_;
	
	public Vector<MyManufacturers> getMyManufacturers() {
		return manufacturers_vector_;
	}
		
	public String getManufacturerName(String mName) {
		String output = null;
		
		Iterator<MyManufacturers> it = manufacturers_vector_.iterator();

		while (it.hasNext()) { // We search the vector for the Manufacturer that starts with mName. 
    			MyManufacturers mymanu = (MyManufacturers)it.next();
    		
    			// Now tokenize the Historic Name list to identify current "Licensed" Name.
    			StringTokenizer st = new StringTokenizer(mymanu.getManufacturerHistoricName().toUpperCase(),",");
    		
    			while (st.hasMoreTokens()) {
    			    String tmpStr = st.nextToken();

    			    if (tmpStr.toLowerCase().contains(mName.toLowerCase())) { // Match it
    				// System.out.println("Success! Manufacturer " +  tmpStr + " matches with " + mName);
    				output = mymanu.getManufacturerName(); // Set our output
    				break;
    			    }
    			    
    			    
    			}
    			// Found Code exit out. 
    			if (output != null ) {
    			    break;
    			}
    		}
		return output;
	}
	
	public String getManufacturerCode(String mName) {
	    String output = null;
	    Iterator<MyManufacturers> it = manufacturers_vector_.iterator();

	    while (it.hasNext()) { // We search the vector for the Manufacturer that starts with mName. 
		MyManufacturers mymanu = (MyManufacturers)it.next();
    		// Now tokenize the Historic Name list to identify current "Licensed" Name.
    		StringTokenizer st = new StringTokenizer(mymanu.getManufacturerHistoricName().toUpperCase(),",");
		
    		while (st.hasMoreTokens()) {
    			String tmpStr = st.nextToken();
		
    			if (tmpStr.toLowerCase().contains(mName.toLowerCase())) { // Match it
    				// System.out.println("Success! Manufacturer " +  tmpStr + " matches with " + mName);
    				output = mymanu.getManufacturerCode(); // Set our output for Manufacturer Code
    			}
    				
    			// Found Code exit out. 
    			if (output != null ) {
    			    break;
    			}
    		}
	    }
	
	    return output;
	}
	
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 		Node nValue = (Node) nlList.item(0); 
 
 		return nValue.getNodeValue();    
 	}
	
	public void run() {
		// System.out.println("Reading " + DEFAULT_FNAME);
		manufacturers_vector_ = new Vector<MyManufacturers>();
		try {
		    File fXmlFile = new File(DEFAULT_FNAME);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(fXmlFile);
		    doc.getDocumentElement().normalize();
		 
		    // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    NodeList nList = doc.getElementsByTagName("Manufacturer");
		 
		    for (int temp = 0; temp < nList.getLength(); temp++) {
		    	sleep(10);	// multi-threaded
		    	Node nNode = nList.item(temp);	    
		    	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
		    		Element eElement = (Element) nNode;
			      
		    		// Generate Manufacturer object
		    		MyManufacturers tmpMyManufacturers = new MyManufacturers(getTagValue("Manufacturer_Name",eElement), 
		        		  getTagValue("Code",eElement),
		        		  getTagValue("Short_Name",eElement),
		        		  getTagValue("Historic_Name",eElement));
		          
		    		// System.out.println("Entry for: " + tmpMyManufacturers.getManufacturerInfo());
		    		if (tmpMyManufacturers != null ) // check NULL case
		    			manufacturers_vector_.add(tmpMyManufacturers);
		        }
		    }
		    
		  } catch (java.io.FileNotFoundException e) {
			  System.out.println("File not Found! " + DEFAULT_FNAME + " " + e);
			  System.out.println("Generating Sample XML file. Please populate completely: " + DEFAULT_FNAME);
			  
			  GenerateStandardXMLfile();	// generate it. 
			  
			  System.exit(1);
		  } catch (java.io.IOException e) {
	            System.out.println("Error " + e);
	            System.exit(1);
		  }  catch (ParserConfigurationException e) {
			  System.out.println("Parser Configuration Exception: " + e); 
		  } catch (Exception e) {
			  System.out.println("Something went wrong: " + e); 
		  }
	}
	
	public void GenerateStandardXMLfile() {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
        
			String root = new String("ManufacturerXMLFile");
        
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
		
			// Generate Sample XML file

			Element Manufacturer = document.createElement("Manufacturer");     
        
			Element Manufacturer_Name = document.createElement("Manufacturer_Name");
			Manufacturer_Name.appendChild(document.createTextNode("Aristocrat Technologies Australia"));
		
			Element Code = document.createElement("Code");
			Code.appendChild(document.createTextNode("00"));
			
			Element ShortName = document.createElement("Short_Name");
			ShortName.appendChild(document.createTextNode("ARI"));
		
			Element HistoricName = document.createElement("Historic_Name");
			HistoricName.appendChild(document.createTextNode("Aritocrat Leisure Industries"));
		
			rootElement.appendChild(Manufacturer);
			Manufacturer.appendChild(Manufacturer_Name);
			Manufacturer.appendChild(Code);
			Manufacturer.appendChild(ShortName);
			Manufacturer.appendChild(HistoricName);
		
			// Prepare the output file
			File file = new File(DEFAULT_FNAME);
        
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result =  new StreamResult(file);
		transformer.transform(source, result);
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
		
	// Default Constructor
	DatafileManufacturers() {
	}
}
