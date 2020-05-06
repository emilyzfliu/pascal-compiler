package ast;
import environment.*;
import java.util.*;

/**
 * Class Program maintains and executes the entire program. It is equivalent
 * to the root node of the abstract syntax tree. Class Program first stores
 * a list of procedures and then evaluates a single statement.
 * 
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Program
{
    // instance variables - replace the example below with your own
    private ArrayList<Variable> vars;
    private ArrayList<ProcedureDeclaration> prs;
    private Statement s;

    /**
     * Constructor for objects of class Program
     * @param st        the main statement of the program
     * @param v         the list of variables for the program
     * @param p         the list of procedures for the program
     */
    public Program(Statement st, ArrayList<Variable> v, ArrayList<ProcedureDeclaration> p)
    {
        s = st;
        vars = v;
        prs = p;
    }

    /**
     * Runs the entire program.
     * 
     * @param e    the environment in which to evaluate the program
     */
    public void exec(Environment e)
    {
        for (ProcedureDeclaration p: prs) 
        {
            p.exec(e);
        }
        s.exec(e);
    }
    
    /**
     * Sets up .data, .text, and the main (global) method in the MIPS assembly file,
     * as well as executing normal termination.
     * @param fileName      the name of the output file which contains the MIPS instructions
     */
    public void compile(String fileName)
    {
        Emitter e = new Emitter(fileName);
        e.emit(".data");
        e.emit("nl: .asciiz \"\\n\"");
        e.emit("#storing all variables");
        for (Variable v: vars)
        {
            e.emit(v.getName()+": .word 0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        s.compile(e);
        e.emit("#normal termination");
        e.emit("li $v0 10");
        e.emit("syscall");
        e.close();
    }
}
