package qcastslgenerator;
//
//  FileHandler.java
//  This is class that reads txt file formatted in csv form only to generate a game object. 
//
//  Created by James Aceret on 13/10/05.
//  Copyright 2005 __MyCompanyName__. All rights reserved.
//

import java.io.*;
import java.util.*;


public class FileHandler implements Runnable {
    public String filename_;
    private Vector<String> output_;
    private Vector<game> FileStack_;
    private boolean EOF;
    private Thread t_;

    public Vector<game> getGameStack() {
        return FileStack_;
    }

    public Vector<String> getOutput() {
        return output_;
    }

    private game ProcessLine(Vector<String> myoutput_) {
        String gamename_ = null;
        String manufacturer_ = null;
        String status_ = null;
        String approvalnumber_ = null;
        String filename_ = null;
        String filetype_ = null;
        String displaytype_ = null;

        game gameoutput = null;

        // This has to be correct Based on the CSV file - you'll need to watch/debug the code to verify this. 
   	    gamename_ = myoutput_.elementAt(0).toString();
        manufacturer_ = myoutput_.elementAt(1).toString();
        status_ = myoutput_.elementAt(2).toString();
        approvalnumber_ = myoutput_.elementAt(5).toString();
        filename_ = myoutput_.elementAt(7).toString();
        filetype_ = myoutput_.elementAt(8).toString();
        displaytype_ = myoutput_.elementAt(6).toString();

        gameoutput = new game(gamename_, manufacturer_, status_, approvalnumber_, filename_, filetype_, displaytype_);

        return gameoutput;
    }

    public void run() {
        output_ = new Vector<String>(); 
        FileStack_ = new Vector<game>(); // This will hold output_ generated vectors, i.e. all lines
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename_));
            while (!EOF) {
                Thread.sleep(50); // multi-threaded
                // 1. Read a line
                String LineStr = br.readLine();

                // 2. Tokenise the line.
                if (!LineStr.equals(null)) {
                    StringTokenizer st = new StringTokenizer(LineStr);

                    while (st.hasMoreTokens()) { // Process Each Line
                        String saved = st.nextToken(",").trim(); // Process Each Token
                        output_.add(saved);
                    }

                    // Save the line as a game in a stack.
                    FileStack_.add(ProcessLine(output_));
                    output_.clear();
                } else {
                    EOF = true; // End of File Reached
                }
            }
            br.close(); // Do some clean ups
        } catch (java.lang.NullPointerException e) {
            EOF = true;
        } catch (java.io.EOFException e) {
            EOF = true;
            System.out.println("EOFException " + e);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not Found! " + e);
            System.exit(1);
        } catch (java.io.IOException e) {
            System.out.println("Error " + e);
            System.exit(1);
        } catch (InterruptedException e) {}

        // Final Sanity Check: End of the file, and we didn't find it
        if (EOF && FileStack_.isEmpty()) {
                System.out.println("Error. EOF reached, but Filestack is still empty");
                System.exit(1);
        }
    }

    // Main Constructor, uses filename as input, assumes nothing.
    public FileHandler(String str) {
        filename_ = str;
        t_ = new Thread(this, "CSV FileHandler Thread");
        t_.start();
        System.out.println("Starting Thread: " + t_);
    }

}
