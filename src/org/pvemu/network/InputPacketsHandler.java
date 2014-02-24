/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network;

import java.util.HashMap;
import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class InputPacketsHandler {
    final private HashMap<String, InputPacket> packets = new HashMap<>();
    
    final static private int MIN_ID_SIZE = 2;
    final static private int MAX_ID_SIZE = 3;
    
    public void registerPacket(InputPacket packet){
        packets.put(packet.id(), packet);
    }
    
    public void parsePacket(String packet, IoSession session){
        if(packet.length() < MIN_ID_SIZE)
            return;
        
        InputPacket obj = null;  
        
        final int maxSize = packet.length() >= MAX_ID_SIZE ? MAX_ID_SIZE : packet.length();
        int idSize;
        for(idSize = MIN_ID_SIZE; idSize <= maxSize
                && (obj = packets.get(packet.substring(0, idSize))) == null; ++idSize){}
        
        if(obj == null){
            Loggin.debug("Packet non reconnu : '%s'", packet);
            return;
        }
        
        String extra = packet.length() >= idSize ? packet.substring(idSize) : "";
        obj.perform(extra, session);
    }
}
