package org.pvemu.commands.argument;

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
        return String.format(
                "Invalid argument %d%s", 
                argumentNumber,
                message == null ? "" : " : " + message
        );
    }    
}
