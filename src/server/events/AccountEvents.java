package server.events;

import jelly.Config;
import models.Account;
import org.apache.mina.core.session.IoSession;
import server.game.GameServer;
import server.realm.RealmPacketEnum;

public class AccountEvents {
    //TODO : data correspond à l'id du Game Server (à utiliser en cas de multi-serveurs)
    public static void onServerSelected(IoSession session, String data) {
        Account acc = (Account) session.getAttribute("account");
        
        if(acc == null){
            session.close(true);
            return;
        }
        
        if (Config.getBool("CRYPT_IP")) {
            RealmPacketEnum.SELECT_SERVER_CRYPT.send(session, GameServer.CRYPT_IP + acc.getPk());
        }else{
            String p = Config.getString("ip", "127.0.0.1") + ";" + Config.getString("game_port", "5555") + ";" + acc.getPk();
            RealmPacketEnum.SELECT_SERVER.send(session, p);
        }
        acc.setWaiting();
    }
}
