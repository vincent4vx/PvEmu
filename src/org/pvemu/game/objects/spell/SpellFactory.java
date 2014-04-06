/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.pvemu.game.objects.spell.effect.SpellEffect;
import org.pvemu.game.objects.spell.effect.SpellEffectsHandler;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Spell;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class SpellFactory {
    final static private Map<Integer, SpellLevels> spells = new HashMap<>();
    
    static public SpellLevels getSpellLevelsById(int id){
        if(!spells.containsKey(id)){
            Spell model = DAOFactory.spell().find(id);
            List<GameSpell> levels = new ArrayList<>();
            
            if(model == null){
                Loggin.debug("Cannot found spell %d", id);
                return null;
            }
            
            String[] lvlsDatas = new String[]{
                model.lvl1,
                model.lvl2,
                model.lvl3,
                model.lvl4,
                model.lvl5,
                model.lvl6
            };
            
            for(int i = 0; i < lvlsDatas.length; ++i){
                GameSpell spell = createGameSpell(model, (byte)(i + 1), lvlsDatas[i]);
                
                if(spell == null)
                    break;
                
                levels.add(spell);
            }
            
            spells.put(id, new SpellLevels(model, levels));
        }
        
        return spells.get(id);
    }
    
    static private GameSpell createGameSpell(Spell model, byte level, String data){
        if(data.isEmpty() || data.equals("-1"))
            return null;
        
        String[] tmp = Utils.split(data, ",");
        
        if(tmp.length < 2)
            return null;
        
        Set<SpellEffectData> effects = parseSpellEffect(tmp[0]);
        Set<SpellEffectData> criticals = parseSpellEffect(tmp[1]);
        
        return new GameSpell(model, level, effects, criticals);
    }
    
    static private Set<SpellEffectData> parseSpellEffect(String strEffect){
        Set<SpellEffectData> effects = new HashSet<>();
        
        if(strEffect.isEmpty() || strEffect.equals("-1"))
            return effects;
        
        String[] effectsArray = Utils.split(strEffect, "|");
        
        for(int i = 0; i < effectsArray.length; ++i){
            if(effectsArray[i].isEmpty() || effectsArray[i].equals("-1"))
                continue;
            
            String[] args = Utils.split(effectsArray[i], ";");
            
            try{
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
                
                if(effect == null){
                    Loggin.debug("Cannot find effectID %d", id);
                    continue;
                }
                
                effects.add(new SpellEffectData(effect, min, max, duration, target));
            }catch(Exception e){
                Loggin.error("Cannot parse spell effect '" + effectsArray[i] + "'", e);
            }
        }
        
        return effects;
    }
}
