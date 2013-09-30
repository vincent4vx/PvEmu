package com.oldofus.game.objects.dep;

import com.oldofus.game.objects.dep.Stats.Element;
import java.util.ArrayList;
import com.oldofus.models.Spell.SpellStats;

public abstract class Creature {

    protected Stats baseStats = new Stats();
    protected int level;
    protected int gfxID;
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
    public int getInitiative() {
        int fact = 4;
        int pvmax = 100;
        int pv = 100;
        /*if (_classe == Constants.CLASS_SACRIEUR) {
         fact = 8;
         }*/
        double coef = pvmax / fact;

        coef += getTotalStats().get(Element.INIT);
        coef += getTotalStats().get(Element.AGILITE);
        coef += getTotalStats().get(Element.CHANCE);
        coef += getTotalStats().get(Element.INTEL);
        coef += getTotalStats().get(Element.FORCE);

        int init = 1;
        if (pvmax != 0) {
            init = (int) (coef * ((double) pv / (double) pvmax));
        }
        if (init < 0) {
            init = 0;
        }
        return init;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getGfxID() {
        return gfxID;
    }

    public String[] getColors() {
        return colors;
    }
}
