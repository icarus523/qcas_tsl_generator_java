package TSLgameremover;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import msexcelreader.MSexcellreader;

import qcastslgenerator.TSLfile;
import qcastslgenerator.game;

public class TSLGameRemover implements Runnable {
	public String fname_;
	public String fname_game_deleted_;
	
	private int NumberOfGames_;
	private int TotalSizeofTSLfile_;
	
	TSLGameRemover() {
	}
	
    TSLGameRemover(String fname_tsl_file, String fname_game_deleted, int Num_of_Games) {
    	this.fname_ = fname_tsl_file;
    	this.fname_game_deleted_ = fname_game_deleted; 
    	this.NumberOfGames_ = Num_of_Games; 
    	
    	t_ = new Thread(this, "TSLGameRemover Thread");
        t_.start(); // we are Pro, so we use threads. 
        
        System.out.println(">>>> Starting Thread: " + t_);     
    }
    
	/**
	 * @param args
	 */
	public static void main(String[]  args) {
		switch (args.length) {
		case 0:
			System.out.println("Usage: -h -v");
			break;
		case 1:
		case 2:
			if (args[0].equals("-v")) {
				// Version Number
				System.out.println("v1.0");
			} else if (args[0].equals("-h")) {
				// help line
				System.out
						.println("java -jar TSLGameRemover.jar <INPUT TOBEDELETED.TLS> <DELGAMES.XLS> <NUM_OF_GAMES_TO_BE_DELETED");
			} 
			else System.out.println("Usage: -h -v -bad two arguments");
			break;
		case 3:
			if ((args[0].toUpperCase().endsWith("TSL")) && (args[1].toUpperCase().endsWith("XLS"))) {
				try {
					// Test for file exists.
					@SuppressWarnings("unused")
					BufferedReader br = new BufferedReader(new FileReader(args[0]));
					System.out.println(">>>> Removing Games from: " + args[1]);
					// args[0] - current TSL file
					// args[1] - XLS file with games to be removed.
					new TSLGameRemover(args[0], args[1], Integer.parseInt(args[2])); // convert to Integera

				} catch (java.io.FileNotFoundException e) {
					System.out.println(">>>> File not Found! " + e);
					System.exit(1);
				} catch (Exception e) {
					System.out.println(">>>> Exception reached: " + e);
				}
				
			} else 
				System.out.println("Usage: -h -v -bad three arguments");
			break;
		default:
			System.out.println("Usage: -h -v");
			System.exit(0);
			break;
		}
	}

	public void run() {
		// Read current TSL file
		System.out.println(">>>> Reading TSL file: " + fname_ + " and generating current TSL entries");
		TSLfile mytsf = new TSLfile(fname_);
		mytsf.run();
		
		System.out.println(">>>> " + fname_ + " contains " + mytsf.getSize() + " items");

		// Generate game list to be removed
		System.out.println(">>>> Generating list of games to be deleted from: " + fname_game_deleted_);
		MSexcellreader xcellReader = new MSexcellreader(fname_game_deleted_, this.NumberOfGames_);
		xcellReader.run();
		
		// To access games list use this variable, this should return a Vector<game> type
		// xcellReader.getXLSgames()
		
		Iterator<game> it = xcellReader.getXLSgames().iterator();
   		while (it.hasNext()) { // Process Each Line
   			try {
				Thread.sleep(100);
				game myGame = (game)it.next();
	   			
	   			synchronized(mytsf) {
	   				int currentsize_ = mytsf.getSize();
	   				mytsf.removeGame(myGame);	// This is so Pro.
	   				
	   				if (currentsize_ == mytsf.getSize()) {
	   					System.out.println(">>>> Could not find " + myGame.getGameName() + " for Deletion");
	   					NumberOfGames_--;
	   				}
	   				else {
	   					//if (xcellReader.getXLSgames().removeElement(myGame)) {
	   						System.out.println(">>>> Deleted entry: " + myGame.getTSLentry());
	   					//}
	   				}
	   			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		}
		
   		System.out.println(">>>> After Deletion of Games, " + fname_ + " now contains " + mytsf.getSize() 
   				+ " entries. ");
   		
   		TotalSizeofTSLfile_ = mytsf.getSize();
   		
   		mytsf.Sort(); // sort the file
   		
   		int status = 0;
   		status = writetoTSLfile(mytsf.getARIVec(), status); // Use sorted vector of games
   		status = writetoTSLfile(mytsf.getIGTVec(), status);
   		status = writetoTSLfile(mytsf.getARUVec(), status);
   		status = writetoTSLfile(mytsf.getSTAVec(), status);
   		status = writetoTSLfile(mytsf.getBALVec(), status);
   		status = writetoTSLfile(mytsf.getKONVec(), status);
   		status = writetoTSLfile(mytsf.getAGTVec(), status);
   		status = writetoTSLfile(mytsf.getVGTVec(), status);
   		printProgBar(100); // ensure we print 100%
   		
	    System.out.println("\n>>>> After Deletion of Games, " + fname_ + " now contains " + mytsf.getSize() 
   				+ " entries. ");
	    System.out.println(">>>> Total Number of Games Deleted: " + NumberOfGames_);
	    System.out.println("\n>>>> -= COMPLETE! =- ");
   		
	    System.exit(0); // Exit out cleanly
	}

	public int writetoTSLfile(Vector<game> gamevec, int counter) {
    	double increment = (gamevec.size()+TotalSizeofTSLfile_) * 0.01;  // 800 * 100% = 8 increment = 1%
    	// int counter = 0;
    	BufferedWriter wr = null;
    	
    	if (gamevec.size() > 0)
    		System.out.println("\n\n>>>> Writing TSL file for Manufacturer ID: [" + gamevec.elementAt(0).getManufacturer() + "]:");
    	else
    		System.out.println(">>>> Skipping a Manufacturer because it has zero elements.");
    	
    	try {
        	File file = new File(fname_.substring(0, fname_.length()-4) + "-Less Deleted Games.TSL"); // we're appending a new file 
           	wr = new BufferedWriter(new FileWriter(file, true));	  // to make it simpler to File Compare. 
           	
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
    		// printProgBar(100);
        }
        catch (FileNotFoundException e) {
            System.out.println(">>>> File not Found: " + e);
        }
        catch (IOException e) {
            System.out.println(">>>> Error: " + e);
        } finally {
            try {
                if (wr != null)
                    wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return counter;
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
    
	private Thread t_;
	
}
