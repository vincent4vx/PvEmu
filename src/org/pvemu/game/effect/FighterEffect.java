package org.pvemu.game.effect;

import java.util.Collection;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.buff.Buff;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.common.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 * Define methods for effect witch affect directly the fighers on the area
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class FighterEffect implements Effect {

    /**
     * Apply this effect to a fighter (without concider the area)
     * @param min
     * @param max
     * @param caster the spell caster
     * @param target the targeted fighter
     */
    abstract public void applyToFighter(int min, int max, Fighter caster, Fighter target);
    
    final public void applyToFighter(EffectData data, Fighter caster, Fighter target){
        applyToFighter(data.getMin(), data.getMax(), caster, target);
    }
    
    abstract public void startBuff(EffectData data, Fighter caster, Fighter target);
    
    abstract public void applyBuff(Buff buff);

    @Override
    public void applyToFight(EffectData data, Fight fight, Fighter caster, short cell) {
        Collection<Short> cells = MapUtils.getCellsFromArea(
                fight.getFightMap().getMap(),
                cell,
                caster.getCellId(),
                data.getArea()
        );

        Loggin.debug("Apply %s on cell %d", data, cell);

        for (Fighter target : fight.getFightMap().getFightersByCells(cells)) { 
            if(!isGoodTarget(data.getTarget(), caster, target)){
                continue;
            }

            if (!target.isAlive()) {
                continue;
            }

            if(data.getDuration() < 1){
                applyToFighter(data, caster, target);
            }else{
                startBuff(data, caster, target);
            }
        }
        
        fight.checkZombies();
    }
    
    protected boolean isGoodTarget(byte ET, Fighter caster, Fighter target){
        if(target == null)
            return false;
        
        //don't affect friends
        if(((ET & 1) == 1) && (target.getTeam() == caster.getTeam()))
            return false;
        //don't affect caster
        if((((ET>>1) & 1) == 1) && (target == caster))
            return false;
        //affect only friends
        if((((ET>>2) & 1) == 1) && (target.getTeam() != caster.getTeam()))
            return false;
        //TODO: affect only invocation
        /*if((((ET>>3) & 1) == 1) && (!target.isInvocation()))
            return false;*/
        //TODO: don't affect invocations
        /*if((((ET>>4) & 1) == 1) && (target.isInvocation()))
            return false;*/
        //affect only caster
        if((((ET>>5) & 1) == 1) && (target != caster))
            return false;
        
        return true;
    }

    abstract protected int getEfficiencyForOneFighter(EffectData data, Fight fight, Fighter caster, Fighter target);

    @Override
    public int getEfficiency(EffectData data, Fight fight, Fighter caster, short cell){
        int efficiency = 0;
        
        Collection<Short> cells = MapUtils.getCellsFromArea(
                fight.getFightMap().getMap(),
                cell,
                caster.getCellId(),
                data.getArea()
        );
        
        for (Fighter target : fight.getFightMap().getFightersByCells(cells)) {            
            if(!isGoodTarget(data.getTarget(), caster, target)){
                continue;
            }

            if (!target.isAlive()) {
                continue;
            }

            efficiency += getEfficiencyForOneFighter(data, fight, caster, target);
        }
        
        return efficiency;
    }
    
}
