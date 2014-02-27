package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.item.ItemStats;
import org.pvemu.jelly.utils.Utils;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

@Deprecated
public class ExchangeEvents {

    public static void onRequest(IoSession session, String data) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        int action, id;

        try {
            String[] param = Utils.split(data, "|");//data.split("\\|");
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
    
    public static void onAccept(IoSession session){
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.getExchange() == null){
            GamePacketEnum.EXCHANGE_CREATE_ERROR.send(session);
            return;
        }
        
        GamePacketEnum.EXCHANGE_CREATE_OK.send(session, 1);
        GamePacketEnum.EXCHANGE_CREATE_OK.send(p.getExchange().getTarget().getSession(), 1);
    }
    
    public static void onLeave(IoSession session){
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.getExchange() == null){
            return;
        }
        
        Player T = p.getExchange().getTarget();
        
        p.stopExchange();
        T.stopExchange();
        
        GamePacketEnum.EXCHANGE_LEAVE.send(session);
        GamePacketEnum.EXCHANGE_LEAVE.send(T.getSession());
    }
    
    public static void onMove(IoSession session, String data){
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null || p.getExchange() == null){
            return;
        }
        
        switch(data.charAt(0)){
            case 'O': //échange d'objets
                int itemID, qu;
                try{
                    String[] params = Utils.split(data.substring(2), "|");//data.substring(2).split("\\|");
                    itemID = Integer.parseInt(params[0]);
                    qu = Integer.parseInt(params[1]);
                }catch(Exception e){
                    GamePacketEnum.EXCHANGE_MOVE_ERROR.send(session);
                    return;
                }
                
                GamePacketEnum.EXCHANGE_OK.send(session, "0" + p.getID());
                GamePacketEnum.EXCHANGE_OK.send(session, "0" + p.getExchange().getTarget().getID());
                GamePacketEnum.EXCHANGE_OK.send(p.getExchange().getTarget().getSession(), "0" + p.getID());
                GamePacketEnum.EXCHANGE_OK.send(p.getExchange().getTarget().getSession(), "0" + p.getExchange().getTarget().getID());
                
                if(data.charAt(1) == '+'){
                    if((qu = p.getExchange().addItem(itemID, qu)) == -1){
                        GamePacketEnum.EXCHANGE_MOVE_ERROR.send(session);
                        return;
                    }
                }else{
                    if((qu = p.getExchange().removeItem(itemID, qu)) == -1){
                        GamePacketEnum.EXCHANGE_MOVE_ERROR.send(session);
                        return;
                    }
                }
                
                String p1 = qu == 0 ? "O-" + itemID : new StringBuilder().append("O+").append(itemID).append('|').append(qu).toString();
                ItemStats IS = p.getInventory().getItemById(itemID).getItemStats();
                String p2 = qu == 0 ? p1 : new StringBuilder().append(p1).append('|').append(IS.getID()).append('|').append(IS.statsToString()).toString();
                
                GamePacketEnum.EXCHANGE_LOCAL_MOVE_OK.send(session, p1);
                GamePacketEnum.EXCHANGE_DISTANT_MOVE_OK.send(p.getExchange().getTarget().getSession(), p2);
                break;
        }
    }
}
