/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.effect;

import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class EffectFactory {

    static public EffectData parseSpellEffect(String spellEffect, String area, byte target) {

        String[] args = Utils.split(spellEffect, ";");

        try {
            short id = Short.parseShort(args[0]);
            int duration = Integer.parseInt(args[4]);
            
            int min = Integer.parseInt(args[1].trim());
            int max = Integer.parseInt(args[2].trim());
            
            if(max < min)
                max = min;

            Effect effect = EffectsHandler.instance().getEffect(id);

            if (effect == null) {
                Loggin.debug("Cannot find effectID %d", id);
                return null;
            }

            return new EffectData(effect, min, max, duration, target, area);

        } catch (Exception e) {
            Loggin.error("error during parsing effect : " + spellEffect, e);
            return null;
        }
    }
    
    static public EffectData parseItemEffect(String itemEffect, String area){
        
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
            
            Effect effect = EffectsHandler.instance().getEffect(effectID);
            
            if(effect == null)
                return null;
            
            return new EffectData(effect, min, max, 0, (byte)2, area);
    }
}
