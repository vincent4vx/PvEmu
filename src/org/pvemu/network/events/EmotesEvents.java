package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.game.GamePacketEnum;

public class EmotesEvents {
    public static void onDirection(IoSession session, String packet){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        byte dir = 0;
        
        try{
            dir = Byte.parseByte(packet);
            
            if(dir < 0){
                dir = 0;
            }else if(dir > 7){
                dir = 7;
            }
        }catch(NumberFormatException e){
            return;
        }
        
        p.orientation = dir;
        
        GamePacketEnum.EMOTE_DIRECTION.sendToMap(p.getMap(), new StringBuilder().append(p.getID()).append('|').append(dir).toString());
    }
}
