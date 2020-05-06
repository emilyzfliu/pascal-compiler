package parser;
import scanner.*;
import java.util.*;
import java.io.*;
import environment.*;
import ast.*;

/**
 * Class ParserTest reads in a file and uses a Parser object (from the package parser)
 * to evaluate the input, a piece of Pascal code consisting of BEGIN/END block statements,
 * variable declarations, print statements, arithmetic operations using +, -, *, /, and (),
 * IF/THEN/ELSE statements, and WHILE loops. Outputs a MIPS assembly executable.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class ParserTest
{
    /**
     * Main method for class ParserTest. Runs and tests objects of the Parser class
     * @param args      Arguments of input strings
     */
    public static void main (String[] args) throws Exception
    {
        Environment e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest.txt--------------------------");
        FileInputStream inStream0 = new FileInputStream(new File("parserTest.txt"));
        parser.Parser par0 = new parser.Parser (inStream0);
        while (par0.hasNext()) 
        {
            par0.parseProgram().compile("mips_from_test.txt");
        }
        System.out.println("Finished parsing parserTest.txt!");
        
        /*e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest1.txt--------------------------");
        FileInputStream inStream1 = new FileInputStream(new File("parserTest1.txt"));
        parser.Parser par1 = new parser.Parser (inStream1);
        while (par1.hasNext()) 
        {
            par1.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest2.txt--------------------------");
        FileInputStream inStream2 = new FileInputStream(new File("parserTest2.txt"));
        parser.Parser par2 = new parser.Parser (inStream2);
        while (par2.hasNext()) 
        {
            par2.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest3.txt--------------------------");
        FileInputStream inStream3 = new FileInputStream(new File("parserTest3.txt"));
        parser.Parser par3 = new parser.Parser (inStream3);
        while (par3.hasNext()) 
        {
            par3.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest4.txt--------------------------");
        FileInputStream inStream4 = new FileInputStream(new File("parserTest4.txt"));
        parser.Parser par4 = new parser.Parser (inStream4);
        while (par4.hasNext()) 
        {
            par4.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest6.txt--------------------------");
        FileInputStream inStream6 = new FileInputStream(new File("parserTest6.txt"));
        parser.Parser par6 = new parser.Parser (inStream6);
        while (par6.hasNext()) 
        {
            par6.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest7.txt--------------------------");
        FileInputStream inStream7 = new FileInputStream(new File("parserTest7.txt"));
        parser.Parser par7 = new parser.Parser (inStream7);
        while (par7.hasNext()) 
        {
            par7.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest8.txt--------------------------");
        FileInputStream inStream8 = new FileInputStream(new File("parserTest8.txt"));
        parser.Parser par8 = new parser.Parser (inStream8);
        while (par8.hasNext()) 
        {
            par8.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing parserTest9.txt--------------------------");
        FileInputStream inStream9 = new FileInputStream(new File("parserTest9.txt"));
        parser.Parser par9 = new parser.Parser (inStream9);
        while (par9.hasNext()) 
        {
            par9.parseProgram().exec(e);
        }
        
        e = new Environment(new HashMap<String, Integer>(),
                                new HashMap<String, ProcedureDeclaration>(), null);
        System.out.println("-----------Parsing ProceduresWarmupCode.txt--------------------------");
        FileInputStream inStream10 = new FileInputStream(new File("ProceduresWarmupCode.txt"));
        parser.Parser par10 = new parser.Parser (inStream10);
        while (par10.hasNext()) 
        {
            par10.parseProgram().exec(e);
        }*/
    }
}
