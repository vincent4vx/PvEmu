/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.FilterFactory;

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
    
    abstract public void perform(String[] args, Asker asker);
    
    public String title(){
        return name();
    }
    
    public String[] usage(){
        return new String[]{
            "Commande non documenté."
        };
    }
    
    public Filter conditions(){
        return FilterFactory.yesFilter();
    }
}
