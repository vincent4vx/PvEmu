/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ActionsRegistry {
    
    private static MapActions map = new MapActions();
    private static PlayerActions player = new PlayerActions();

    /**
     * Get the value of player
     *
     * @return the value of player
     */
    public static PlayerActions getPlayer() {
        return player;
    }

    /**
     * Set the value of player
     *
     * @param player new value of player
     */
    public static void setPlayer(PlayerActions player) {
        ActionsRegistry.player = player;
    }


    /**
     * Get the value of map
     *
     * @return the value of map
     */
    public static MapActions getMap() {
        return map;
    }

    /**
     * Set the value of map
     *
     * @param map new value of map
     */
    public static void setMap(MapActions map) {
        ActionsRegistry.map = map;
    }

}
