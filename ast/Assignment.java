package ast;
import environment.*;

/**
 * Class Assignment extends Statement and takes a String variable
 * and an Expression exp, and associates the value of the expression
 * with the variable name in a given environment.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Assignment extends Statement
{
    // instance variables - replace the example below with your own
    private String var;
    private Expression exp;

    /**
     * Constructor for objects of class Assignment
     * @param v     the variable name
     * @param e     the expression to be associated with the variable
     */
    public Assignment(String v, Expression e)
    {
        var = v;
        exp = e;
    }
    
    /**
     * Overrides exec method from Statement. Stores the variable
     * and value as a pair in a Map object.
     * @param e     the Environment in which to assign the variable
     *              its value
     */
    public void exec(Environment e)
    {
        e.setVariable(var, (Integer)exp.eval(e));
    }
    
    /**
     * Overrides compile method from Statement. Assigns the variable
     * to the value specified by the expression.
     * @param e     the emitter used to write to the MIPS file.
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("#assigning value to variable "+var);
        e.emit("sw $v0 "+var);
    }

}
