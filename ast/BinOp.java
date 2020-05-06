package ast;
import environment.*;

/**
 * Class BinOp, for Binary Operation, takes in two expression and a symbol
 * of type {+, -, *, /} indicating whether to add, subtract, multiply,
 * or divide the two expressions, and evaluates accordingly.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class BinOp extends Expression
{
    // instance variables - replace the example below with your own
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for objects of class BinOp.
     * @param e1        The first expression to be evaluated
     * @param e2        The second expression to be evaluated
     * @param o         How the two expressions should be combined.
     *                  Must be one of {+, -, *, /}
     */
    public BinOp(Expression e1, Expression e2, String o)
    {
        op = o;
        exp1 = e1;
        exp2 = e2;
    }
    
    /**
     * Overrides method eval from Expression.
     * @param e     the environment in which to evaluate the binary operation
     * @return      an Integer object containing the values of exp1 and exp2,
     *              either added, subtracted, multiplied, or divided together
     *              according to op. If op is not one of {+, -, *, /}, returns null.
     */
    public Object eval(Environment e)
    {
        if (op.equals("+")) return (Integer)exp1.eval(e)+(Integer)exp2.eval(e);
        if (op.equals("-")) return (Integer)exp1.eval(e)-(Integer)exp2.eval(e);
        if (op.equals("*")) return (Integer)exp1.eval(e)*(Integer)exp2.eval(e);
        if (op.equals("/")) return (Integer)exp1.eval(e)/(Integer)exp2.eval(e);
        return null;
    }
    
    /**
     * Overrides method compile from Expression.
     * Pushes first element of binop onto stack and loads
     * second element onto register, performs the operation
     * and stores it in register
     * @param e     the emitter used to write to the MIPS file
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emit("#push elements of $v0 onto stack");
        e.emitPush("$v0");
        exp2.compile(e);
        e.emit("#pop top of stack onto $t0");
        e.emitPop("$t0");
        e.emit("#perform binary operation");
        if (op.equals("+")) e.emit("addu $v0 $t0 $v0");
        if (op.equals("-")) e.emit("subu $v0 $t0 $v0");
        if (op.equals("*")) e.emit("mul $v0 $t0 $v0");
        if (op.equals("/")) e.emit("div $v0 $t0 $v0");
    }
}
