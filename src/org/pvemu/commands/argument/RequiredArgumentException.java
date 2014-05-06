/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.argument;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RequiredArgumentException extends CommandArgumentException{

    public RequiredArgumentException(int argumentNumber) {
        super(argumentNumber, "it was required");
    }
    
}
