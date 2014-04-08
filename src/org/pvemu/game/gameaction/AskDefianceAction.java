/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AskDefianceAction implements GameAction{

    @Override
    public short id() {
        return GameActionsRegistry.ASK_DEFIANCE;
    }

    @Override
    public void start(GameActionData data) {
        if(data.getPlayer().getActionsManager().isBusy()){
            GameSendersRegistry.getDefiance().defianceCasterError(
                    data.getPlayer().getSession(), 
                    data.getPlayer().getID()
            );
            return;
        }
        Player other;
        try{
            int target = Integer.parseInt(data.getArgument(0));
            other = data.getPlayer().getMap().getPlayers().get(target);
            
            if(other == null || other.getActionsManager().isBusy()){
                GameSendersRegistry.getDefiance().defianceTargetBusyError(
                        data.getPlayer().getSession(), 
                        data.getPlayer().getID()
                );
                return;
            }
        }catch(NumberFormatException e){
            GameSendersRegistry.getDefiance().defianceCasterError(
                    data.getPlayer().getSession(), 
                    data.getPlayer().getID()
            );
            return;
        }
        
        GameSendersRegistry.getGameAction().unidentifiedGameActionToMap(
                data.getPlayer().getMap(),
                id(), 
                data.getPlayer().getID(), 
                other.getID()
        );
        
        other.getActionsManager().setBusy(true);
        data.getPlayer().getActionsManager().setBusy(true);
        other.getActionsManager().setDefianceTarget(data.getPlayer().getID());
        data.getPlayer().getActionsManager().setDefianceTarget(other.getID());
        Loggin.debug("asking for defiance : %s -> %s", data.getPlayer().getName(), other.getName());
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
    }
    
}
