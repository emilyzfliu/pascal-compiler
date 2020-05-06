package ast;
import environment.*;

/**
 * Class Number stores an integer value.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Number extends Expression
{
    // instance variables - replace the example below with your own
    private int val;

    /**
     * Constructor for objects of class Number
     * @param v     the value to be stored
     */
    public Number(int v)
    {
        val = v;
    }
    
    /**
     * Overrides eval method from abstract class Expression.
     * Returns the number stored in the class
     * @param e     the environment in which to evaluate the number
     * @return      the value of the number
     */
    public Object eval(Environment e) 
    {
        return val;
    }
    
    /**
     * Overrides compile method from abstract class Expression.
     * Stores number into the register.
     * @param e     the emitter used to write code to the MIPS file.
     */
    public void compile(Emitter e)
    {
        e.emit("#load value into $v0");
        e.emit("li $v0 "+val);
    }
}
