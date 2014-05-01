package org.pvemu.game.effect;

import java.util.Collection;
import org.pvemu.game.fight.Fight;
import static org.pvemu.game.fight.Fight.STATE_FINISHED;
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
        Collection<Short> cells = MapUtils.getCellsFromArea(
                fight.getFightMap().getMap(),
                cell,
                caster.getCellId(),
                data.getArea()
        );

        Loggin.debug("Apply %s on cell %d", data, cell);

        for (Fighter target : fight.getFightMap().getFightersByCells(cells)) {
            if (fight.getState() == Fight.STATE_FINISHED) {
                return;
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

}
