/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.World;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapFactory;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerActions {
    /**
     * Téléporte le personnage
     * @param player personne à téléporter
     * @param mapID map de destination
     * @param cellID cellule de destination
     */
    public void teleport(Player player, short mapID, short cellID){
        if(!MapUtils.isValidDest(mapID, cellID)){
            return;
        }
        
        //GameMap map = DAOFactory.map().getById(mapID).getGameMap();
        GameMap map = MapFactory.getById(mapID);
        MapCell cell = map.getCellById(cellID);
        ActionsRegistry.getMap().removePlayer(player.getMap(), player);
        
        player.setMap(map);
        player.setCell(cell);
        
        ActionsRegistry.getMap().addPlayer(map, player);
    }
    
    public void arrivedOnCell(Player player, MapCell cell){
        player.getCell().removePlayer(player.getID());
        player.setCell(cell);
        player.getCell().addPlayer(player);

        Loggin.debug("Joueur %s arrivé sur la cellule %d avec succès !", player.getName(), player.getCell().getID());
        
        player.getCell().performCellAction(player);
    }
    
    public void logout(Player player){
        ActionsRegistry.getMap().removePlayer(player.getMap(), player);
        IoSession session = player.getSession();
        player.setSession(null);
        player.stopExchange();
        player.getActionsManager().clearAll();
        SessionAttributes.PLAYER.removeValue(session);
        World.instance().removeOnline(player);
        player.save();
        Loggin.debug("%s : logout", player.getName());
    }
}
