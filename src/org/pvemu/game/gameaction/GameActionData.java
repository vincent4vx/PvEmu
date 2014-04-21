/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.objects.player.Player;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionData {
    
    final private Player player;
    final private PlayerFighter fighter;
    final private short gameActionID;
    final private String[] arguments;

    public GameActionData(Player player, PlayerFighter fighter, short gameActionID, String[] arguments) {
        this.player = player;
        this.fighter = fighter;
        this.gameActionID = gameActionID;
        this.arguments = arguments;
    }
    

    /**
     * Get the value of arguments
     *
     * @return the value of arguments
     */
    public String[] getArguments() {
        return arguments;
    }
    
    public String getArgument(int index){
        return arguments.length > index ? arguments[index] : null;
    }
    
    public void setArgument(int index, String value){
        if(arguments.length > index)
            arguments[index] = value;
    }


    /**
     * Get the value of gameActionID
     *
     * @return the value of gameActionID
     */
    public short getGameActionID() {
        return gameActionID;
    }


    /**
     * Get the value of player
     *
     * @return the value of player
     */
    public Player getPlayer() {
        return player;
    }

    public PlayerFighter getFighter() {
        return fighter;
    }

    public boolean isFight(){
        return fighter != null;
    }
}
