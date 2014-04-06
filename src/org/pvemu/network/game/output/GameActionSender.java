/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionSender {
    public void error(IoSession session){
        GamePacketEnum.GAME_ACTION_ERROR.send(session);
    }
    
    public void gameAction(IoSession session, int id, short gameActionID, Object... args){
        GamePacketEnum.GAME_ACTION.send(
                session,
                GeneratorsRegistry.getGameAction().generateGameAction(id, gameActionID, args)
        );
    }
    
    public void gameAction(IoSession session, int id, GameActionData data){
        gameAction(session, id, data.getGameActionID(), data.getArguments());
    }
    
    public void gameActionToMap(GameMap map, int id, short gameActionID, Object... args){
        String packet = GeneratorsRegistry.getGameAction().generateGameAction(id, gameActionID, args);
        
        for(Player player : map.getPlayers().values()){
            GamePacketEnum.GAME_ACTION.send(player.getSession(), packet);
        }
    }
    
    public void gameActionToMap(GameMap map, int id, GameActionData data){
        Object[] args = new Object[data.getArguments().length + 1];
        System.arraycopy(data.getArguments(), 0, args, 1, data.getArguments().length);
        args[0] = data.getPlayer().getID();
        gameActionToMap(map, id, data.getGameActionID(), args);
    }
}
