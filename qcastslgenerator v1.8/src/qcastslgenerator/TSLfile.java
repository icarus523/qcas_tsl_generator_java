package qcastslgenerator;
//
//  TSLfile.java
//  qcastslgenerator
//
//  Created by James Aceret on 13/07/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

import java.io.*;
import java.util.*;

//import au.com.bytecode.opencsv.CSVReader;

import TSLgameremover.CSVHelper;

public class TSLfile extends Thread {
	public boolean ReadingComplete_;
	
	public String filename_;

	private DatafileManufacturers dfm_;
	
    private Vector<game> ARI_GamesV_;
    private Vector<game> IGT_GamesV_;
    private Vector<game> KON_GamesV_;
    private Vector<game> AGT_GamesV_;
    private Vector<game> VGT_GamesV_;
    private Vector<game> STA_GamesV_;
    private Vector<game> ARU_GamesV_;
    private Vector<game> BAL_GamesV_;
    
    public int getSize() {
		return ARI_GamesV_.size() 
		+ IGT_GamesV_.size()
		+ KON_GamesV_.size()
		+ AGT_GamesV_.size()
		+ VGT_GamesV_.size()
		+ STA_GamesV_.size()
		+ ARU_GamesV_.size()
		+ BAL_GamesV_.size();
	}
   
    public void removeGame(game g) {
    	// Check For Manufacturer
    	if (g.getManufacturer().equals(dfm_.getManufacturerCode("ARI"))) {
        	Iterator<game> it = ARI_GamesV_.iterator();
        	int i=0;
        	while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				ARI_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
        }
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("IGT"))) {
    		Iterator<game> it = IGT_GamesV_.iterator();
        	int i=0;
    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				IGT_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("KON"))) {
    		Iterator<game> it = KON_GamesV_.iterator();
        	int i=0;
    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				KON_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("AGT"))) {
    		Iterator<game> it = AGT_GamesV_.iterator();
        	int i=0;

    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				AGT_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("VGT"))) {
    		Iterator<game> it = VGT_GamesV_.iterator();
    		int i=0;
    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				VGT_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("STA"))) {
    		Iterator<game> it = STA_GamesV_.iterator();
    		int i=0;
    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				STA_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("ARU"))) {
    		Iterator<game> it = ARU_GamesV_.iterator();
    		int i=0;
    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				ARU_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else if (g.getManufacturer().equals(dfm_.getManufacturerCode("BAL"))) {
    		Iterator<game> it = BAL_GamesV_.iterator();
    		int i=0;
    		while (it.hasNext()) { // Look for the Game
    			game myGame = (game)it.next(); 		
    			if (myGame.getApprovalNumber().equals(g.getApprovalNumber())) {
    				BAL_GamesV_.removeElementAt(i);
    				break;
    			}
    			i++;
    		}
    	}
    	else {
    		System.out.println("Can't remove game: Don't know this manufacturer: " + g.getManufacturer());
    		System.exit(1);
    	}
    }
    
    
    public void setARIVec(Vector<game> v) {
    	ARI_GamesV_ = v;
    }
    
    public void setIGTVec(Vector<game> v) {
    	IGT_GamesV_ = v;
    }
    
    public void setKONVec(Vector<game> v) {
    	KON_GamesV_ = v;
    }
    
    public void setAGTVec(Vector<game> v) {
    	AGT_GamesV_ = v;
    }
    
    public void setVGTVec(Vector<game> v) {
    	VGT_GamesV_ = v;
    }
    
    public void setSTAVec(Vector<game> v) {
    	STA_GamesV_ = v;
    } 
    
    public void setARUVec(Vector<game> v) {
    	ARU_GamesV_ = v;
    } 
    
    public void setBALVec(Vector<game> v) {
    	BAL_GamesV_ = v;
    }
    public Vector<game> getARIVec() {
    	return ARI_GamesV_;
    }

    public Vector<game> getIGTVec() {
    	return IGT_GamesV_;
    }
    
    public Vector<game> getKONVec() {
    	return KON_GamesV_;
    }
    
    public Vector<game> getAGTVec() {
    	return AGT_GamesV_;
    }
    
    public Vector<game> getVGTVec() {
    	return VGT_GamesV_;
    }
    
    public Vector<game> getSTAVec() {
    	return STA_GamesV_;
    }
    
    public Vector<game> getARUVec() {
    	return ARU_GamesV_;
    }
    
    public Vector<game> getBALVec() {
    	return BAL_GamesV_;
    }
        
    public void addBALgame(game g) {
    	Iterator<game> it = BAL_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		BAL_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addARIgame(game g) {
    	Iterator<game> it = ARI_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		ARI_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addIGTgame(game g) {
    	Iterator<game> it = IGT_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		IGT_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addAGTgame(game g) {
    	Iterator<game> it = AGT_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		AGT_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addVGTgame(game g) {
    	Iterator<game> it = VGT_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		VGT_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addKONgame(game g) {
    	Iterator<game> it = KON_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		KON_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addSTAgame(game g) {
    	Iterator<game> it = STA_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		STA_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
    
    public void addARUgame(game g) {
    	Iterator<game> it = ARU_GamesV_.iterator();
    	boolean present = false;
    	
    	while (it.hasNext()) {
    		game gGame = (game)it.next();
    		if (gGame.isEqual(g))
    			present = true;
    	}
    	if (!present)
    		ARU_GamesV_.addElement(g);
    	else System.out.println("\nIgnoring duplicate entry: " + g.getTSLentry());
    }
        
    // Objective: 
    //	Sort the Vectors, by using only the game versions
    @SuppressWarnings("unchecked")
	public void Sort() {
    	
    	System.out.println("\nNow Sorting by Game Name...");
    	
    	// Check for size > 0, otherwise it just loops. 
    	if (ARI_GamesV_.size() > 1) 
    		Collections.sort(ARI_GamesV_, game.GameNameComparator); // Sort by game name
    		// Collections.sort(ARI_GamesV_, game.ApprovalNumberComparator); // Sort by approval number
    	else {
    		System.out.println("Game vector size is < 0" + ARI_GamesV_.toString());
    		System.exit(1);
    	}
    	if (IGT_GamesV_.size() > 1) 
    		Collections.sort(IGT_GamesV_, game.GameNameComparator); // Sort by game name
    	// Collections.sort(IGT_GamesV_, game.ApprovalNumberComparator); // Sort by approval number
    	else {
    		System.out.println("Game vector size is < 0" + IGT_GamesV_.toString());
    		System.exit(1);
    	}
    	
    	if (AGT_GamesV_.size() > 1) 
    		Collections.sort(AGT_GamesV_, game.GameNameComparator); // Sort by game name
    	// Collections.sort(AGT_GamesV_, game.ApprovalNumberComparator); // Sort by approval number
    	else {
    		System.out.println("Game vector size is < 0" + AGT_GamesV_.toString());
    		System.exit(1);
    	}
    	
    	if (STA_GamesV_.size() > 1) 
    		Collections.sort(STA_GamesV_, game.GameNameComparator); // Sort by game name
    	// Collections.sort(STA_GamesV_, game.ApprovalNumberComparator); // Sort by approval number
    	else {
    		System.out.println("Game vector size is < 0" + STA_GamesV_.toString());
    		System.exit(1);
    	}
    	
    	if (VGT_GamesV_.size() > 1) 
    		Collections.sort(VGT_GamesV_, game.GameNameComparator); // Sort by game name
    	// Collections.sort(VGT_GamesV_, game.ApprovalNumberComparator); // Sort by approval number
    	else {
    		System.out.println("Game vector size is < 0" + VGT_GamesV_.toString());
    		System.exit(1);
    	}
    	
    	if (KON_GamesV_.size() > 1) 
    		Collections.sort(KON_GamesV_, game.GameNameComparator); // Sort by game name
    	// Collections.sort(VGT_GamesV_, game.ApprovalNumberComparator); // Sort by approval number
    	else {
    		System.out.println("Game vector size is < 0" + KON_GamesV_.toString());
    		System.exit(1);
    	}
    	
    	/*
    	if (BAL_GamesV_.size() > 1) 
    		Collections.sort(BAL_GamesV_, game.GameNameComparator); // Sort by game name
    	else {
    		System.out.println("Game vector size is < 0" + BAL_GamesV_.toString());
    		System.exit(1);
    	}
    	*/
    }
    
    // Objective:
    // Process the String input as a game object, we won't be parsing as a CSV file,
    // then output game data for sorting purposes. 
    // input: TSL entry
    // output: game object for TSL entry
   	// Example of TSL entry
	// 00,0000053699,WILD THING-V                                                ,0101380             ,PS32
	// 00,0000056441,WILD THING MONEY TRAIN-V                                    ,0201550             ,PS32
	// 00,0000056435,WILD THING SCORCHIN FORTUNE-V                               ,0101660             ,PS32
	// 00,0000083596,WILD TEPEE-V                                                ,20254811            ,SHA1
	// 00,0000087745,WILD TEPEE (GEN 7)-V                                        ,10160542            ,BLNK

    private game ProcessLine(String str) {
       	game mygameoutput = null;
     	
    	String myManf = str.substring(0, 2);
    	// String[] split = str.split(",");
    	//String myManf = str[0].trim();
    	    	
    	String manufacturer_ = null;
    	
    	if (myManf.equals(dfm_.getManufacturerCode("ARI")))
    		manufacturer_ = dfm_.getManufacturerName("ARI");
   	    else if (myManf.equals(dfm_.getManufacturerCode("IGT"))) 
    		manufacturer_ = dfm_.getManufacturerName("IGT");
   	    else if (myManf.equals(dfm_.getManufacturerCode("ARU"))) 
    		manufacturer_ = dfm_.getManufacturerName("ARU");
   	    else if (myManf.equals(dfm_.getManufacturerCode("STA"))) 
    		manufacturer_ = dfm_.getManufacturerName("STA");
   	    else if (myManf.equals(dfm_.getManufacturerCode("KON"))) 
    		manufacturer_ = dfm_.getManufacturerName("KON");
   	    else if (myManf.equals(dfm_.getManufacturerCode("AGT"))) 
    		manufacturer_ = dfm_.getManufacturerName("AGT");
   	    else if (myManf.equals(dfm_.getManufacturerCode("VGT"))) 
    		manufacturer_ = dfm_.getManufacturerName("VGT");
   	    else if (myManf.equals(dfm_.getManufacturerCode("BAL"))) 
   	    	manufacturer_ = dfm_.getManufacturerName("BAL");
   	    else {
   	    	manufacturer_ = "unknown";
   	    	System.out.println("Unknown Manufacturer!!");
   	    	System.exit(1);
   	    }
    	
    	
    	// I give up, I will use absolute character range - it should be okay since 
    	// it is standard, it's expected that fields are of certain lengths
    	// Note: These will throw exceptions if the entries are not correct in length. 
    	
    	String approvalnumber = str.substring(3,13);
    	// String approvalnumber = str[1].trim();
    	
    	String gamename = str.substring(14,73);
    	//String gamename = str[2].trim();
    	
    	String hfname = str.substring(75,95); // was 94, now 95 oops.
    	// String hfname = str[3].trim();
    	
    	
    	String tmp_hftype = str.substring(96, 100);
    	// String tmp_hftype = str[4].trim();
    	String hftype = null;
    	
    	// This is required because <game> object expects full name input
    	if (tmp_hftype.equals("BLNK"))
    		hftype = "BIN LINK FILE";
    	else  if (tmp_hftype.equals("PS32"))
        	hftype = "PSA 32";
        else  if (tmp_hftype.equals("SHA1"))
        	hftype = "HMAC SHA1";
    	
    	// Format display type correctly
     	// This fails if the existing TSL file has incorrect entries, i.e. other than -V everything
    	// is defaulted to Stepper type. 
    	// However because we re-use the previous TSL file to generate
    	// the new TSL file, one can assume that TSL files that have been 
    	// previously manually generated will be error free. I have checked this in the first runs  
    	// and have corrected "manually" generated TSL files to include the correct display type. 
    	// 
    	// What has been done, is verification of the display type to either -V or -S, anything else
    	// then we abort! 
    	// 
    	String displaytype = null;
    	if (gamename.toString().trim().endsWith("-V"))
    		displaytype = "Video";
        else if (gamename.toString().trim().endsWith("-S"))
        	displaytype = "Stepper";
    	else {
    		System.out.println("Invalid Display type read in TSL file for: " + gamename.toString());
    		System.out.println("Please manually fix the TSL file before continuing!");
    		System.out.println(str.toString());
    		System.exit(1); 	// Aborting!! 
    	}
    	
    	String tmpstr = gamename.trim();
    	String tmpstr2 = tmpstr.substring(0, tmpstr.length()-2); // Removing display type "-V" or "-S" in game name
    	
    	// Generate the <game> object based on the String line input
    	mygameoutput = new game(tmpstr2, manufacturer_, "Approved", approvalnumber, hfname, hftype, displaytype );
    	
    	return mygameoutput;
    }
    
    public void run() {
    	@SuppressWarnings("unused")
		boolean EOF = false, ReadingComplete_ = false;
    	String LineStr = "";
    	@SuppressWarnings("unused")
		String[] row = null; 
    	
        ARI_GamesV_ = new Vector<game>();
        IGT_GamesV_ = new Vector<game>();
        KON_GamesV_ = new Vector<game>();
        AGT_GamesV_ = new Vector<game>();
        VGT_GamesV_ = new Vector<game>();
        STA_GamesV_ = new Vector<game>();
        ARU_GamesV_ = new Vector<game>();
        BAL_GamesV_ = new Vector<game>();
        System.out.println("\n\n>>>> Reading TSL file: " + filename_ + " Please wait... ");
        
        // Get number of lines used in TSL file, i.e. range/resolution.
        int lines = 0;
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(filename_));
        	while (reader.readLine() != null) lines++;
			reader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        int linenumber = 0;
        double increment = lines * 0.01;  // 800 * 100% = 8 increment = 1%
    	
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename_));
            while (!EOF) {
            	sleep(25);	// allow multithreaded
                // 1. Read a line
                LineStr = br.readLine();
                // 2. Tokenise the line.
                // if (LineStr == null) break; 
                if (LineStr != null) {
                	double prog = linenumber / increment;
                	// System.out.print("."); 
                    if (LineStr.startsWith(dfm_.getManufacturerCode("ARI"))) {
                    	ARI_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("IGT"))) {
                    	IGT_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("STA"))) {
                    	STA_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("KON"))) {
                    	KON_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("VGT"))) {
                    	VGT_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("AGT"))) {
                    	AGT_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("ARU"))) {
                    	ARU_GamesV_.add(ProcessLine(LineStr));
                    } else if (LineStr.startsWith(dfm_.getManufacturerCode("BAL"))) {
                    	BAL_GamesV_.add(ProcessLine(LineStr));
                    } else {
                    	System.out.println("\nSkipping unknown manufacturer: " + LineStr);	
                    }
                    printProgBar((int)prog);
                    linenumber++;
                } else {
                	System.out.println("\nEOF reached! LineStr: " + linenumber + ": " + LineStr);
                    EOF = true; // End of File Reached
                    printProgBar(100);
                }
            }
            br.close(); // Do some clean ups
         } 
        catch (java.lang.NullPointerException e) {
           System.out.println("Null Pointer Exception found: " + e);
           // EOF = true;
           //System.out.println("")
        } catch (java.io.EOFException e) {
            EOF = true;
            System.out.println("EOFException " + e);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not Found! " + e);
            System.exit(1);
        } catch (java.io.IOException e) {
            System.out.println("IO Error " + e);
            System.exit(1);
        } catch (InterruptedException e) {
        	 System.out.println("Interrupted Exception Error " + e);
             System.exit(1);
        }
      
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
    
    // Main Constructor, uses filename as input, assumes nothing.
    public TSLfile(String str) {
        filename_ = str;
        
        // Create Object Once!
        dfm_ = new DatafileManufacturers();
		dfm_.run(); // allow for multi-threaded. 
    }
}
