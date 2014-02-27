package org.pvemu.network.events;

import org.pvemu.game.World;
import org.pvemu.game.objects.Player;
import java.net.InetSocketAddress;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Utils;
import org.pvemu.models.Account;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.GameServer;
import org.pvemu.network.game.output.GameSendersRegistry;
import org.pvemu.network.realm.RealmPacketEnum;

public class AccountEvents {
    //TODO : data correspond à l'id du Game Server (à utiliser en cas de multi-serveurs)

    public static void onServerSelected(IoSession session, String data) {
        Account acc = SessionAttributes.ACCOUNT.getValue(session);//(Account) session.getAttribute("account");

        if (acc == null) {
            session.close(true);
            return;
        }

        String ticket = acc.setWaiting(); //plus sécurisé que l'id du compte... Retourne un id aléatoire

        if (Config.CRYPT_IP.getValue() || Constants.DOFUS_VER_ID <= 1200) {
            RealmPacketEnum.SELECT_SERVER_CRYPT.send(session, GameServer.CRYPT_IP + ticket);
        } else {
            String p = Config.IP.getValue() + ";" + Config.GAME_PORT.getValue().toString() + ";" + ticket;
            RealmPacketEnum.SELECT_SERVER.send(session, p);
        }
    }

    public static void onCharactersList(IoSession session) {
        Account acc = SessionAttributes.ACCOUNT.getValue(session);//(Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        //GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
        GameSendersRegistry.getAccount().charactersList(session, acc);
    }

/*    public static void onAttach(IoSession session, String ticket) {
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
            //session.setAttribute("player", p);
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
                    .append(Utils.implode("|", p.getColors())).append("|");

            param.append(p.getInventory().toString());
            
            GamePacketEnum.ACCOUNT_ATTACH_OK.send(session, param);
        }
    }*/
}
