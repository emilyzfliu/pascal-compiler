package ast;
import environment.*;
import java.util.*;

/**
 * Class ProcedureCall accepts a procedure name and a list of arguments.
 * During evaluation, ProcedureCall instantaites a new environment and
 * defines all the arguments as variables in the new environment.
 * 
 * @author Emily Liu
 * @version April 8, 2020
 */
public class ProcedureCall extends Expression
{
    // instance variables - replace the example below with your own
    private String name;
    private ArrayList<Expression> vals;
    
    /**
     * Constructor for objects of class ProcedureCall
     * @param n     the name of the procedure
     * @param v     the list of arguments
     */
    public ProcedureCall(String n, ArrayList<Expression> v)
    {
        name = n;
        vals = v;
    }

    /**
     * Overrides eval method from abstract class Expression.
     * Executes the procedure as defined by the procedure declaration
     * stored in the parent environment e.
     * 
     * @param  e   the parent environment in which to evaluate the procedure
     * @return     the return value of the procedure
     */
    public Object eval(Environment e)
    {
        ProcedureDeclaration pro = e.getProcedure(name);
        ArrayList<String> names = pro.getParams();
        HashMap<String, Integer> vars = new HashMap<String, Integer>();
        for (int i=0; i<names.size()-1; i++)
        {
            vars.put(names.get(i), (int)vals.get(i).eval(e));
        }
        vars.put(names.get(names.size()-1), 0);
        Environment e1 = new Environment(vars,
                            new HashMap<String, ProcedureDeclaration>(), e.getParent());
        pro.execStatement(e1);
        return e1.getVariable(name);
    }
}
