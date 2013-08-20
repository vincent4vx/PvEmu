package server.events;

import game.objects.GameMap;
import game.objects.Player;
import jelly.Loggin;
import jelly.Utils;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class MapEvents {

    /**
     * Affiche le perso sur la map (à appeler après GameMap.addPlayer())
     * @param session 
     */
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
    
    /**
     * Retire le joueur de la map (ne pas appeler GameMap.removePlayer())
     * @param session 
     */
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
        
        p.curMap.removePlayer(p);
    }
    
    /**
     * Utilisé en cas de changement de maps
     * @param session
     * @param mapID
     * @param cellID 
     */
    public static void onArrivedOnMap(IoSession session, int mapID, int cellID){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        try{
            p.curMap = DAOFactory.map().getById(mapID).getGameMap();
            p.curCell = p.curMap.getCellById(cellID);
        }catch(NullPointerException e){
            return;
        }
        
        if(p.curCell == null){
            return;
        }
        
        p.curMap.addPlayer(p, cellID);
        
        GamePacketEnum.MAP_DATA.send(session, p.curMap.getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }
    
    /**
     * En cas d'arrivé IG
     * @param session 
     */
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
    
    /**
     * Packet GI : charge les données de la map
     * @param session 
     */
    public static void onInitialize(IoSession session){
        onAddMap(session);
        GamePacketEnum.MAP_LOADED.send(session);
    }
    
    /**
     * Arrivé sur une cellule après déplacement (gestion des triggers)
     * @param session
     * @param cellID 
     */
    public static void onArrivedOnCell(IoSession session, int cellID){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        p.curCell.removePlayer(p.getID());
        p.curCell = p.curMap.getCellById(cellID);
        p.curCell.addPlayer(p);
        
        Loggin.debug("Joueur %s arrivé sur la cellule %d avec succès !", new Object[]{p.getName(), cellID});
        
        p.curCell.performCellAction(p);
    }
}
