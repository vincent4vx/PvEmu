/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.models.Account;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Command {
    /**
     * Get the command name, use to identify him.
     * @return the command name
     */
    abstract public String name();
    
    abstract public void perform(String[] args, Account acc);
}
