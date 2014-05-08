package org.pvemu.commands.parser;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ParserError extends Exception{
    final private String commandLine;
    final private String message;
    final private int col;

    public ParserError(String commandLine, String message, int col) {
        this.commandLine = commandLine;
        this.message = message;
        this.col = col;
    }

    @Override
    public String getMessage() {
        String msg = "[Parser error] " + message + " at column " + col + " near : '";
        int begin = col - 4;
        
        if(begin < 0)
            begin = 0;
        
        int end = col + 4;
        
        if(end > commandLine.length())
            end = commandLine.length();
        
        return msg + commandLine.substring(begin, end) + "'";
    }
    
    
    
}
