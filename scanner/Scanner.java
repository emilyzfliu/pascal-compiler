package scanner;

import java.io.*;
/**
 *  Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 *  @author Emily Liu
 *  @version January 2020
 *  
 *  Usage:
 *  Class Scanner reads tokens from either an InputStream object or a String object. Tokens consist
 *  of identifiers (variable names), numbers (strings of digits), and operands.
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    
    /**
     * Method getNextChar reads the next character in the input stream of characters and sets the
     * currentChar variable to the char read by the input reader.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
                eof = true;
            else 
                currentChar = (String.valueOf((char)inp)).charAt(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Method eat uses the getNextChar helper method to advance the current character. eat compares
     * the current character to the expected character and throws an exception if they do not match.
     * @param expected      the expected current character, which should match with the
     *                      current character in the input stream
     * @exception           ScanErrorException on illegal character
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected==currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new IllegalArgumentException("method 'eat' has illegal argument");
        }
    }
    
    /**
     * Evaluates whether or not the input character is a letter of the phonetic alphabet,
     * a-z or A-Z.
     * @param   c   the character to be evaluated
     * @return true if c is a letter
     *          false   if c is not a letter
     */
    public static boolean isLetter(char c) 
    {
        return (c>='A' && c<='Z') || (c>='a' && c<='z');
    }
    
    /**
     * Evaluates whether or not the input character is a numerical digit, 0-9.
     * @param   c   the character to be evaluated
     * @return true if c is a digit
     *          false   if c is not a digit
     */
    public static boolean isDigit(char c) 
    {
        return (c>='0' && c<='9');
    }
    
    /**
     * Evaluates whether or not the input character is a space, tab, or new line
     * @param   c   the character to be evaluated
     * @return true if c is a space, tab, or new line
     *          false   otherwise
     */
    public static boolean isWhiteSpace(char c) 
    {
        return c==' ' || c=='\t' || c=='\r' || c=='\n';
    }
    
    
    /**
     * Tokenizes the next lexeme of the input as a number.
     * Reads until the end of the numerical lexeme that starts with the current char.
     * @precondition    current character is a digit
     * @return      A String containing a number lexeme with all characters as digits (0-9).
     * @exception           ScanErrorException when current character
     *                      does not match next in stream
     */
    private String scanNumber() throws ScanErrorException
    {
        String ret = "";
        while (isDigit(currentChar)) 
        {
            ret+= String.valueOf(currentChar);
            eat(currentChar);
        }
        return ret;
    }
    
    /**
     * Tokenizes the next lexeme of the input as an identifier.
     * Reads until the end of the lexeme that starts with the current char.
     * @precondition    current character is a letter
     * @return      A String containing an identifier lexeme with a letter
     *              as the first character, and all following characters are
     *              alphanumeric symbols (a-z, A-Z, 0-9).
     * 
     * @exception           ScanErrorException when current character
     *                      does not match next in stream
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String ret = "";
        while (isDigit(currentChar) || isLetter(currentChar)) 
        {
            ret+= String.valueOf(currentChar);
            eat(currentChar);
        }
        return ret;
    }
    
    /**
     * Evaluates whether or not the input character is an operand of the
     * type {+, -, =, *, /, %, (, ), <, >, :, ;, .}.
     * @param   c   the character to be evaluated
     * @return true if c is an operand of the type {+, -, =, *, /, %, (, ), <, >, :, ;, .}.
     *          false   otherwise
     */
    private boolean isOperand(char c) 
    {
        return c=='='||c=='+'||c=='-'||c=='*'||c=='/'||c=='%'||c=='('||c==')'
                || c=='<' || c=='>' || c==':' || c==';' || c=='.' || c==',';
    }
    
    /**
     * Tokenizes the next character of the input as an operand of
     * the type {+, -, =, *, /, %, (, ), <, >, :, ;, .}.
     * If the next charater is not an operand, throws an exception.
     * @precondition    the next character is an operand of the
     * type {+, -, =, *, /, %, (, ), <, >, :, ;, .}.
     * @return      A String containing the next character, if the next
     *              character is an operand
     * @exception           ScanErrorException on illegal character or when current character
     *                      does not match next in stream
     */
    private String scanOperand() throws ScanErrorException
    {
        //System.out.println("scanning operand. char is "+currentChar);
        if (isOperand(currentChar)) 
        {
            if (currentChar=='>' || currentChar=='<' || currentChar==':')
            {
                String ret = String.valueOf(currentChar);
                eat(currentChar);
                if (currentChar=='=')
                {
                    ret+=String.valueOf(currentChar);
                    eat(currentChar);
                    return ret;
                }
                if (ret.equals("<") && currentChar=='>') 
                {
                    eat(currentChar);
                    return "<>";
                }
                else return ret;
            }
            if (currentChar=='.') 
            {
                eof = true;
            }
            char c = currentChar;
            eat(currentChar);
            return String.valueOf(c);
        }
        else 
        {
            char inv = currentChar;
            eat(currentChar);
            throw new ScanErrorException("Invalid character "+String.valueOf(inv));
        }
    }
    
    /**
     * Advances the current character, starting with a line comment
     * ('//') until the end of the line is reached.
     * Throws an exception if the current character does not match the character eaten by input.
     * @precondition        current character is the second slash of the line comment
     * @exception           ScanErrorException when current character
     *                      does not match next in stream
     */
    private void skipLineComment() throws ScanErrorException
    {
        while (currentChar!='\n' && currentChar!='\r') 
        {
            eat(currentChar);
        }
    }
    
    /**
     * Method nextToken skips all white spaces and gets the next complete number, operand,
     * or identifier (variable name) from the input stream. nextToken skips past all line comments
     * nextToken will throw an exception if the input character is not recognized
     * (it is not a digit, letter, white space, or operand). If the end of the file has
     * been reached, returns a period.
     * 
     * @return      a String object containing the next number, operand, or identifier.
     * @exception           ScanErrorException on illegal character or when current character
     *                      does not match next in stream
     */
    public String nextToken() throws ScanErrorException
    {
        if (currentChar=='.')
        {
            eof = true;
        }
        if (eof)
        {
            return ".";
        }
        while (isWhiteSpace(currentChar)||currentChar=='/') 
        {
            while (isWhiteSpace(currentChar)) 
            { 
                eat(currentChar);
                if (eof) return ".";
            }
        
            char c = currentChar;
            while (c=='/') 
            {
                eat(currentChar);
                if (currentChar!='/') return "/";
                skipLineComment();
                c = currentChar;
            }
        }
        
        if (isDigit(currentChar)) 
        {
            return scanNumber();
        }
        else if (isLetter(currentChar)) 
        {
            return scanIdentifier();
        }
        else 
        {
            return scanOperand();
        }
    }
    
    /**
     * Evaluates whether or not the end of the input stream has been reached
     * @return true if there are more characters to be read
     *          false if the end of the input stream has been reached
     */
    public boolean hasNext()
    {
        return !eof;
    }

}

