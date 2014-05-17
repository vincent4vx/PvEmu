/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.basic;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.chat.ChatHandler;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MessagePacket implements InputPacket {

    @Override
    public String id() {
        return "BM";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);

        if (p == null) {
            return;
        }

        String[] args = Utils.split(extra, "|");
        
        if(args.length < 2){
            return;
        }
        
        ChatHandler.instance().parse(args, p);
    }
    
}
