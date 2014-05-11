/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MapActions {
    /**
     * Remove a player from map
     * @param map the player's map
     * @param player the player to remove
     * @see GameMap#removePlayer(org.pvemu.game.objects.player.Player) 
     * @see org.pvemu.network.game.output.MapSender#removeGMable(org.pvemu.game.objects.map.GameMap, org.pvemu.game.objects.map.GMable) 
     */
    public void removePlayer(GameMap map, Player player){
        player.getMap().removePlayer(player);
        GameSendersRegistry.getMap().removeGMable(map, player);
    }
    
    /**
     * A a new player into map
     * @param map the map
     * @param player the player
     * @see GameMap#addPlayer(org.pvemu.game.objects.player.Player, short) 
     * @see org.pvemu.network.game.output.MapSender#mapData(org.pvemu.game.objects.map.GameMap, org.apache.mina.core.session.IoSession) 
     * @see org.pvemu.network.game.output.MapSender#fightCount(org.pvemu.game.objects.map.GameMap, org.apache.mina.core.session.IoSession) 
     */
    public void addPlayer(GameMap map, Player player){
        map.addPlayer(player, player.getCell().getID());
        
        GameSendersRegistry.getMap().mapData(map, player.getSession());
        GameSendersRegistry.getMap().fightCount(map, player.getSession());
    }
}
