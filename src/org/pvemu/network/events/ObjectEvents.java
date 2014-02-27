package org.pvemu.network.events;

import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.Player;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

@Deprecated
public class ObjectEvents {
    
    public static void onMove(IoSession session, int objID, byte objPOS){
        if(session == null){
            return;
        }
        GamePacketEnum.OBJECT_MOVE.send(session, new StringBuilder().append(objID).append('|').append(objPOS));
    }
    
    public static void onQuantityChange(IoSession session, int objID, int objQU){
        if(session == null){
            return;
        }
        GamePacketEnum.OBJECT_QUANTITY.send(session, new StringBuilder().append(objID).append('|').append(objQU).toString());
    }
    
    /**
     * Appelé à chaque modification des pods (ajout d'item dans inventaire / modification stats)
     * @param session
     * @param P 
     */
    public static void onWeightChange(IoSession session, Player P){
        GamePacketEnum.OBJECTS_WEIGHT.send(session, new StringBuilder().append(P.getUsedPods()).append('|').append(P.getTotalPods()));
    }
    
    public static void onAdd(IoSession session, GameItem obj){
        if(session == null || obj == null){
            return;
        }
        
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        GamePacketEnum.OBJECT_ADD_OK.send(session, obj.toString());
        onWeightChange(session, p);
    }
    
    public static void onRemove(IoSession session, int id){
        if(session == null){
            return;
        }
        
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        GamePacketEnum.OBJECT_REMOVE.send(session, id);
    }
}
