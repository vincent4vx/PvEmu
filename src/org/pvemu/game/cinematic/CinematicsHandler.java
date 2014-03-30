/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.cinematic;

import java.util.HashMap;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class CinematicsHandler {
    final static private CinematicsHandler instance = new CinematicsHandler();
    final private HashMap<Byte, Cinematic> cinematics = new HashMap<>();
    
    final static public byte GO_TO_MOON       = 1;
    final static public byte GO_TO_WABIT      = 2;
    final static public byte BRIGANDIN_LEFT   = 3;
    final static public byte BRIGANDIN_RIGHT  = 4;
    final static public byte BRIGANDIN_TRAVEL = 5;
    final static public byte GO_TO_MINE       = 6;
    final static public byte GO_TO_ASTRUB     = 7;
    final static public byte START            = 8;
    final static public byte START_NO_SOUND   = 9;
    
    private CinematicsHandler(){
        registerCinematic(new GoToAstrubCinematic());
    }
    
    public void registerCinematic(Cinematic cinematic){
        cinematics.put(cinematic.id(), cinematic);
    }
    
    public void endOfCinematic(byte id, Player player){
        Cinematic cinematic = cinematics.get(id);
        
        if(cinematic == null){
            Loggin.debug("Cinématique %d inconnue", id);
            return;
        }
        
        cinematic.end(player);
    }
    
    static public CinematicsHandler instance(){
        return instance;
    }
}
