package ast;
import environment.*;
import java.util.*;

/**
 * Class ProcedureDeclaration accepts a procedure name, the statement
 * to be evaluated inside the procedure, and a list of arguments.
 * During execution, the object stores itself under the given name
 * within the given environment.
 * 
 * @author Emily Liu
 * @version April 8, 2020
 */
public class ProcedureDeclaration extends Statement
{
    // instance variables - replace the example below with your own
    private String name;
    private Statement s;
    private ArrayList<String> params;

    /**
     * Constructor for objects of class ProcedureDeclaration
     * @param n     the name of the procedure
     * @param st    the statement containing code within the procedure
     * @param p     the list of arguments for the procedure
     */
    public ProcedureDeclaration(String n, Statement st, ArrayList<String> p)
    {
        name = n;
        s = st;
        params = p;
        params.add(name);
    }

    /**
     * Overrides exec method from abstract class Statement.
     * Stores the procedure declaration object within the parent environment.
     * 
     * @param  e   the environment in which to execute the procedure declaration
     */
    public void exec(Environment e)
    {
        e.setProcedure(name, this);
    }
    
    /**
     * returns the parameters/arguments of the procedure
     * @return      the arguments of the procedure
     */
    public ArrayList<String> getParams() 
    {
        return params;
    }
    
    /**
     * Executes the statement of the procedure
     * @param e     the environment in which to execute the statement
     */
    public void execStatement(Environment e) 
    {
        s.exec(e);
    }
}
