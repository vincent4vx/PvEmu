/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.plugin.exception;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InvalidPluginFile extends PluginException{

    public InvalidPluginFile(String plugin, String message) {
        super(plugin, message);
    }
    
}
