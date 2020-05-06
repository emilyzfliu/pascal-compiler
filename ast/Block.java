package ast;
import java.util.*;
import environment.*;

/**
 * Class Block takes a List of Statements as input and evaluates
 * each of them.
 * 
 * @author Emily Liu
 * @version April 8, 2020
 */
public class Block extends Statement
{
    // instance variables - replace the example below with your own
    private List<Statement> stmts;

    /**
     * Constructor for objects of class Block
     * @param ss        the list of statements to evaluate
     */
    public Block(List<Statement> ss)
    {
        stmts = ss;
    }
    
    /**
     * Overrides the exec method from abstract class Statement.
     * Evaluates all the statements in list stmts.
     * @param e         the environment in which to evaluate
     *                  the block of statements
     */
    public void exec(Environment e) 
    {
        for (Statement s:stmts) 
        {
            s.exec(e);
        }
    }
    
    /**
     * Overrides compile method from abstract class Statement.
     * Compiles all the statements in the list.
     * @param e     the emitter used to write to the MIPS file.
     */
    public void compile(Emitter e)
    {
        e.emit("#parsing statements from block");
        for (Statement s: stmts)
        {
            s.compile(e);
        }
        e.emit("#statement block parsed");
    }

}
