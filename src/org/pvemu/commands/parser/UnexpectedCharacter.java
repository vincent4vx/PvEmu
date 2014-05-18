package org.pvemu.commands.parser;

import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class UnexpectedCharacter extends ParserError{

    public UnexpectedCharacter(String commandLine, char c, int col) {
        super(
                commandLine, 
                I18n.tr(Commands.UNEXPECTED_CHAR, c), 
                col
        );
    }
    
}
