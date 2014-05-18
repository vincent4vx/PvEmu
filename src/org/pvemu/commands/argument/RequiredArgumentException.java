/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.argument;

import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RequiredArgumentException extends CommandArgumentException{

    public RequiredArgumentException(int argumentNumber) {
        super(argumentNumber, I18n.tr(Commands.REQUIRED_ARGUMENT));
    }
    
}
