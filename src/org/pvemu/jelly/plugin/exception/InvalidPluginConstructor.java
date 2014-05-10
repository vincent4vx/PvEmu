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
public class InvalidPluginConstructor extends PluginException{

    public InvalidPluginConstructor(String plugin, String clazz, String message) {
        super(plugin, "Invalid class : " + clazz + " - " + message);
    }
    
}
