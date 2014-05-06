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
public class LockedArgumentList extends Exception{

    public LockedArgumentList() {
        super("Cannot add an argument to a locked argument list");
    }
    
}
