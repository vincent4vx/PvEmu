/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import java.net.InetSocketAddress;
import org.apache.mina.core.session.IoSession;
import org.pvemu.game.World;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
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
public class AttachAccountPacket implements InputPacket {

    @Override
    public String id() {
        return "AT";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Account acc = Account.getWaitingAccount(extra);

        if (acc == null) {
            GamePacketEnum.ACCOUNT_ATTACH_ERROR.send(session);
            Loggin.debug("not exists");
            session.close(true);
            return;
        }

        InetSocketAddress ISA = (InetSocketAddress) session.getRemoteAddress();
        if (!acc.isWaiting(ISA.getAddress().getHostAddress())) {
            GamePacketEnum.ACCOUNT_ATTACH_ERROR.send(session);
            Loggin.debug("bad ip");
            session.close(true);
            return;
        }

        acc.setSession(session);

        acc.removeWaiting();

        if (Constants.DOFUS_VER_ID >= 1100) {
            //GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
            GameSendersRegistry.getAccount().charactersList(session, acc);
        } else {
            Player p = acc.getWaitingCharacter();
            SessionAttributes.PLAYER.setValue(p, session);
            
            if(p == null){
                return;
            }

            World.instance().addOnline(p);
            p.setSession(session);

            //génération du packet ASK
            StringBuilder param = new StringBuilder();

            param.append('|').append(p.getID()).append("|").append(p.getName()).append("|")
                    .append(p.getLevel()).append("|").append(p.getClassID()).append("|")
                    .append(p.getSexe()).append("|").append(p.getGfxID()).append("|")
                    .append(Utils.join(p.getColors(), "|")).append("|");

            param.append(p.getInventory().toString());
            
            GamePacketEnum.ACCOUNT_ATTACH_OK.send(session, param);
        }
    }
    
}
