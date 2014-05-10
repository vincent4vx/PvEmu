/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.plugin.exception;

import org.pvemu.jelly.Constants;
import org.pvemu.jelly.utils.VersionMatcher;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class IncompatiblePluginException extends PluginException{

    public IncompatiblePluginException(String plugin, VersionMatcher need) {
        super(plugin, "its needs " + need + " while the emu is on version " + Constants.VERSION);
    }
    
}
