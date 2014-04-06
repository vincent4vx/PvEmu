/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ExchangeMovePacket implements InputPacket {

    @Override
    public String id() {
        return "EM";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null || p.getExchange() == null){
            return;
        }
        
        switch(extra.charAt(0)){
            case 'O': //échange d'objets
                int itemID, qu;
                try{
                    String[] params = Utils.split(extra.substring(2), "|");//data.substring(2).split("\\|");
                    itemID = Integer.parseInt(params[0]);
                    qu = Integer.parseInt(params[1]);
                }catch(Exception e){
                    GamePacketEnum.EXCHANGE_MOVE_ERROR.send(session);
                    return;
                }
                
                /*GamePacketEnum.EXCHANGE_OK.send(session, "0" + p.getID());
                GamePacketEnum.EXCHANGE_OK.send(session, "0" + p.getExchange().getTarget().getID());
                GamePacketEnum.EXCHANGE_OK.send(p.getExchange().getTarget().getSession(), "0" + p.getID());
                GamePacketEnum.EXCHANGE_OK.send(p.getExchange().getTarget().getSession(), "0" + p.getExchange().getTarget().getID());*/
                GameSendersRegistry.getExchange().exchangeOk(p, p.getExchange().getTarget(), false);
                GameSendersRegistry.getExchange().exchangeOk(p.getExchange().getTarget(), p, false);
                p.getExchange().setState(false);
                p.getExchange().getTarget().getExchange().setState(false);
                
                if(extra.charAt(1) == '+'){
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
                //ItemStats IS = p.getInventory().getItemById(itemID).getItemStats();
                GameItem item = p.getInventory().getItemById(itemID);
                //String p2 = qu == 0 ? p1 : new StringBuilder().append(p1).append('|').append(IS.getID()).append('|').append(IS.statsToString()).toString();
                String p2 = qu == 0 ? p1 : new StringBuilder().append(p1).append('|').append(item.getTemplate().id).append('|').append(GeneratorsRegistry.getObject().generateStats(item.getStats())).toString();
                
                GamePacketEnum.EXCHANGE_LOCAL_MOVE_OK.send(session, p1);
                GamePacketEnum.EXCHANGE_DISTANT_MOVE_OK.send(p.getExchange().getTarget().getSession(), p2);
                break;
        }
        
    }
    
}
