package org.pvemu.game.effect;

import java.util.Collection;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;

/**
 * Define methods for effect witch affect directly the fighers on the area
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class FighterEffect implements Effect {

    /**
     * Apply this effect to a fighter (without concider the area)
     *
     * @param data the effect data
     * @param caster the spell caster
     * @param target the targeted fighter
     */
    abstract public void applyToFighter(EffectData data, Fighter caster, Fighter target);

    @Override
    public void applyToFight(EffectData data, Fight fight, Fighter caster, short cell) {
        if (fight.getState() == Fight.STATE_FINISHED) {
            return;
        }
        
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

            applyToFighter(data, caster, target);

            if (!target.isAlive()) {
                fight.onFighterDie(target);
            }
        }

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
