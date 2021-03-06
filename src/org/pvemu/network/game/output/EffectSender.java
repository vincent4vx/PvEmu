/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.buff.Buff;
import org.pvemu.game.fight.fightertype.InvocationFighter;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EffectSender {
    public void removePM(Fight fight, int fighterID, int nb){
        updatePoint(fight, fighterID, FightActionsRegistry.USE_PM, -nb);
    }
    
    public void removePAOnAction(Fight fight, int fighterID, int nb){
        updatePoint(fight, fighterID, FightActionsRegistry.USE_PA_ON_ACTION, -nb);
    }
    
    public void removePA(Fight fight, int fighterID, int nb){
        updatePoint(fight, fighterID, FightActionsRegistry.UPDATE_PA, -nb);
    }
    
    public void removePMOnWalk(Fight fight, int fighterID, int nb){
        updatePoint(fight, fighterID, FightActionsRegistry.USE_PM_ON_MOVE, -nb);
    }
    
    public void removeVita(Fight fight, int fighterID, int nb){
        updatePoint(fight, fighterID, FightActionsRegistry.UPDATE_VITA, -nb);
    }
    
    public void addVita(Fight fight, int fighterID, int nb){
        updatePoint(fight, fighterID, FightActionsRegistry.UPDATE_VITA, nb);
    }
    
    private void updatePoint(Fight fight, int fighterID, short action, int nb){
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
    
    public void buffEffect(Fight fight, Buff buff){
        GamePacketEnum.FIGHT_EFFECT.sendToFight(fight, GeneratorsRegistry.getEffect().generateBuffEffect(buff));
    }
    
    public void invocate(Fight fight, Fighter invocator, InvocationFighter invoc){
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight, 
                FightActionsRegistry.INVOCATE,
                invocator.getID(),
                GeneratorsRegistry.getFight().generateMonsterGMPacket(invoc)
        );
    }
}
