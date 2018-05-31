package qcastslgenerator;
/**
 * <p>Title: OLGR Qcas TSL generator</p>
 *
 * <p>Description: Generates TSL file</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author James Aceret
 * @version 1.8
 */

import java.util.*;
import java.io.*;

import msexcelreader.MSexcellreader;

public class qcasTSLgenerator extends Thread {
	
    private String gamesfromCOGSfilename_;
    private String currentTSLfile_;
    private TSLfile tsf_;
    private DatafileManufacturers dfm_;
   
    public void writetoTSLfile(Vector<game> gamevec, String filename) {
    	double increment = gamevec.size() * 0.01;  // 800 * 100% = 8 increment = 1%
    	int counter = 0;
    	BufferedWriter wr = null;
    	System.out.println("\nWritng TSL file: " + filename);
		
    	try {
        	File file = new File(filename);
           	wr = new BufferedWriter(new FileWriter(file));	// we're creating a new file 
           	
           	Iterator<game> it = gamevec.iterator();
       		while (it.hasNext()) { // Process Each Line
       			double prog = counter / increment;
       			
       			game mylocalGame = (game)it.next();
       			if (mylocalGame != null ) {
       				wr.write(mylocalGame.getTSLentry());
       				wr.newLine();
       			}
    			printProgBar((int)prog);
       			counter++;
       		}
    		printProgBar(100);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not Found: " + e);
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                if (wr != null)
                    wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void run() {
    	try {
    		sleep(100);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	System.out.println("New Games Filename is: " + gamesfromCOGSfilename_);
    	System.out.println("Current TSL file is: " + currentTSLfile_);
    	
    	// *********************************************************
    	// This is how we read Manufacturer XML file and process it. 
    	dfm_ = new DatafileManufacturers();
    	dfm_.run();
    	// *********************************************************
	
    	switch (this.filetype_) {
    		case CSV: 
    			// 1. Read the text file
    			FileHandler newgames = new FileHandler(gamesfromCOGSfilename_);
    			// new FileHandler(gamesfromCOGSfilename_);
    			// 	2. Generate new game entries
    			// newgames.run();
    			
    			// 3. Read current TSL file
    			// 4. Write individual TSL file per manufacturer
    			TSLfile mytsf = new TSLfile(currentTSLfile_);
    			mytsf.run();
       
    			// Add new games in the correct "stack" in the TSL file
    			tsf_ = MergeNewGamestoTSLcurrentgames(newgames.getGameStack(), mytsf);
    	    
    			// sort the TSL file
    			tsf_.Sort();
    	 
    			break;
    		case XML:
    			// We are reading an XMLfile
    			// 1. Read the xml file
    			// 2. Generate new game entries
    			xmlHandler myxml = new xmlHandler(gamesfromCOGSfilename_);
    			myxml.run();
		
    			// 3. Read current TSL file
    			// 4. Write individual TSL file per manufacturer
    			TSLfile mytsf2 = new TSLfile(currentTSLfile_);
    			mytsf2.run();
		
    			// Add new games in the correct "stack" in the TSL file
    			tsf_ = MergeNewGamestoTSLcurrentgames(myxml.getGameList(), mytsf2);
        
    			// sort the TSL file
    			tsf_.Sort();
    			break;
    		case XLS:
    			// We are reading XLS file
    			// 1. Read XLS file
    			MSexcellreader xcellReader = new MSexcellreader(gamesfromCOGSfilename_, this.NumberOfGames_);
    			// 2. Generate new game entries
    			xcellReader.run();
	    
    			// Uncomment this to generate XML file for XLS file
    			// xcellReader.SaveXMLfile();

    			// 3. Read current TSL file
    			TSLfile mytsf3 = new TSLfile(currentTSLfile_);
    			mytsf3.run();
    	
    			// Add new games in the correct "stack" in the TSL file
    			tsf_ = MergeNewGamestoTSLcurrentgames(xcellReader.getXLSgames(), mytsf3);
    	    
    			// sort the TSL file
    			tsf_.Sort();
    	      	    
    			break;
    		default: 
    			break;
    	}
    	
    	// tsf_ should now contain all stacks per manufacturer
	    // Write out TSL file per manufacturer
	    writetoTSLfile(tsf_.getARIVec(), "ARI.tsl");	// Use sorted vector of games 
	    writetoTSLfile(tsf_.getARUVec(), "ARU.tsl");
	    writetoTSLfile(tsf_.getAGTVec(), "AGT.tsl");
	    writetoTSLfile(tsf_.getIGTVec(), "IGT.tsl");
	    writetoTSLfile(tsf_.getKONVec(), "KON.tsl");
	    writetoTSLfile(tsf_.getSTAVec(), "STA.tsl");
	    writetoTSLfile(tsf_.getVGTVec(), "VGT.tsl");
	    writetoTSLfile(tsf_.getBALVec(), "BAL.tsl");

	    System.out.println("\n -= COMPLETE! =- ");

    }
    
    private int NumberOfGames_; 
    private ReaderMode filetype_;
    
    public qcasTSLgenerator(String str1, String str2, ReaderMode filetype, int numberofGames) {
    	gamesfromCOGSfilename_ = str1;
    	currentTSLfile_ = str2;
    	NumberOfGames_ = numberofGames; 
    	filetype_ = filetype;
    }
    
    public qcasTSLgenerator(String str1, String str2, ReaderMode filetype) {
    	gamesfromCOGSfilename_ = str1;
    	currentTSLfile_ = str2;
    	filetype_ = filetype;
    }

    public void printProgBar(int percent){
        StringBuilder bar = new StringBuilder("[");

        for(int i = 0; i < 50; i++){
            if( i < (percent/2)){
                bar.append("=");
            }else if( i == (percent/2)){
                bar.append(">");
            }else{
                bar.append(" ");
            }
        }

        bar.append("]   " + percent + "%     ");
        System.out.print("\r" + bar.toString());
    }
    
    // This runs for XML read file only
    // Override Class for MergeNewGamestoTSLcurrentgames(Vector, TSLfile)
    // input: 	newgames - vector of new game objects
    // 			tsf - TSL file object
    // output:	tsf - Updated TSL file object containg new games
    // 
    public TSLfile MergeNewGamestoTSLcurrentgames(Vector<game> newgames, TSLfile tsf) {
    	Vector<game> addedgames = new Vector<game>();
    	Vector<game> badgames = new Vector<game>();
    	Iterator<game> it = newgames.iterator();
    	int counter = 0;
    	
    	double increment = newgames.size() * 0.01;  // 800 * 100% = 8 increment = 1%
    	System.out.println("Merging New Games List to current TSL games list... Please Wait.");
	
        while (it.hasNext()) { // Process Each Line
            game mylocalGame = (game)it.next(); 		
    		double prog = counter / increment;
    		printProgBar((int)prog);
    			
    		if (mylocalGame != null && dfm_ != null) {
        		if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("ARI"))) {
    		    	tsf.addARIgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("ARU"))) {
    		    	tsf.addARUgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("AGT"))) {
    		    	tsf.addAGTgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("VGT"))) {
    		    	tsf.addVGTgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("KON"))) {
    		    	tsf.addKONgame(mylocalGame);
    				addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("STA"))) {
    		    	tsf.addSTAgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("IGT"))) {
    		    	tsf.addIGTgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		    else if (mylocalGame.getManufacturer().equals(dfm_.getManufacturerCode("BAL"))) {
    		    	tsf.addBALgame(mylocalGame);
    		    	addedgames.add(mylocalGame);	// Add this game 
    		    }
    		} else {
    		    if (mylocalGame == null) 
    		    	System.out.println("We have Unknown Game.");

    		    badgames.add(mylocalGame);
    		}
    		counter++;
    	 }
		printProgBar(100);

    	// We print to screen the games that have been added and 
        PrintReport(addedgames, badgames);
    	
    	return tsf;
    }
    
    @SuppressWarnings("unchecked")
    public void PrintReport(Vector<game> g1, Vector<game> g2) {
    	// g1 = added games
    	// g2 = bad games
    	
    	System.out.println("\nSorting game TSL " + g1.toString());
    	Collections.sort(g1, game.GameNameComparator); // Sort by game name
    	Collections.sort(g2, game.GameNameComparator); // Sort by game name

    	int counter=0; 
    	// double increment = g1.size() * 0.01;  // 800 * 100% = 8 increment = 1%
    	BufferedWriter wr = null;
    	java.util.Date date= new java.util.Date();
    	
    	try {
	    	String fname = date.getTime()+"-Added_games.txt";
    		File file = new File(fname);
    		System.out.println("\n\nNow Printing Added Games to: " + fname);
    		wr = new BufferedWriter(new FileWriter(file));	// we're creating a new file 
       	
    		Iterator<game> it = g1.iterator();
    		while (it.hasNext()) { // Process Each Line
    			// double prog = counter / increment;
    			// printProgBar((int)prog);
   			
    			game mylocalGame = (game)it.next();
    			if (mylocalGame != null ) {
    				wr.write(mylocalGame.getTSLentry());
    				wr.newLine();
    			}
    			counter++;
    		}
    		// printProgBar(100);
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("File not Found: " + e);
    	}
    	catch (IOException e) {
    		System.out.println("Error: " + e);
    	} finally {
    		try {
    			if (wr != null)
    				wr.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
	    	
    	if (g2.size() > 0) { // Only Print this if there's something to print. 
        	Iterator<game> it2 = g2.iterator();
    		System.out.println("\n### Bad Games ###");
    		while (it2.hasNext()) { // Process Each Line
    			game mylocalGame = (game)it2.next();
    			System.out.println(mylocalGame.getTSLentry());
    		}   
    	}
    }
    
    // main() Class
    // Command line application for generating TSL files needed for QCAS datafile generation.
    // TSL file format is as follows:
    // 
    // 00,0000069457,15 LIONS MONEY TRAIN II-V                                   ,10193711            ,PS32
    // 00,0000091186,50 DRAGONS GOLDEN GOALS-V                                   ,20280011            ,SHA1
    // 00,0000088046,50 LIONS THUNDERHEART (GEN 7)-V                             ,10360463            ,BLNK 
    // 
    // 
    //	Requirement:
    //		2 arguments:	1. report of new games approved in a correctly formatted file.
    //						2. current TSL file 
    //
    //	Assumptions:
    // The first argument required is an output from COGS in CSV format with the following fields:
    // <GAME NAME>,<MANUFACTURER>,<STATUS>,<DATE APPROVED>,<MARKET>,<APPROVAL NUMBER>,<FILENAME>,<FILETYPE>,<DISPLAY TYPE>
    // Refer to Gavin Rowles for the file
    // 
    
    public static void HelpScreen() {
    	System.out.println("Requires two (2) or three (3) Input Parameters!");
    	System.out.println("Using XML or CSV file? then: ");
    	System.out.println("Try: qcasTSLgenerator <input XLS/CSV file> <current tsl file>");
    	System.out.println("Using XLS file? then: ");
    	System.out.println("Try: qcasTSLgenerator <input XLS file> <current TSL file> <Number of Games to be Added>");
    }
    
    public static void main(String[] args) {
    	ReaderMode filetype = null;
    	try {
    		switch (args.length) {
    			case 0:
    			case 1:
    				HelpScreen();
    				break;
    			case 2: 
    				// Test file exists
    				@SuppressWarnings("unused")
					BufferedReader br = new BufferedReader(new FileReader(args[0]));
    				// Test TSL file exists
    				@SuppressWarnings("unused")
					BufferedReader br2 = new BufferedReader(new FileReader(args[1]));
		    
    				// Main Stuff
    				if ((args[0].toUpperCase().endsWith("XML"))) {
    					filetype = ReaderMode.XML;
    					qcasTSLgenerator qcastslgenerator = new qcasTSLgenerator(args[0], args[1], filetype);
    					qcastslgenerator.run();
    				}
    				else if (args[0].toUpperCase().endsWith("CSV")) {
    					filetype = ReaderMode.CSV;
    					qcasTSLgenerator qcastslgenerator = new qcasTSLgenerator(args[0], args[1], filetype);
    					qcastslgenerator.run();
    				}
    				else {
    			        	System.out.println("Unknown file type:" + args[0]);
        					System.exit(1);	
    				}
    				break;
    			case 3: 
    				// Test file exists
    				@SuppressWarnings("unused")
					BufferedReader br3 = new BufferedReader(new FileReader(args[0]));
    				// Test TSL file exists
    				@SuppressWarnings("unused")
					BufferedReader br4 = new BufferedReader(new FileReader(args[1]));
    			
    				if (args[0].toUpperCase().endsWith("XLS")) {
    					filetype = ReaderMode.XLS;
    					qcasTSLgenerator qcastslgenerator = new qcasTSLgenerator(args[0], args[1], filetype, Integer.parseInt(args[2]));
    					qcastslgenerator.run();
    				}		
    				break;
    			default: 
    			HelpScreen();
    			break;
    		}
    	} catch (java.io.FileNotFoundException e) {
    		System.out.println("File not Found! " + e);
    		System.exit(1);
    	}
		System.exit(0); 
    }
    
    public enum ReaderMode { CSV, XML, XLS } ;
}
