/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.game;

import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AskDefianceAction implements GameAction<Player>{

    @Override
    public short id() {
        return GameActionsRegistry.ASK_DEFIANCE;
    }

    @Override
    public void start(GameActionData<Player> data) {
        if(data.getPerformer().getActionsManager().isBusy()){
            GameSendersRegistry.getDefiance().defianceCasterError(
                    data.getPerformer().getSession(), 
                    data.getPerformer().getID()
            );
            return;
        }
        Player other;
        try{
            int target = Integer.parseInt(data.getArgument(0));
            other = data.getPerformer().getMap().getPlayers().get(target);
            
            if(other == null || other.getActionsManager().isBusy()){
                GameSendersRegistry.getDefiance().defianceTargetBusyError(
                        data.getPerformer().getSession(), 
                        data.getPerformer().getID()
                );
                return;
            }
        }catch(NumberFormatException e){
            GameSendersRegistry.getDefiance().defianceCasterError(
                    data.getPerformer().getSession(), 
                    data.getPerformer().getID()
            );
            return;
        }
        
        GameSendersRegistry.getGameAction().unidentifiedGameActionToMap(
                data.getPerformer().getMap(),
                id(), 
                data.getPerformer().getID(), 
                other.getID()
        );
        
        other.getActionsManager().setBusy(true);
        data.getPerformer().getActionsManager().setBusy(true);
        other.getActionsManager().setDefianceTarget(data.getPerformer().getID());
        data.getPerformer().getActionsManager().setDefianceTarget(other.getID());
        Loggin.debug("asking for defiance : %s -> %s", data.getPerformer().getName(), other.getName());
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
    }
    
}
