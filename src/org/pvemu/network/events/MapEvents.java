package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;
import org.pvemu.network.generators.PlayerGenerator;

public class MapEvents {

    /**
     * Affiche le perso sur la map (à appeler après GameMap.addPlayer())
     *
     * @param session sender
     */
    public static void onAddMap(IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        GamePacketEnum.MAP_ADD_PLAYER.sendToMap(p.getMap(), GeneratorsRegistry.getPlayer().generateGM(p));
        
        for(GMable Ga : p.getMap().getGMables()){
            if(Ga == p){
                continue; //pas besoin d'envoyer 2 fois le même packet
            }
            GamePacketEnum.MAP_ADD_PLAYER.send(session, Ga.getGMData());
        }
    }

    /**
     * Retire le joueur de la map (ne pas appeler GameMap.removePlayer())
     *
     * @param session target
     */
    public static void onRemoveMap(IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        for (Player P : p.getMap().getPlayers().values()) {
            if (P.getSession() != null) {
                GamePacketEnum.MAP_REMOVE.send(P.getSession(), String.valueOf(p.getID()));
            }
        }

        p.getMap().removePlayer(p);
    }

    /**
     * Utilisé en cas de changement de maps
     *
     * @param session sender
     * @param mapID map d'arrivée
     * @param cellID cellule d'arrivée
     */
    public static void onArrivedOnMap(IoSession session, short mapID, short cellID) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        try {
            p.setMap(DAOFactory.map().getById(mapID).getGameMap());
            p.setCell(p.getMap().getCellById(cellID));
        } catch (NullPointerException e) {
            return;
        }

        p.getMap().addPlayer(p, cellID);

        GamePacketEnum.MAP_DATA.send(session, p.getMap().getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }

    /**
     * En cas d'arrivé IG
     *
     * @param session target
     */
    public static void onArrivedInGame(IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            session.close(true);
            return;
        }

        p.getMap().addPlayer(p, p.getCell().getID());

        GamePacketEnum.MAP_DATA.send(session, p.getMap().getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }

    /**
     * Packet GI : charge les données de la map
     *
     * @param session target
     */
    public static void onInitialize(IoSession session) {
        onAddMap(session);
        GamePacketEnum.MAP_LOADED.send(session);
    }

    /**
     * Arrivé sur une cellule après déplacement (gestion des triggers)
     *
     * @param session target / sender
     * @param cellID cellule d'arrivée
     */
    public static void onArrivedOnCell(IoSession session, short cellID) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        p.getCell().removePlayer(p.getID());
        p.setCell(p.getMap().getCellById(cellID));
        p.getCell().addPlayer(p);

        Loggin.debug("Joueur %s arrivé sur la cellule %d avec succès !", p.getName(), cellID);

        p.getCell().performCellAction(p);
    }
}
