/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.jelly.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InformativeMessageGenerator {
    public String generateIm(int msgID, Object... args){
        return new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)).toString();
    }
}
