package server.events;

import game.objects.GameItem;
import game.objects.Player;
import jelly.Loggin;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class ObjectEvents {
    public static void onObjectMove(IoSession session, String packet){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        String[] data = packet.split("\\|");
        int id;
        byte target;
        try{
            id = Integer.parseInt(data[0]);
            target = Byte.parseByte(data[1]);
        }catch(Exception e){
            return;
        }
        
        boolean result = p.moveItem(id, 1, target);
        
        if(!result){
            Loggin.debug("Erreur lors du déplacement de l'objet %d", id);
        }else{
            Loggin.debug("Déplacement de l'objet %d OK !", id);
        }
    }
    
    public static void onMove(IoSession session, GameItem GI){
        if(session == null || GI == null){
            return;
        }
        GamePacketEnum.OBJECT_MOVE.send(session, new StringBuilder().append(GI.getID()).append('|').append(GI.getInventory().position));
    }
    
    public static void onQuantityChange(IoSession session, GameItem obj){
        if(session == null){
            return;
        }
        GamePacketEnum.OBJECT_QUANTITY.send(session, new StringBuilder().append(obj.getID()).append('|').append(obj.getInventory().qu).toString());
    }
    
    public static void onAdd(IoSession session, GameItem obj){
        if(session == null || obj == null){
            return;
        }
        GamePacketEnum.OBJECT_ADD_OK.send(session, obj.toString());
    }
    
    public static void onRemove(IoSession session, int id){
        if(session == null){
            return;
        }
        GamePacketEnum.OBJECT_REMOVE.send(session, id);
    }
}
