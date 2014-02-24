/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.pvemu.network.InputPacketsHandler;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameInputHandler extends InputPacketsHandler {
    final static private GameInputHandler instance = new GameInputHandler();
    
    private GameInputHandler() {
        registerPacket(new AttachAccountPacket());
        registerPacket(new CharacterListPacket());
        registerPacket(new GenerateNamePacket());
        registerPacket(new AddCharacterPacket());
        registerPacket(new SelectCharacterPacket());
        registerPacket(new DeleteCharacterPacket());
    }
    
    static public GameInputHandler instance(){
        return instance;
    }
    
}
