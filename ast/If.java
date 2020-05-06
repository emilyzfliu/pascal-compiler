package ast;
import environment.*;

/**
 * Class If accepts a conditional and any statement, if the statement is an IF/THEN statement.
 * If it is an IF/THEN/ELSE statement, then the If object accepts two statements, one to
 * be executed if the conditional is true and the other to be executed if it is false.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class If extends Statement
{
    // instance variables - replace the example below with your own
    private Condition cond;
    private Statement s1;
    private Statement s2;
    /**
     * Constructor for class if, for IF/THEN statements
     * @param c     the condition to be evaluated
     * @param st1   the statement to be executed if c is satisfied
     */
    public If(Condition c, Statement st1)
    {
        cond = c;
        s1 = st1;
    }
    
    /**
     * Constructor for class if, for IF/THEN/ELSE statements
     * @param c     the condition to be evaluated
     * @param st1   the statement to be executed if c is satisfied
     * @param st2   the statement to be executed if c is not satisfied
     */
    public If(Condition c, Statement st1, Statement st2)
    {
        cond = c;
        s1 = st1;
        s2 = st2;
    }

    /**
     * Overrides exec method in abstract class Statement. If c is true,
     * execute s1, if c is false and s2 exists, execute s2 (and do nothing
     * if s2 does not exist)
     * @param e         the Environment in which to execute the if statement
     */
    public void exec(Environment e)
    {
        if ((Boolean)cond.eval(e)) 
        {
            s1.exec(e);
        }
        else 
        {
            if (s2!=null) s2.exec(e);
        }
    }
    
    /**
     * Overrides compile method in abstract class Statement. Sets up a branch
     * label containing the compiled statement if the condition is satisfied;
     * otherwise, jumps to directly after the label with the statement.
     * @param e     the emitter used to write code to the MIPS file.
     */
    public void compile(Emitter e)
    {
        String label = "endif"+e.nextLabelID();
        String jlabel = "j"+label;
        cond.compile(e, label);
        e.emit("#setting up jump");
        e.emit("j "+jlabel);
        e.emit(label+":");
        s1.compile(e);
        e.emit("#continue with code");
        e.emit(jlabel+":");
    }
}
