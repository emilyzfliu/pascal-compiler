package ast;
import environment.*;

/**
 * Abstract class Statement is a template for all concrete
 * classes containing statements that execute print statements,
 * variable declarations, if statements, statement blocks, 
 * and while loops.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public abstract class Statement
{
    /**
     * Abstract method exec accepts an environment as input
     * and executes the statement in the given environment
     * @param e     the environment in which to execute
     *              the statement
     */
    public abstract void exec (Environment e);
    
    /**
     * Compile method. Placeholder method to be overwritten
     * in concrete sublcasses.
     * @param e     the emitter used to write code to the MIPS file.
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
