package ast;
import java.io.*;

/**
 * Class Emitter writes MIPS assembly code to a synthesis file and keeps track of labels.
 * @author Emily Liu
 * @version May 5, 2020
 */
public class Emitter
{
    private PrintWriter out;
    private int labelID;

    /**
     * Constructor for class Emitter. Creates an emitter for writing to a new file
     * with the given name.
     * @param outputFileName        name of the file to write code to
     */
    public Emitter(String outputFileName)
    {
        labelID = 0;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prints one line of code to file (with non-labels indented)
     * @param code      the line of code to print to the file
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * Closes the file. Close method should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }
    
    /**
     * Pushes the contents in the given register onto the stack
     * @param reg       the registers whose contents to push onto the stack.
     */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw "+reg+" ($sp)");
    }
    
    /**
     * Pops the top element of the stack into the given register.
     * @param reg       the register with which to store the element removed
     *                  from the top of the stack
     */
    public void emitPop(String reg)
    {
        emit("lw "+reg+" ($sp)");
        emit("addu $sp $sp 4");
    }
    
    /**
     * Tracks, returns, and increments the label ID, starting from 1, to keep all labels distinct
     * @return      the current label ID
     */
    public int nextLabelID()
    {
        int ret = labelID;
        labelID++;
        return ret;
    }
}
