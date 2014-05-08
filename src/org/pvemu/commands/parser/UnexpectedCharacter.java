package org.pvemu.commands.parser;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class UnexpectedCharacter extends ParserError{

    public UnexpectedCharacter(String commandLine, char c, int col) {
        super(
                commandLine, 
                "unexpected character " + c, 
                col
        );
    }
    
}
