/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.fight;

import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellAction implements GameAction<PlayerFighter>{

    @Override
    public short id() {
        return FightActionsRegistry.SPELL;
    }

    @Override
    public void start(GameActionData<PlayerFighter> data) {
        int spellID;
        short cell;
        
        try{
            spellID = Integer.parseInt(data.getArgument(0));
            cell = Short.parseShort(data.getArgument(1));
        }catch(NumberFormatException e){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
            return;
        }
        
        GameSpell spell = data.getPerformer().getSpellById(spellID);
        
        if(spell == null){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
            return;
        }
        
        GameSendersRegistry.getGameAction().gameActionStartToFight(
                data.getPerformer().getFight(), 
                data.getPerformer().getID()
        );
        
        if(!data.getPerformer().castSpell(spell, cell)){
            GameSendersRegistry.getGameAction().error(data.getPerformer().getSession());
        }
        
        GameSendersRegistry.getGameAction().gameActionFinishToFight(
                data.getPerformer().getFight(), 
                0, 
                data.getPerformer().getID()
        );
    }

    @Override
    public void end(GameActionData<PlayerFighter> data, boolean success, String[] args) {
    }
    
}
