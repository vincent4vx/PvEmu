package com.oldofus.network.events;

import com.oldofus.game.World;
import com.oldofus.game.objects.Player;
import java.net.InetSocketAddress;
import com.oldofus.jelly.Config;
import com.oldofus.jelly.Constants;
import com.oldofus.jelly.Utils;
import com.oldofus.models.Account;
import org.apache.mina.core.session.IoSession;
import com.oldofus.network.game.GamePacketEnum;
import com.oldofus.network.game.GameServer;
import com.oldofus.network.realm.RealmPacketEnum;

public class AccountEvents {
    //TODO : data correspond à l'id du Game Server (à utiliser en cas de multi-serveurs)

    public static void onServerSelected(IoSession session, String data) {
        Account acc = (Account) session.getAttribute("account");

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

    public static void onCharactersList(IoSession session) {
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
    }

    public static void onAttach(IoSession session, String ticket) {
        Account acc = Account.getWaitingAccount(ticket);

        if (acc == null) {
            GamePacketEnum.ACCOUNT_ATTACH_ERROR.send(session);
            session.close(true);
            return;
        }

        InetSocketAddress ISA = (InetSocketAddress) session.getRemoteAddress();
        if (!acc.isWaiting(ISA.getAddress().getHostAddress())) {
            GamePacketEnum.ACCOUNT_ATTACH_ERROR.send(session);
            session.close(true);
            return;
        }

        acc.setSession(session);

        acc.removeWaiting();

        if (Constants.DOFUS_VER_ID >= 1100) {
            GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
        } else {
            Player p = acc.getWaitingCharacter();
            session.setAttribute("player", p);
            
            if(p == null){
                return;
            }

            World.addOnline(p);
            p.setSession(session);

            //génération du packet ASK
            StringBuilder param = new StringBuilder();

            param.append('|').append(p.getID()).append("|").append(p.getName()).append("|")
                    .append(p.getLevel()).append("|").append(p.getClassID()).append("|")
                    .append(p.getSexe()).append("|").append(p.getGfxID()).append("|")
                    .append(Utils.implode("|", p.getColors())).append("|");

            param.append(p.getInventory().toString());
            
            GamePacketEnum.ACCOUNT_ATTACH_OK.send(session, param);
        }
    }
}
