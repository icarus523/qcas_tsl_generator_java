package TSLgameremover;

import java.io.LineNumberReader;

import java.io.Writer;
import java.util.List;
import java.util.Vector;
public class CSVHelper
{
    public static void writeLine(Writer w, List<String> values) 
        throws Exception
    {
        boolean firstVal = true;
        for (String val : values)  {
            if (!firstVal) {
                w.write(",");
            }
            w.write("\"");
            for (int i=0; i<val.length(); i++) {
                char ch = val.charAt(i);
                if (ch=='\"') {
                    w.write("\"");  //extra quote
                }
                w.write(ch);
            }
            w.write("\"");
            firstVal = false;
        }
        w.write("\n");
    }

    /**
    * Returns a null when the input stream is empty
    */
    public static List<String> parseLine(LineNumberReader lnr)  
        throws Exception
    {
        String line = lnr.readLine();
        if (line==null) {
            return null;
        }
        Vector<String> store = new Vector<String>();
        StringBuffer curVal = new StringBuffer();
        boolean inquotes = false;
        for (int i=0; i<line.length(); i++) {
            char ch = line.charAt(i);
            if (inquotes) {
                if (ch=='\"') {
                    inquotes = false;
                }
                else {
                    curVal.append(ch);
                }
            }
            else {
                if (ch=='\"') {
                    inquotes = true;
                    if (curVal.length()>0) {
//if this is the second quote in a value, add a quote
//this is for the double quote in the middle of a value
                        curVal.append('\"');
                    }
                }
                else if (ch==',') {
                    store.add(curVal.toString());
                    curVal = new StringBuffer();
                }
                else {
                    curVal.append(ch);
                }
            }
        }
        store.add(curVal.toString());
        return store;
    }
}