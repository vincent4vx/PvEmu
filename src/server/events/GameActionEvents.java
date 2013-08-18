package server.events;

import game.GameAction;
import game.objects.Player;
import java.util.concurrent.atomic.AtomicReference;
import jelly.utils.Pathfinding;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class GameActionEvents {
    public static void onGameAction(IoSession session, String packet){
        Player p = (Player)session.getAttribute("player");
        if(p == null){
            return;
        }
        int actionID = 0;
        GameAction GA;
        try{
            actionID = Integer.parseInt(packet.substring(0, 3));
            String args = packet.substring(3);
            
            GA = GameAction.create(p, actionID, args);
        }catch(Exception e){
            return;
        }
        
        switch(actionID){
            case 1: //déplacement
                onMoveAction(session, GA);
                break;
        }
    }
    
    private static void onMoveAction(IoSession session, GameAction GA){  
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        AtomicReference<String> rPath = new AtomicReference<>(GA.args);
        int cellDest = Pathfinding.isValidPath(p.curMap, p.curCell.getID(), rPath);
        
        GamePacketEnum.GAME_ACTION.send(session, "001;" + rPath.get());
        for(Player P : p.curMap.getPlayers().values()){
            if(P.getSession() != null){
                GamePacketEnum.GAME_ACTION.send(P.getSession(), "0;1;" + p.getID() + ";" + "a" + Pathfinding.cellID_To_Code(p.curCell.getID()) + rPath.get());
            }
        }
        
        p.curCell = p.curMap.getCellById(cellDest);
    }
}
