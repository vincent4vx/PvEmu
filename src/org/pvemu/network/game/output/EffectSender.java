/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EffectSender {
    public void removePM(Fight fight, int fighterID, short nb){
        updatePoint(fight, fighterID, FightActionsRegistry.USE_PM, (short)-nb);
    }
    
    public void removePAOnAction(Fight fight, int fighterID, short nb){
        updatePoint(fight, fighterID, FightActionsRegistry.USE_PA_ON_ACTION, (short)-nb);
    }
    
    public void removePMOnWalk(Fight fight, int fighterID, short nb){
        updatePoint(fight, fighterID, FightActionsRegistry.USE_PM_ON_MOVE, (short)-nb);
    }
    
    public void removeVita(Fight fight, int fighterID, short nb){
        updatePoint(fight, fighterID, FightActionsRegistry.UPDATE_VITA, (short)-nb);
    }
    
    private void updatePoint(Fight fight, int fighterID, short action, short nb){
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight, 
                action,
                fighterID,
                fighterID + "," + nb
        );
    }
}
