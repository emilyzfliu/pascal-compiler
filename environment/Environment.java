package environment;
import java.util.*;
import ast.*;

/**
 * Class Environment manages a collection of variables and procedures. Environment objects are
 * called as parameters in the AST as all the variables are stored in an Environment.
 * 
 * @author Emily Liu
 * @version April 8, 2020
 */
public class Environment
{
    // instance variables - replace the example below with your own
    private HashMap<String, Integer> variables;
    private HashMap<String, ProcedureDeclaration> procedures;
    private Environment parent;
    
    /**
     * Constructor for class Environment.
     * @param vars          the hashmap used to maintain the variable
     *                      names and values.
     * @param prs           the hashmap used to maintain the procedure 
     *                      names and declarations
     * @param e             the parent environment
     */
    public Environment(HashMap<String, Integer> vars,
                HashMap<String, ProcedureDeclaration> prs, Environment e) 
    {
        variables = vars;
        procedures = prs;
        parent = e;
    }
    
    /**
     * Stores a variable and its corresponding integer value
     * into the hashmap.
     * @param variable      the name of the variable to be stored
     * @param value         the value of the variable to be stored
     */
    public void setVariable (String variable, int value) 
    {
        if (this.getVariables().contains(variable)) 
        {
            variables.put(variable, value);
        }
        else if (parent !=null && parent.getVariables()!=null
                && parent.getVariables().contains(variable))
            parent.setVariable(variable, value);
        else variables.put(variable, value);
    }
    
    /**
     * Retreives the value of a variable from the hashmap.
     * @param variable      the name of the variable to retrieve
     *                      the value for
     * @return              the value of the given variable
     */
    public int getVariable (String variable) 
    {
        //System.out.println("getting "+variable+" value");
        if (variables.containsKey(variable))
            return variables.get(variable);
        else if (parent.getVariables().contains(variable))
            return parent.getVariable(variable);
        variables.put(variable, 0);
        return 0;
    }
    
    /**
     * Retreives a procedure declaration from the hashmap.
     * @param pr        the name of the procedure to retrieve
     * @return          the procedure declaration with the given name
     */
    public ProcedureDeclaration getProcedure (String pr)
    {
        if (procedures.containsKey(pr)) return procedures.get(pr); 
        else return parent.getProcedure(pr);
    }
    
    /**
     * Stores a procedure name and its corresponding declaration
     * in the hashmap
     * @param p         the name of the procedure
     * @param s         the procedure declaration
     */
    public void setProcedure(String p, ProcedureDeclaration s) 
    {
        if (parent !=null && parent.getProcedures()!=null && !parent.getProcedures().contains(p))
            parent.setProcedure(p, s);
        procedures.put(p, s);
    }
    
    /**
     * Returns a set containing all variable names in the environment
     * @return  a set containing all variable names in the environment
     */
    public Set<String> getVariables() 
    {
        return variables.keySet();
    }
    
    /**
     * Returns a set containing all procedure names in the environment
     * @return  a set containing all procedure names in the environment
     */
    public Set<String> getProcedures() 
    {
        return procedures.keySet();
    }
    
    /**
     * Returns the parent environment of the current environment. If the 
     * parent is null (the current environment is global), returns
     * the current environment.
     * @return      the parent environment, or this if parent is null
     */
    public Environment getParent() 
    {
        if (parent==null) return this;
        return parent.getParent();
    }
}
