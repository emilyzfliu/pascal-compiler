package ast;
import environment.*;

/**
 * Class While extends Statement and takes a Condition and Statement
 * as instance variables and executes the statement
 * so long as the Condition is satisfied and evaluates to true.
 * 
 * @author Emily Liu 
 * @version May 5, 2020
 */
public class While extends Statement
{
    // instance variables - replace the example below with your own
    private Condition c;
    private Statement s;
    
    /**
     * Constructor for objects of class While.
     * @param cond      the condition to be satisfied
     * @param st        the statement to execute
     */
    public While(Condition cond, Statement st)
    {
        c = cond;
        s = st;
    }

    /**
     * Overrides exec method in abstract class Statement. While c is true,
     * execute the statements contained in s.
     * @param e         the Environment in which to execute the while loop
     */
    public void exec(Environment e)
    {
        while ((Boolean)c.eval(e))
        {
            s.exec(e);
        }
    }
    
    /**
     * Overrides compile method in abstract class Statement. Sets up a while
     * label with the compiled statement if the statement is true. Continues
     * to loop through the program until the statement is no longer true. If
     * the statement is never true, jumps to the code after the loop.
     * @param e     the emitter used to write code to the MIPS file
     */
    public void compile(Emitter e)
    {
        String label = "while"+e.nextLabelID();
        String jlabel = "j"+label;
        c.compile(e, label);
        e.emit("j "+jlabel);
        e.emit(label+":");
        s.compile(e);
        c.compile(e, label);
        e.emit(jlabel+":");
    }
}
