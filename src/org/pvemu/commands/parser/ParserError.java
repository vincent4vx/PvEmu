package org.pvemu.commands.parser;

import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

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
        int begin = col - 4;
        
        if(begin < 0)
            begin = 0;
        
        int end = col + 4;
        
        if(end > commandLine.length())
            end = commandLine.length();
        
        return I18n.tr(Commands.PARSER_ERROR, message, col, commandLine.substring(begin, end));
    }
    
    
    
}
