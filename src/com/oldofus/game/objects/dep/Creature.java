package com.oldofus.game.objects.dep;

import com.oldofus.game.objects.dep.Stats.Element;
import java.util.ArrayList;
import com.oldofus.models.Spell.SpellStats;

public abstract class Creature {

    protected Stats baseStats = new Stats();
    protected short level;
    protected short gfxID;
    protected ArrayList<SpellStats> _spells;
    protected String[] colors = new String[3];
    protected String name;

    /**
     * Retourne les stats de bases
     *
     * @return
     */
    public Stats getBaseStats() {
        return baseStats;
    }

    /**
     * Retourne les stats total (base + équipement)
     *
     * @return
     */
    public Stats getTotalStats() {
        return baseStats;
    }

    /**
     * Retourne le nom de la créature
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne l'initiative
     *
     * @return
     */
    public Short getInitiative() {
        short fact = 4;
        short pvmax = 100;
        short pv = 100;
        /*if (_classe == Constants.CLASS_SACRIEUR) {
         fact = 8;
         }*/
        double coef = pvmax / fact;

        coef += getTotalStats().get(Element.INIT);
        coef += getTotalStats().get(Element.AGILITE);
        coef += getTotalStats().get(Element.CHANCE);
        coef += getTotalStats().get(Element.INTEL);
        coef += getTotalStats().get(Element.FORCE);

        short init = 1;
        if (pvmax != 0) {
            init = (short) (coef * ((double) pv / (double) pvmax));
        }
        if (init < 0) {
            init = 0;
        }
        return init;
    }

    public Short getLevel() {
        return level;
    }

    public Short getGfxID() {
        return gfxID;
    }

    public String[] getColors() {
        return colors;
    }
}
