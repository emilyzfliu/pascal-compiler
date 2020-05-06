package ast;
import environment.*;

/**
 * Abstract class Expression is a template for all concrete
 * classes containing expressions that evaluate to an integer
 * or boolean value. This includes Number, Variable, BinOp,
 * and Condition.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public abstract class Expression
{
    /**
     * Abstract method eval accepts an environment as input
     * and evaluates the expression to return an Object
     * (which is either an Integer or a Boolean).
     * @param e     the environment in which to evaluate the expression
     * @return      an Object which is either an Integer or Boolean
     *              equivalent to the result of the simplified expression
     */
    public abstract Object eval(Environment e);
    
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
