/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.objects.spell.effect;

import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class SpellEffectFactory {

    static public SpellEffectData parseSpellEffect(String spellEffect) {

        String[] args = Utils.split(spellEffect, ";");

        try {
            short id = Short.parseShort(args[0]);
            String jet = args[6];
            int duration = Integer.parseInt(args[4]);
            int target = 0; //TODO: spell target

            String[] d = Utils.split(jet, "d");
            String[] p = Utils.split(d[1], "+");
            int min = Integer.parseInt(d[0]);
            int max = Integer.parseInt(p[0]);
            int fix = Integer.parseInt(p[1]);
            min += fix;
            max += fix;

            SpellEffect effect = SpellEffectsHandler.instance().getSpellEffect(id);

            if (effect == null) {
                Loggin.debug("Cannot find effectID %d", id);
                return null;
            }

            return new SpellEffectData(effect, min, max, duration, target);

        } catch (Exception e) {
            Loggin.error("error during parsing effect : " + spellEffect, e);
            return null;
        }
    }
    
    static public SpellEffectData parseItemEffect(String itemEffect){
        
            if(itemEffect.isEmpty()){
                return null;
            }
            String[] elem_data = Utils.split(itemEffect, "#");
            
            short effectID, min, max;
            
            try{
                effectID = Short.parseShort(elem_data[0], 16);
                min = Short.parseShort(elem_data[1], 16);
                max = Short.parseShort(elem_data[2], 16);

                max = min > max ? min : max;
            }catch(NumberFormatException ex){
                return null;
            }
            
            SpellEffect effect = SpellEffectsHandler.instance().getSpellEffect(effectID);
            
            if(effect == null)
                return null;
            
            return new SpellEffectData(effect, min, max, 0, 0);
    }
}
