/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.models.Account;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CharacterListPacket implements InputPacket {

    @Override
    public String id() {
        return "AL";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Account acc = SessionAttributes.ACCOUNT.getValue(session);

        if (acc == null) {
            return;
        }

        GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());        
    }
    
}
