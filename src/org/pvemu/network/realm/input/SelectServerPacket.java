/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.realm.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Constants;
import org.pvemu.models.Account;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GameServer;
import org.pvemu.network.realm.RealmPacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SelectServerPacket implements InputPacket {

    @Override
    public String id() {
        return "AX";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Account acc = SessionAttributes.ACCOUNT.getValue(session);

        if (acc == null) {
            session.close(true);
            return;
        }

        String ticket = acc.setWaiting(); //plus sécurisé que l'id du compte... Retourne un id aléatoire

        if (Config.getBool("CRYPT_IP") || Constants.DOFUS_VER_ID <= 1200) {
            RealmPacketEnum.SELECT_SERVER_CRYPT.send(session, GameServer.CRYPT_IP + ticket);
        } else {
            String p = Config.getString("ip", "127.0.0.1") + ";" + Config.getString("game_port", "5555") + ";" + ticket;
            RealmPacketEnum.SELECT_SERVER.send(session, p);
        }
    }
    
}
