package server.events;

import game.objects.GameMap;
import game.objects.Player;
import jelly.Loggin;
import jelly.Utils;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class MapEvents {

    public static void onAddMap(IoSession session) {
        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            return;
        }
        
        for (Player P : p.curMap.getPlayers().values()) {
            if (P.getSession() != null) {
                GamePacketEnum.MAP_ADD_PLAYER.send(P.getSession(), p.getGMData());
            }
            if(P != p){
                GamePacketEnum.MAP_ADD_PLAYER.send(session, P.getGMData());
            }
        }
    }
    
    public static void onRemoveMap(IoSession session){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        for(Player P : p.curMap.getPlayers().values()){
            if(P.getSession() != null){
                GamePacketEnum.MAP_REMOVE.send(P.getSession(), String.valueOf(p.getID()));
            }
        }
        
        p.curMap.removePlayer(p, p.curCell.getID());
    }
    
    public static void onArrivedOnMap(IoSession session, int mapID, int cellID){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            session.close(true);
            return;
        }
        
        try{
            p.curMap = DAOFactory.map().getById(mapID).getGameMap();
            p.curCell = p.curMap.getCellById(cellID);
        }catch(NullPointerException e){
            session.close(true);
            return;
        }
        
        if(p.curCell == null){
            session.close(true);
            return;
        }
        
        p.curMap.addPlayer(p, cellID);
        
        GamePacketEnum.MAP_DATA.send(session, p.curMap.getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }
    
    public static void onArrivedInGame(IoSession session){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            session.close(true);
            return;
        }
        
        p.curMap.addPlayer(p, p.curCell.getID());
           
        GamePacketEnum.MAP_DATA.send(session, p.curMap.getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }
    
    public static void onInitialize(IoSession session){
        onAddMap(session);
        GamePacketEnum.MAP_LOADED.send(session);
    }
    
    public static void onArrivedOnCell(IoSession session, int cellID){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        p.curCell.removePlayer(p.getID());
        p.curCell = p.curMap.getCellById(cellID);
        p.curCell.addPlayer(p);
        
        Loggin.debug("Joueur %s arrivé sur la cellule %d avec succès !", new Object[]{p.getName(), cellID});
    }
}
