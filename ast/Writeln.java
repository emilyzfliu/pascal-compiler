package ast;
import environment.*;

/**
 * Class Writeln accepts an expression and prints the value
 * it evaluates to.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Writeln extends Statement
{
    // instance variables - replace the example below with your own
    private Expression exp;

    /**
     * Constructor for objects of class Writeln
     * @param e     the Expression to evaluate and print
     */
    public Writeln(Expression e)
    {
        // initialise instance variables
        exp = e;
    }
    
    /**
     * Overrides exec method in abstract class Statement.
     * Prints the value of expression exp
     * @param e         the Environment in which to execute the writeln statement
     */
    public void exec (Environment e) 
    {
        System.out.println(exp.eval(e));
    }
    
    /**
     * Overrides compile method in abstract class Statement
     * Prints the compiled value of an expression exp,
     * and then prints a new line
     * @param e     the emitter used to write code to the MIPS file
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("#load value in $v0 into $a0 and print");
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("#print new line");
        e.emit("li $v0 4");
        e.emit("la $a0 nl");
        e.emit("syscall");
    }
}
