/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Account;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DeleteCharacterPacket implements InputPacket {

    @Override
    public String id() {
        return "AD";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Account acc = SessionAttributes.ACCOUNT.getValue(session);//(Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        String response = "";
        int id = 0;

        try {
            String[] data = Utils.split(extra, "|");//packet.split("\\|");
            id = Integer.parseInt(data[0]);
            if (data.length > 1) {
                response = data[1];
            }
        } catch (Exception e) {
            GamePacketEnum.CHARACTER_DELETE_ERROR.send(session);
            return;
        }

        org.pvemu.models.Character chr = acc.getCharacter(id);

        if (chr == null) {
            GamePacketEnum.CHARACTER_DELETE_ERROR.send(session);
            return;
        }

        if (chr.level >= 20 && !response.equalsIgnoreCase(acc.response)) {
            GamePacketEnum.CHARACTER_DELETE_ERROR.send(session);
            return;
        }

        acc.deleteCharacter(id);
//        GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
        GameSendersRegistry.getAccount().charactersList(session, acc);
        
    }
    
}
