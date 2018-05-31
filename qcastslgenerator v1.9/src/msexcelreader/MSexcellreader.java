/**
 * 
 */
package msexcelreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import qcastslgenerator.game;

/**
 * @author jracer
 * 
 */
public class MSexcellreader extends Thread {

	private Vector<game> XLSgames_;
	private final String filename_;
	private final int numberofGames_;

	// Default Constructor.
	public MSexcellreader(String str, int newGamesCount) {

		filename_ = str;
		numberofGames_ = newGamesCount;
	}

	public Vector<game> getXLSgames() {
		return XLSgames_;
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = nlList.item(0);

		return nValue.getNodeValue();
	}

	public void SaveXMLfile() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// main elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("main");
			doc.appendChild(rootElement);

			// set attribute to staff element
			// Attr attr = doc.createAttribute("id");
			// attr.setValue("1");
			// staff.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");

			Iterator<game> it = XLSgames_.iterator();

			while (it.hasNext()) {
				game myGame = it.next();

				// datarecord elements
				Element datarecord = doc.createElement("DATA_RECORD");
				rootElement.appendChild(datarecord);

				// itemname elements
				Element itemname = doc.createElement("ITEM_NAME");
				// Remove white spaces
				String mystr = myGame.getGameName().trim();
				// Remove "-V" in item_name"
				String tmpstr = mystr.substring(0, mystr.length() - 2);
				itemname.appendChild(doc.createTextNode(tmpstr));
				datarecord.appendChild(itemname);

				// manufacturer name elements
				Element manufacturer = doc.createElement("MANUFACTURER");
				manufacturer.appendChild(doc.createTextNode(myGame
						.getManufacturer()));
				datarecord.appendChild(manufacturer);

				// status elements
				Element status = doc.createElement("STATUS");
				status.appendChild(doc.createTextNode(myGame.getStatus()));
				datarecord.appendChild(status);

				// approvalnumber elements
				Element approvalnumber = doc.createElement("APPROVAL_NUMBER");
				approvalnumber.appendChild(doc.createTextNode(myGame
						.getApprovalNumber()));
				datarecord.appendChild(approvalnumber);

				// display type elements
				Element displaytype = doc.createElement("DISPLAY_TYPE");
				displaytype.appendChild(doc.createTextNode(myGame
						.getDisplayType()));
				datarecord.appendChild(displaytype);

				// image file name elements
				Element imagefilename = doc.createElement("IMAGE_FILE_NAME");
				imagefilename.appendChild(doc.createTextNode(myGame
						.getFileName()));
				datarecord.appendChild(imagefilename);

				// sig type elements
				Element sigtype = doc.createElement("SIG_TYPE");
				// SIGTYPE IS "BLNK" or "PS32" or "SHA1" we have to fudge
				// this...
				String hftype = "";
				if (myGame.getFileType().equals("BLNK"))
					hftype = "BIN LINK FILE";
				else if (myGame.getFileType().equals("PS32"))
					hftype = "PSA 32";
				else if (myGame.getFileType().equals("SHA1"))
					hftype = "HMAC SHA1";

				sigtype.appendChild(doc.createTextNode(hftype));
				datarecord.appendChild(sigtype);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename_ + ".XML"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("XLS File saved to: " + filename_ + ".XML");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	@Override
	public void run() {
		XLSgames_ = new Vector<game>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(this.filename_));
			XLSgames_ = new Vector<game>();

			Sheet qcomCasinoList = workbook.getSheet(0);

			for (int i = 0; i < this.numberofGames_; i++) {

				Cell itemNameCell = qcomCasinoList.getCell(0, i);
				String itemName = itemNameCell.getContents();

				Cell manufacturerCell = qcomCasinoList.getCell(1, i);
				String manufacturer = manufacturerCell.getContents();

				Cell approvalStatusCell = qcomCasinoList.getCell(2, i);
				String status = approvalStatusCell.getContents();

				Cell statusFromDateCell = qcomCasinoList.getCell(3, i);
				String dateCell = statusFromDateCell.getContents();

				Cell marketCell = qcomCasinoList.getCell(4, i);
				String market = marketCell.getContents();

				Cell AppNumberCell = qcomCasinoList.getCell(5, i);
				String approvalNumber = AppNumberCell.getContents();

				Cell DisplayTypeCell = qcomCasinoList.getCell(6, i);
				String displaytype = DisplayTypeCell.getContents();

				Cell ImagefilenameCell = qcomCasinoList.getCell(7, i);
				String imagefilename = ImagefilenameCell.getContents();

				Cell SigTypeCell = qcomCasinoList.getCell(8, i);
				String sigtype = SigTypeCell.getContents();

				/*
				 * System.out.println("MS excell file for: " + itemName +
				 * " Manufacturer: " + manufacturer + " Approval Status: " +
				 * status + " Status From Date: " + dateCell + " Market: " +
				 * market + " Approval Number: " + approvalNumber +
				 * " Display Type: " + displaytype + " Image File Name: " +
				 * imagefilename + " Sig Type: " + sigtype);
				 */

				game myGame = new game(itemName, manufacturer, status,
						approvalNumber, imagefilename, sigtype, displaytype);

				System.out.println("TSL item created: " + myGame.getTSLentry());

				if (myGame != null) {
					XLSgames_.add(myGame);
				} else {
					System.out.println("Game Created via XLS was null.");
					System.exit(1);
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		switch (args.length) {
		case 0:
			System.out.println("Usage: -h -v");
			break;
		case 1:
			if (args[0].equals("-v")) {
				// Version Number
				System.out.println("v1.0");
			} else if (args[0].equals("-h")) {
				// help line
				System.out
						.println("java -jar qcas-msexcel2XMLconverter.jar <INPUT MSEXCELFILE.XLS> <NUMBER OF NEW GAMES>");
				System.out.println("What it does:");
				System.out
						.println("Converts the XLS file, to the correct XML format that is required by the qcasTSLgenerator program. ");
			} else {
				// Only handle -h & -v ATM.
				System.out.println("Usage: -h -v");
			}
			break;
		case 2:
			try {
				// Test for file exists.
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
				MSexcellreader xcellReader = new MSexcellreader(args[0],
						Integer.parseInt(args[1]));
				xcellReader.run();
				xcellReader.SaveXMLfile();

			} catch (java.io.FileNotFoundException e) {
				System.out.println("File not Found! " + e);
				System.exit(1);
			}
			break;
		default:
			System.out.println("Usage: -h -v");
			System.exit(0);
			break;
		}
	}
}
