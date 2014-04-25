/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.pvemu.network.game.input.fight.FightChangePlacePacket;
import org.pvemu.network.game.input.exchange.LeaveExchangePacket;
import org.pvemu.network.game.input.exchange.RequestExchangePacket;
import org.pvemu.network.game.input.exchange.AcceptExchangePacket;
import org.pvemu.network.game.input.exchange.ExchangeMovePacket;
import org.pvemu.network.game.input.exchange.ExchangeOkPacket;
import org.pvemu.network.game.input.dialog.LeaveDialogPacket;
import org.pvemu.network.game.input.dialog.CreateDialogPacket;
import org.pvemu.network.game.input.dialog.ResponseDialogPacket;
import org.pvemu.network.game.input.object.MoveObjectPacket;
import org.pvemu.network.game.input.basic.SmileyPacket;
import org.pvemu.network.game.input.basic.MessagePacket;
import org.pvemu.network.game.input.basic.ChatChanelPacket;
import org.pvemu.network.game.input.basic.DatePacket;
import org.pvemu.network.game.input.basic.AdminCommandPacket;
import org.pvemu.network.game.input.game.GameActionOkPacket;
import org.pvemu.network.game.input.game.CreateGamePacket;
import org.pvemu.network.game.input.game.InitMapPacket;
import org.pvemu.network.game.input.game.GameActionPacket;
import org.pvemu.network.game.input.account.SelectCharacterPacket;
import org.pvemu.network.game.input.account.AddCharacterPacket;
import org.pvemu.network.game.input.account.GenerateNamePacket;
import org.pvemu.network.game.input.account.CharacterListPacket;
import org.pvemu.network.game.input.account.DeleteCharacterPacket;
import org.pvemu.network.game.input.account.AttachAccountPacket;
import org.pvemu.network.InputPacketsHandler;
import org.pvemu.network.game.input.fight.FightEndTurnPacket;
import org.pvemu.network.game.input.fight.FightReadyPacket;
import org.pvemu.network.game.input.spell.BoostSpellPacket;
import org.pvemu.network.game.input.spell.MoveSpellPacket;

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
        registerPacket(new QPingPacket());
        
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
        registerPacket(new FightReadyPacket());
        registerPacket(new FightEndTurnPacket());
        
        //spells packets
        registerPacket(new MoveSpellPacket());
        registerPacket(new BoostSpellPacket());
    }
    
    static public GameInputHandler instance(){
        return instance;
    }
    
}
