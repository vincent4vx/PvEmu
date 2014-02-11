package org.pvemu.models;

import org.pvemu.game.objects.dep.SpellEffect;
import java.util.ArrayList;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.Model;

public class Spell implements Model {

    public int id;
    public String name;
    public int sprite;
    public String spriteInfos;
    public String lvl1, lvl2, lvl3, lvl4, lvl5, lvl6;
    public ArrayList<Integer> effectTarget;
    private final ArrayList<SpellStats> spellStatsList = new ArrayList<>();

    /**
     * Retourne les stats du sort en fonction du lvl
     *
     * @param lvl
     * @return
     */
    public SpellStats getSpellStats(int lvl) {
        if (spellStatsList.isEmpty()) {
            synchronized (spellStatsList) {
                spellStatsList.add(new SpellStats(lvl1, this));
                spellStatsList.add(new SpellStats(lvl2, this));
                spellStatsList.add(new SpellStats(lvl3, this));
                spellStatsList.add(new SpellStats(lvl4, this));
                spellStatsList.add(new SpellStats(lvl5, this));
                if (!"-1".equals(lvl6)) {
                    spellStatsList.add(new SpellStats(lvl6, this));
                }
            }
        }
        return spellStatsList.get(lvl);
    }

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
    }

    public class SpellStats {

        private ArrayList<SpellEffect> effects;
        private ArrayList<SpellEffect> CCeffects;
        private Spell _spell;

        private SpellStats(String data, Spell spell) {
            if (!data.equals("-1")) {
                String[] arr_data = data.split(",");
                effects = parseSpellEffects(arr_data[0]);
                CCeffects = parseSpellEffects(arr_data[1]);
            }
            _spell = spell;
        }

        private ArrayList<SpellEffect> parseSpellEffects(String data) {
            ArrayList<SpellEffect> list = new ArrayList<>();

            int i = 0;
            for (String str_effect : data.split("\\|")) {
                String[] args = str_effect.split(";");
                try {
                    int id = Integer.parseInt(args[0]);
                    String jet = args[6];
                    //int buffID = 
                    int duration = Integer.parseInt(args[4]);
                    int ET = 0;
                    if (_spell.effectTarget.get(i) != null) {
                        ET = _spell.effectTarget.get(i);
                    }
                    list.add(new SpellEffect(id, duration, jet, ET));
                } catch (ArrayIndexOutOfBoundsException e) {
                    Loggin.debug("Analyse du SpellEffect impossible : " + str_effect);
                } finally {
                    i++;
                }
            }

            return list;
        }
    }
}
