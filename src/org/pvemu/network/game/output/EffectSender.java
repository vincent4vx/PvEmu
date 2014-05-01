/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.spell.GameSpell;

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
    
    public void addVita(Fight fight, int fighterID, short nb){
        updatePoint(fight, fighterID, FightActionsRegistry.UPDATE_VITA, nb);
    }
    
    private void updatePoint(Fight fight, int fighterID, short action, short nb){
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight, 
                action,
                fighterID,
                fighterID + "," + nb
        );
    }
    
    public void fighterDie(Fight fight, int fighterID){
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight, 
                FightActionsRegistry.FIGHTER_DIE, 
                fighterID,
                fighterID
        );
    }
    
    public void castSpell(Fight fight, int fighterID, GameSpell spell, short dest){
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight, 
                FightActionsRegistry.SPELL, 
                fighterID,
                spell.getModel().id + "," + 
                        dest + "," + 
                        spell.getModel().sprite + "," +
                        spell.getLevel() + "," +
                        spell.getModel().spriteInfos                
        );
    }
}
