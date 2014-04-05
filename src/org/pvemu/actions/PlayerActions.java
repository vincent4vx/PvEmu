/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapFactory;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;

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
}
