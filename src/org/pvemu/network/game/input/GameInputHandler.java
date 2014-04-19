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
        //account packets
        registerPacket(new AttachAccountPacket());
        registerPacket(new CharacterListPacket());
        registerPacket(new GenerateNamePacket());
        registerPacket(new AddCharacterPacket());
        registerPacket(new SelectCharacterPacket());
        registerPacket(new DeleteCharacterPacket());
        
        //game packets
        registerPacket(new CreateGamePacket());
        registerPacket(new InitMapPacket());
        registerPacket(new GameActionPacket());
        registerPacket(new GameActionOkPacket());
        
        //basic packets
        registerPacket(new DatePacket());
        registerPacket(new MessagePacket());
        registerPacket(new AdminCommandPacket());
        registerPacket(new SmileyPacket());
        registerPacket(new ChatChanelPacket());
        
        //object packets
        registerPacket(new MoveObjectPacket());
        
        //ping
        registerPacket(new PingPacket());
        
        //emote packets
        registerPacket(new ChangeDirectionPacket());
        
        //dialog packets
        registerPacket(new CreateDialogPacket());
        registerPacket(new LeaveDialogPacket());
        registerPacket(new ResponseDialogPacket());
        
        //exchange packets
        registerPacket(new RequestExchangePacket());
        registerPacket(new AcceptExchangePacket());
        registerPacket(new LeaveExchangePacket());
        registerPacket(new ExchangeMovePacket());
        registerPacket(new ExchangeOkPacket());
        
        //fight packets
        registerPacket(new FightChangePlacePacket());
    }
    
    static public GameInputHandler instance(){
        return instance;
    }
    
}
