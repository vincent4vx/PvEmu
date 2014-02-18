/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.jelly.filters.Filterable;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Asker extends Filterable {
    /**
     * Name of the asker
     * @return the name
     */
    abstract public String name();
    
    /**
     * Write a msg to the console
     * @param msg the message to write
     */
    abstract public void write(String msg);
    
    /**
     * Write an error message
     * @param msg the error message
     */
    abstract public void writeError(String msg);
    
    /**
     * Get the admin level
     * @return the admin level
     */
    public byte level();
}
