package ast;
import environment.*;

/**
 * Class Condition takes two expressions and a relative operator of type 
 * {<, >, =, <>, <=, >=} and evaluates whether the boolean expression
 * is true or false.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Condition extends Expression
{
    // instance variables - replace the example below with your own
    private String relop;
    private Expression e1;
    private Expression e2;

    /**
     * Constructor for objects of class Condition
     * @param r     the relative operator, must be one of {<, >, =, <>, <=, >=}
     * @param ex1   the first expression to be evaluated
     * @param ex2   the second expression to be evaluated
     */
    public Condition(String r, Expression ex1, Expression ex2)
    {
        relop = r;
        e1 = ex1;
        e2 = ex2;
    }

    /**
     * Overrides eval method from abstract class Expression.
     * Evaluates whether or not the condition is true and
     * returns a Boolean object (either true or false)
     * @param e     the environment in which to evaluate the conditional
     * @return      true if the condition is true
     *              false otherwise
     */
    public Object eval(Environment e)
    {
        if (relop.equals(">")) return (Integer)e1.eval(e)>(Integer)e2.eval(e);
        if (relop.equals("<")) return (Integer)e1.eval(e)<(Integer)e2.eval(e);
        if (relop.equals("=")) return (Integer)e1.eval(e)==(Integer)e2.eval(e);
        if (relop.equals("<>")) return (Integer)e1.eval(e)!=(Integer)e2.eval(e);
        if (relop.equals(">=")) return (Integer)e1.eval(e)>=(Integer)e2.eval(e);
        if (relop.equals("<=")) return (Integer)e1.eval(e)<=(Integer)e2.eval(e);
        return null;
    }
    
    /**
     * Overrides compile method from abstract class Expression
     * Compiles first operand, stores in temporary register, compiles
     * second operand, compares the two operands and branches accordingly
     * @param e         the emitter used to write to the MIPS file
     * @param label     the name of the label in the MIPS file
     *                  that the code branches to if the condition
     *                  is satisfied
     */
    public void compile(Emitter e, String label) 
    {
        e1.compile(e);
        e.emit("move $t0 $v0");
        e2.compile(e);
        e.emit("#branching according to conditional");
        if (relop.equals(">")) e.emit("bgt $t0 $v0 "+label);
        if (relop.equals("<")) e.emit("blt $t0 $v0 "+label);
        if (relop.equals("=")) e.emit("beq $t0 $v0 "+label);
        if (relop.equals("<>")) e.emit("bne $t0 $v0 "+label);
        if (relop.equals(">=")) e.emit("bge $t0 $v0 "+label);
        if (relop.equals("<=")) e.emit("ble $t0 $v0 "+label);
    }
}
