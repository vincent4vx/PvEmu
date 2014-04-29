/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InformativeMessageGenerator {
    public String generateIm(int msgID, Object... args){
        return new StringBuilder().append(msgID).append(';').append(Utils.join(args, "~")).toString();
    }
    
    public String generateIm(Pair<Integer, Object[]>[] msgs){
        StringBuilder packet = new StringBuilder();
        
        for(Pair<Integer, Object[]> msg : msgs){
            packet.append(generateIm(msg.getFirst(), msg.getSecond())).append('|');
        }
        
        return packet.substring(0, packet.length() - 1);
    }
}
