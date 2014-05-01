/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.fightertype;

import java.util.Collection;
import java.util.concurrent.Future;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.ai.AIHandler;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.spell.GameSpell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class AIFighter extends Fighter{
    private Future AITask = null;

    public AIFighter(Stats baseStats, Fight fight) {
        super(baseStats, fight);
    }

    @Override
    public void endTurn() {
        super.endTurn();
        
        if(AITask != null && !AITask.isDone() && !AITask.isCancelled())
            AITask.cancel(true);
    }
    
    @Override
    public void startTurn() {
        super.startTurn();
        try{
            Thread.sleep(10); //wait for the timer
        }catch(InterruptedException e){}
        AIHandler.instance().runAI(AIType(), fight, this);
    }
    
    abstract protected byte AIType();
    abstract public Collection<GameSpell> getSpellList();
        
    @Override
    public boolean canUseWeapon(Weapon weapon, short dest) {
        throw new UnsupportedOperationException("A monster doesn't have any weapon");
    }

    @Override
    public boolean canUseSpell(GameSpell spell, short dest) {
        int dist = MapUtils.getDistanceBetween(fight.getFightMap().getMap(), cell, dest);
        return getNumPA() >= spell.getPACost()
                && dist >= spell.getPOMin()
                && dist <= spell.getPOMax();
    }

    public void setAITask(Future AITask) {
        this.AITask = AITask;
    }
    
}
