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
 * @version 1.0
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;


public class game implements Comparable<game> {
	
    private DatafileManufacturers dfm_;
	
    private String gamename_;
    private String manufacturer_;
    private String status_;
    private String approvalnumber_;
    private String filename_;
    private String filetype_;
    private String displaytype_;
    private int appnumber_;
    
    public game() {
        // We are not using default constructors
    }

    public boolean isEqual(game g) {
    	// Compare if TSL entries are the same, this is how I check they're the same.
    	// Compare objects doesn't seem to work!!
    
    	// if (this.getTSLentry().equals(g.getTSLentry()))
    	//	return true;
    	// else return false;
    
    	// Don't compare manufacturers this should speed it up a bit! && this.manufacturer_.equals(g.manufacturer_)
    	if (this.approvalnumber_.startsWith(g.approvalnumber_) && this.gamename_.startsWith(g.gamename_)
    			 && this.status_.startsWith(g.status_)) {
        	return true;
    	}
    	else return false;
    }
    
    public int getAppNumber() {
    	return appnumber_;
    }
    
    public int compareTo(game anotherGame) throws ClassCastException {
    	if (!(anotherGame instanceof game))
    		throw new ClassCastException("A game object expected");
    	
    	int anotherGameApprovalNumber = ((game)anotherGame).getAppNumber();
    	return this.appnumber_ - anotherGameApprovalNumber;
    }
    
    public static Comparator GameNameComparator = new Comparator<Object>() {
    	public int compare(Object myGame, Object anotherGame) {
    		String gameName1 = ((game)myGame).getGameName();
    		String gameApprovalNum1 = ((game)myGame).getApprovalNumber();
    		String gameName2 = ((game)anotherGame).getGameName();
    		String gameApprovalNum2 = ((game)anotherGame).getApprovalNumber();
    		
    		if (!gameName1.equals(gameName2))
    			return gameName1.compareTo(gameName2);
    		else
    			return gameApprovalNum1.compareTo(gameApprovalNum2);
    	}
    };
    
    public static Comparator ApprovalNumberComparator = new Comparator<Object>() {
    	public int compare(Object myGame, Object anotherGame) {
    		String gameApprovalNum1 = ((game)myGame).getApprovalNumber();
    		String gameName1 = ((game)myGame).getGameName();
    		String gameApprovalNum2 = ((game)anotherGame).getApprovalNumber();
    		String gameName2 = ((game)anotherGame).getGameName();
    		
    		if (!gameApprovalNum1.equals(gameApprovalNum2))
    			return gameApprovalNum1.compareTo(gameApprovalNum2);
    		else
    			return gameName1.compareTo(gameName2);
    	}
    };
    
    public game(String name, String manuf, String status, String appnum, String filename, String filetype, String displaytype) {
    	
    	// Changed made to replace commas into spaces in game names	
    	gamename_ = name.trim().replaceAll(",", " ");
    	
    	
    	// need 2 characters for manufacturer number, e.g. 00, 01, etc. 
        manufacturer_ = manuf.trim();
        while (manufacturer_.trim().length() < 2) {
            manufacturer_ = "0" + manufacturer_; 
        }
        
        status_ = status.trim();
        
        // need 10 characters for approval number
        while (appnum.trim().length() < 10 ) {
        	appnum = "0" + appnum;
        }        
        
        approvalnumber_ = appnum.trim();
        filename_ = filename.trim();
        filetype_ = filetype.trim();
        displaytype_ = displaytype.trim();
    }

    public String getTSLentry() {
        String output = null;

        // Manufacturer code, i.e. 00 = ARI, 01, IGT
        output = getManufacturer() + ",";

        // SSAN
        output = output + getApprovalNumber() + ",";

        // GAME NAME
        output = output + getGameName() + ",";

        // File name
        output = output + getFileName() + ",";

        // File type
        output = output + getFileType();

        return output;
    }

    // Note: Does not handle all available file types yet.
    public String getFileType() {
        String output = null;
        boolean done = false;

        if (filetype_.equals("BIN LINK FILE")) {
            output = "BLNK";
            done = true;
        }

        if (filetype_.equals("PSA 32")) {
            output = "PS32";
            done = true;
        }

        if (filetype_.equals("HMAC SHA1")) {
            output = "SHA1";
            done = true;
        }

        if (!done) {
            System.out.println("Error: Unknown File Type" + filetype_);
            System.exit(1);
        }
        
        return output;
    }

    public String getFileName() {
        String output = null;

        output = filename_; // copy

        // need 20 characters
        while (output.length() < 20 ) {
            output = output + " ";
        }
              
        return output;
    }

    // Note: This will format the approval number exactly to what is required in length
    public String getApprovalNumber() {
        String output = null;

        // make a local copy
        output = approvalnumber_;

        // need 10 characters
        while (output.length() < 10 ) {
            output = "0" + output;
        }

        return output;
    }

    // Note this will fail on non-video games, i.e. Stepper games - because I'm too lazy
    public String getGameName() {
        String output = null;

        // All uppercase
        output = gamename_.toUpperCase().trim();
        // Add Trailing "-V" or "S"

        if (getDisplayType().equals("Video"))
            output = output + "-V";
        if (getDisplayType().equals("Stepper"))
                output = output + "-S";

        // need 60 characters
        while (output.length() < 60 ) {
            output = output + " ";
        }
        return output;
    }


    public String getDisplayType() {
        return displaytype_;
    }

    // Output should be "ARISTOCRAT TECHNOLOGIES AUSTRALIA PTY LIMITED"
    // I G T (AUSTRALIA) PTY LTD
    // etc
    public String getManufacturer() {
    	String output = null;
    	
    	// Read XML datafile manufacturers. 
    	
    	dfm_ = new DatafileManufacturers();
	dfm_.run(); // allow for multi-threaded.
	
	Vector<MyManufacturers> manufacturers_vector_ = dfm_.getMyManufacturers();
	Iterator<MyManufacturers> it = manufacturers_vector_.iterator();
    	
    	while (it.hasNext()) {
    		MyManufacturers mymanu = (MyManufacturers)it.next();
    		
    		if (manufacturer_.equals(mymanu.getManufacturerName()) || 
    				manufacturer_.equals(mymanu.getManufacturerCode()) ||
    						manufacturer_.contains(mymanu.getManufacturerHistoricName())) {
    			output =  mymanu.getManufacturerCode();
    		}
    	}
    	
    	
        return output;
    }

    public String getStatus() {
        return status_;
    }
}
