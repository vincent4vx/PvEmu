package org.pvemu.commands.argument;

import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CommandArgumentException extends Exception{
    final private int argumentNumber;
    final private String message;
    
    public CommandArgumentException(int argumentNumber) {
        this.argumentNumber = argumentNumber;
        message = null;
    }

    public CommandArgumentException(int argumentNumber, String message) {
        this.argumentNumber = argumentNumber;
        this.message = message;
    }

    @Override
    public String getMessage() {
        /*return String.format(
                "Invalid argument %d%s", 
                argumentNumber,
                message == null ? "" : " : " + message
        );*/
        
        return I18n.tr(Commands.INVALID_ARGUMENT, argumentNumber) + (message == null ? "" : " : " + message);
    }
}
