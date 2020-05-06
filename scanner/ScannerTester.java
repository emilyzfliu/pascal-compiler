package scanner;

import java.io.*;
import java.util.*;
/**
 * Driver class for class Scanner. Takes a file as input and tokenizes the contents of the file
 * into numbers, identifiers, and operands.
 * 
 * @author Emily Liu
 * @version January 2020
 */
public class ScannerTester
{
    /**
     * Main method for testing class Scanner. Reads the contents of a file
     * into an InputStream objects and prints them out, one per line.
     * @param args          arguments of input strings
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        FileInputStream inStream = new FileInputStream(new File("ScannerTest.txt"));
        Scanner lex = new Scanner(inStream);
        ArrayList<String> tokenstr = new ArrayList<String>();
        while (lex.hasNext()) 
        {
            try
            {
                tokenstr.add(lex.nextToken());
                System.out.println(tokenstr.get(tokenstr.size()-1));
            }
            //to print error message: comment out try/catch statement
            //to skip error message but print unrecognized character: uncomment system print message
            //to skip unrecognized characters entirely: comment system print message
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }
}

