package ast;
import environment.*;

/**
 * Class Variable stores a String containing the variable name
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Variable extends Expression
{
    // instance variables - replace the example below with your own
    private String name;

    /**
     * Constructor for objects of class Variable
     * @param n     the name of the variable
     */
    public Variable(String n)
    {
        name = n;
    }
    
    /**
     * Overrides eval method from abstract class Expression.
     * Returns the name of the variable
     * @param e     the environment in which to evaluate the variable
     * @return      the name of the variable
     */
    public Object eval (Environment e) 
    {
        return e.getVariable(name);
    }
    
    /**
     * Gets the name of the variable.
     * @return      the name of the variable
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Overrides compile method from abstract class Expression.
     * Loads the value of the variable onto the $v0 register
     * @param e     the emitter used to write code to the MIPS file
     */
    public void compile(Emitter e)
    {
        e.emit("#load variable value onto $v0");
        e.emit("la $t0 "+name);
        e.emit("lw $v0 ($t0)");
    }
}
