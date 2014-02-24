/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Utils;
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
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        String[] args = Utils.split(extra, "|");
        
        if(args.length < 2){
            return;
        }
        
        if(args[1].charAt(0) == '!'){
            switch(args[1].toLowerCase()){
                case "!console":
                    session.write("BAPtest");
                    session.write("BAT2Welcome !");
                    break;
            }
            return;
        }

        switch (args[0]) {
            case "*": //canal noir (map)
                StringBuilder b = new StringBuilder();
                b.append("|").append(p.getID()).append("|").append(p.getName()).append("|").append(args[1]);
                String msg = b.toString();
                GamePacketEnum.CHAT_MESSAGE_OK.sendToMap(p.getMap(), msg);
                break;
        }
        
    }
    
}
