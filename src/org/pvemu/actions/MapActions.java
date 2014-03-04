/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MapActions {
    public void removePlayer(GameMap map, Player player){
        player.getMap().removePlayer(player);
        GameSendersRegistry.getMap().removeGMable(map, player);
    }
    
    public void addPlayer(GameMap map, Player player){
        map.addPlayer(player, player.getCell().getID());
        
        GameSendersRegistry.getMap().mapData(map, player.getSession());
        GameSendersRegistry.getMap().fightCount(map, player.getSession());
    }
}
