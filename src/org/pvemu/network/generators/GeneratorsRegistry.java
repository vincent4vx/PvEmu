/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GeneratorsRegistry {
    private static PlayerGenerator player = null;
    
    public static PlayerGenerator getPlayer(){
        if(player == null)
            player = new PlayerGenerator();
        
        return player;
    }
    
    public static void setPlayer(PlayerGenerator newPlayer){
        player = newPlayer;
    }
    
    
}
