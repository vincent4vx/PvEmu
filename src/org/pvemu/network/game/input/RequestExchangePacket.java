/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RequestExchangePacket implements InputPacket {

    @Override
    public String id() {
        return "ER";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        int action, id;

        try {
            String[] param = Utils.split(extra, "|");//data.split("\\|");
            action = Integer.parseInt(param[0]);
            id = Integer.parseInt(param[1]);
        } catch (Exception e) {
            GamePacketEnum.EXCHANGE_ERROR.send(session);
            return;
        }

        switch (action) {
            case 1:
                Player T = (Player) p.getMap().getGMable(id);

                if (T == null) {
                    GamePacketEnum.EXCHANGE_ERROR.send(session);
                    return;
                }

                if (T.getExchange() != null) {
                    GamePacketEnum.EXCHANGE_ERROR.send(session, "O");
                    return;
                }
                
                String packet = p.getID() + "|" + T.getID() + "|1";
                p.startExchange(T);
                T.startExchange(p);
                GamePacketEnum.EXCHANGE_REQUEST_OK.send(session, packet);
                GamePacketEnum.EXCHANGE_REQUEST_OK.send(T.getSession(), packet);
                break;
        }
        
    }
    
}
