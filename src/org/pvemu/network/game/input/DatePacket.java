/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.network.InputPacket;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DatePacket implements InputPacket {

    @Override
    public String id() {
        return "BD";
    }

    @Override
    public void perform(String extra, IoSession session) {
        GameSendersRegistry.getBasic().date(session);
        GameSendersRegistry.getBasic().time(session);
    }
    
}
