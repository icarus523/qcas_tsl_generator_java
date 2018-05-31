package pslmslnamegenerator;

import java.io.BufferedReader;
import java.io.FileReader;

import TSLgameremover.TSLGameRemover;

public class pslmslnamegenerator implements Runnable {

	private Thread t_;
	
	pslmslnamegenerator(String str) {
		t_ = new Thread(this, "pslmslnamegenerator Thread");
        t_.start();
        System.out.println("Starting Thread: " + t_);   
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
				System.out.println("Generates and Chooses Filenames based on current file and date");
			} else if (args[0].toUpperCase().endsWith("TSL")) {
				try {
					// Test for file exists.
					BufferedReader br = new BufferedReader(new FileReader(args[0]));
					System.out.println("Removing Games from: " + args[0]);
					new pslmslnamegenerator(args[0]); 

				} catch (java.io.FileNotFoundException e) {
					System.out.println("File not Found! " + e);
					System.exit(1);
				}
				
			} else 
				System.out.println("Usage: -h -v");

			break;
		default:
			System.out.println("Usage: -h -v");
			System.exit(0);
			break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
