/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface InputPacket {
    /**
     * return the packet id (2 or 3 chars)
     * @return 
     */
    public String id();
    
    /**
     * Perform the packet
     * @param extra the extra data (packet minus the id)
     * @param session the curent user session
     */
    public void perform(String extra, IoSession session);
}
