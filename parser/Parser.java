package parser;
import scanner.*;
import ast.*;
import java.io.*;
import java.util.*;

/**
 * Objects of the class Parser use the scanner.Scanner object to read and
 * tokenize a string of inputs, then evaluates statements, expressions, terms,
 * and factors in increasing levels of priority, according to the order of operations.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Parser
{
    // instance variables - replace the example below with your own
    private String currentToken;
    private scanner.Scanner s;

    /**
     * Constructor for objects of class Parser
     * @param inStream      the input stream to be read, tokenized, and evaluated
     */
    public Parser (InputStream inStream) throws ScanErrorException
    {
        s = new scanner.Scanner(inStream);
        currentToken = s.nextToken();
    }

    /**
     * Method eat consumes the next token of input in the input stream.
     * @param expected      the string expected to be eaten.
     * @throws Exception     if the expected String does not match the consumed token
     */
    private void eat (String expected) throws Exception
    {
        if (expected.equals(currentToken))
        {
            //System.out.println(currentToken);
            currentToken = s.nextToken();
        }
        else throw new IllegalArgumentException("token "+currentToken+" does not match "+expected);
    }
    
    /**
     * Method parseNumber reads an integer from the current token and consumes it,
     * returning an ast.Number object containing integer read.
     * @precondition        the current token is a number
     * @return              the ast.Number object containing the value read from the current token
     * @throws Exception    if the expected String does not match the consumed token
     */
    private Expression parseNumber() throws Exception
    {
        //System.out.println("parse number: current token is "+currentToken);
        int r= Integer.parseInt(currentToken);
        eat(currentToken);
        //System.out.println ("parsing number returns "+r);
        return new ast.Number(r);
    }
    
    /**
     * Method parseIdentifier reads a variable from the current token and consumes it,
     * returning a Variable object containing the variable name
     * @precondition        the current token is a previously declared variable
     * @return              a Variable object storing the name of the variable
     * @throws Exception    if the expected String does not match the consumed token
     */
    private Expression parseIdentifier() throws Exception
    {
        String name = currentToken;
        eat(currentToken);
        if (currentToken.equals("(")) 
        {
            eat("(");
            ArrayList<Expression> vals = new ArrayList<Expression>();
            while (!currentToken.equals(")")) 
            {
                vals.add(parseExpression());
                if (currentToken.equals(",")) 
                {
                    eat(",");
                }
            }
            eat(")");
            return new ProcedureCall(name, vals);
        }
        return new Variable("var"+name);
    }
    
    /**
     * Method parseFactor reads a factor starting from the current token. If an open parenthesis is
     * encountered, the parser object begins parsing a new statement, the value of which
     * it will return. If a minus sign is encountered, the parser begins parsing another factor and
     * returns a BinOp object evaluating to 0-[value], equivalent to the negative of that value.
     * Otherwise, it checks if currentToken is a variable or procedure and parses accordingly.
     * 
     * @precondition        all tokens are valid tokens in the Pascal language
     * @throws Exception    if the expected String does not match the consumed token
     * @return              an Expression consisting of an AST equivalent to the parsed factor
     */
    private Expression parseFactor() throws Exception
    {
        //System.out.println("parse factor: current token is "+currentToken);
        while (currentToken.equals("(") || currentToken.equals("-"))
        {
            //System.out.println("looping");
            if (currentToken.equals("("))
            {
                eat("(");
                //System.out.println("current token eaten");
                Expression r = parseExpression();
                eat(currentToken);
                //System.out.println ("parsing factor returns "+r);
                return r;
            }
            else if (currentToken.equals("-"))
            {
                eat(currentToken);
                Expression e2 = parseFactor();
                Expression e1 = new ast.Number(0);
                BinOp r = new BinOp(e1, e2, "-");
                return r;
            }
        }
        if (isIdentifier(currentToken))
        {
            return parseIdentifier();
        }
        
        //System.out.println("factor parsed");
        return parseNumber();
    }
    
    /**
     * Method parseTerm reads a term, defined as a series of factors multiplied together or
     * divided by one another. parseTerm first reads a factor starting from the current token, then
     * reads * or / indicating whether to multiply or divide, grouping all previous multiplications
     * and divisions into the same expression to maintain associativity
     * @throws Exception    if the expected String does not match the consumed token
     * @return      a BinOp consisting of two expressions and either "*" or "/"
     */
    private Expression parseTerm() throws Exception
    {
        Expression e1 = parseFactor();
        Expression ret =  e1;
        while (currentToken.equals("*") || currentToken.equals("/")) 
        {
            if (currentToken.equals("*")) 
            {
                eat("*");
                Expression e2 = parseFactor();
                //System.out.println("term parsed");
                ret = new BinOp(ret, e2, "*");
            }
            else if (currentToken.equals("/"))
            {
                eat("/");
                Expression e2 = parseFactor();
                ret = new BinOp(ret, e2, "/");
            }
        }
        
        return ret;
    }
    
    /**
     * Method parseExpression reads an expression, defined as a series of terms added together or
     * subtracted. parseExpression first reads a term starting from the current token, then
     * reads + or - indicating whether to add or subtract, grouping all previous additions
     * and subtractions into the same expression to maintain associativity
     * @throws Exception    if the expected String does not match the consumed token
     * @return      a BinOp consisting of two expressions and either "+" or "-"
     */
    private Expression parseExpression() throws Exception
    {
        Expression e1 = parseTerm();
        Expression ret = e1;
        
        while (currentToken.equals("+") || currentToken.equals("-")) 
        {
            if (currentToken.equals("+")) 
            {
                eat("+");
                Expression e2 = parseTerm();
                //System.out.println("term parsed");
                ret = new BinOp(ret, e2, "+");
            }
            else if (currentToken.equals("-")) 
            {
                eat("-");
                Expression e2 = parseTerm();
                ret = new BinOp(ret, e2, "-");
            }
        }
        return ret;
    }
    
    /**
     * Method parseStatements parses a series of statements, storing them in an ArrayList.
     * Starting from the current token, it parses the statement and terminates if END is reached.
     * @throws Exception    if the expected String does not match the consumed token
     * @param sts           the list of statements in which to store each parsed statement
     */
    private void parseStatements(ArrayList<Statement> sts) throws Exception
    {
        sts.add(parseStatement());
        if (currentToken.equals("END"))
        {
            eat("END");
            eat(";");
            return;
        }
        parseStatements(sts);
    }
    
    /**
     * Tests whether or not an input string is an identifier of the form 
     * [A-Z, a-z][A-Z, a-z, 0-9]*
     * @param str       the String to be tested
     * @return          true if str is a valid identifier
     *                  false otherwise
     */
    private boolean isIdentifier (String str)
    {
        if (!(str.charAt(0)<='Z' && str.charAt(0) >='A' ||
            str.charAt(0)<='z' && str.charAt(0) >='a'))
        {
            return false;
        }
        
        for (int i=1; i<str.length(); i++)
        {
            char c = str.charAt(i);
            if (!(c<='Z' && c >='A'|| c<='z' && c >='a'|| c<='9' && c>='0')) 
            {   
                return false;
            }
        }
        return true;
    }
    
    /**
     * Helper method parseCondition parses a conditional statement consisting of two expressions
     * separated by a relative operator of type {<, >, =, <>, <=, >=}, returning a Condition
     * object containing the two expressions and the relative operator.
     * @throws Exception    if the expected String does not match the consumed token
     * @return          a Condition object containing the two expressions and the relative operator
     */
    private Condition parseCondition() throws Exception
    {
        Expression e1 = parseExpression();
        String relop = currentToken;
        eat(currentToken);
        Expression e2 = parseExpression();
        return new Condition(relop, e1, e2);
    }
    
    /**
     * Method parseStatement reads the input token and begins evaluating a
     * statement in the pascal language. A statement starts either with a print
     * command (WRITELN), a begin block command (BEGIN), a variable/procedure declaration,
     * an if statement (IF), or a while statement (WHILE [cond] DO [xyz]).
     * 
     * @throws Exception    if the expected String does not match the consumed token
     * @return              the parsed statement
     */
    public Statement parseStatement() throws Exception
    {
        //System.out.println("parsing statement");
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            //System.out.println("statmenet parsed");
            return new Writeln(exp);
        }
        else if (currentToken.equals("BEGIN"))
        {
            ArrayList<Statement> sts = new ArrayList<Statement>();
            eat(currentToken);
            parseStatements(sts);
            return new Block(sts);
        }
        else if (currentToken.equals("IF")) 
        {
            eat("IF");
            Condition c = parseCondition();
            eat("THEN");
            Statement sta = parseStatement();
            if (currentToken.equals("ELSE")) 
            {
                eat("ELSE");
                Statement s2 = parseStatement();
                return new If(c, sta, s2);
            }
            return new If(c, sta);
        }
        else if (currentToken.equals("WHILE")) 
        {
            eat("WHILE");
            Condition c = parseCondition();
            eat("DO");
            return new While(c,parseStatement());
        }
        else if (isIdentifier(currentToken))
        {
            String name = currentToken;
            eat(currentToken);
            eat(":=");
            Expression n = parseExpression();
            eat(";");
            //System.out.println("statmenet parsed");
            return new Assignment("var"+name, n); 
        }
        else if (currentToken.equals(".")) 
        {
            //System.out.println("statmenet parsed");
            return null;
        }
        
        return null;
    }
    
    /**
     * Evaluates the entire AST of the program.
     * @return      an AST describing the entire program
     */
    public Program parseProgram() throws Exception
    {
        ArrayList<Variable> vars = new ArrayList<Variable>();
        if (currentToken.equals("VAR"))
        {
            
            eat("VAR");
            vars.add(new Variable("var"+currentToken));
            eat(currentToken);
            while (!currentToken.equals(";"))
            {
                eat(",");
                vars.add(new Variable("var"+currentToken));
                eat(currentToken);
            }
            eat(";");
        
        }
        ArrayList<ProcedureDeclaration> a = new ArrayList<ProcedureDeclaration>();
        while (currentToken.equals("PROCEDURE")) 
        {
            eat("PROCEDURE");
            String name = currentToken;
            eat(currentToken);
            eat("(");
            ArrayList<String> params = new ArrayList<String>();
            while (!currentToken.equals(")")) 
            {
                params.add(currentToken);
                eat(currentToken);
                if (currentToken.equals(",")) 
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            Statement stat = parseStatement();
            a.add(new ProcedureDeclaration(name, stat, params));
        }
        Statement st = parseStatement();
        return new Program(st,vars, a);
    } 
    
    /**
     * Method hasNext determines whether or not there are still statements to be parsed.
     * @return      true if there are still statements to be parsed
     *              false otherwise
     */
    public boolean hasNext() 
    {
        return s.hasNext();
    }
}
