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
    private static ObjectActions object = new ObjectActions();
    private static AccountAction account = new AccountAction();

    /**
     * Get the value of account
     *
     * @return the value of account
     */
    public static AccountAction getAccount() {
        return account;
    }

    /**
     * Set the value of account
     *
     * @param account new value of account
     */
    public static void setAccount(AccountAction account) {
        ActionsRegistry.account = account;
    }


    /**
     * Get the value of object
     *
     * @return the value of object
     */
    public static ObjectActions getObject() {
        return object;
    }

    /**
     * Set the value of object
     *
     * @param object new value of object
     */
    public static void setObject(ObjectActions object) {
        ActionsRegistry.object = object;
    }

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
