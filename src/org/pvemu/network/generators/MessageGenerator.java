/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.jelly.Constants;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MessageGenerator {
    public String generateErrorServerMessage(String msg){
        return generateColoredServerMessage(Constants.COLOR_RED, msg);
    }
    
    public String generateInfoServerMessage(String msg){
        return generateColoredServerMessage(Constants.COLOR_GREEN, msg);
    }
    
    public String generateColoredServerMessage(String hexColor, String msg){
        return "<font color=\"#" + hexColor + "\">" + msg + "</font>";
    }
}
