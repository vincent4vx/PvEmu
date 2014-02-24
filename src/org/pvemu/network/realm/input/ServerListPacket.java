/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.realm.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.network.InputPacket;
import org.pvemu.network.realm.RealmPacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ServerListPacket implements InputPacket {

    @Override
    public String id() {
        return "Ax";
    }

    @Override
    public void perform(String extra, IoSession session) {
        RealmPacketEnum.SERVER_LIST.send(session);
    }
    
}
