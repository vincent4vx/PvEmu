package org.pvemu.game.objects.spell;

import org.pvemu.game.effect.EffectData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import org.pvemu.game.effect.EffectFactory;
import org.pvemu.common.Loggin;
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.common.utils.Utils;
import org.pvemu.models.Spell;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class SpellFactory {
    final static private Map<Integer, SpellLevels> spells = new ConcurrentHashMap<>();
    
    static public SpellLevels getSpellLevelsById(int id){
        if(!spells.containsKey(id)){
            Spell model = DAOFactory.spell().find(id);
            
            if(model == null){
                Loggin.debug("Cannot found spell %d", id);
                return null;
            }
            
            spells.put(id, getSpellByModel(model));
        }
        
        return spells.get(id);
    }
    
    static private SpellLevels getSpellByModel(Spell model){
            List<GameSpell> levels = new ArrayList<>();
            
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
            return new SpellLevels(model, levels);
    }
    
    static private GameSpell createGameSpell(Spell model, byte level, String data){
        if(data.isEmpty() || data.equals("-1"))
            return null;
        
        String[] tmp = Utils.split(data, ",");
        
        if(tmp.length < 16)
            return null;
        
        String area = tmp[15].trim();
        
        short PACost = 6, minLevel;
        byte POMin, POMax;
        short criticalRate, failRate;
        
        String[] ets = Utils.split(model.effectTarget, ":");
        String normalTargets;
        String criticalTargets;
        
        if(ets.length == 1){
            normalTargets = criticalTargets = ets[0];
        }else{
            normalTargets = ets[0];
            criticalTargets = ets[1];
        }
        
        try{
            PACost = Short.parseShort(tmp[2].trim());
        }catch(NumberFormatException e){}
        
        try{
            POMin = Byte.parseByte(tmp[3].trim());
            POMax = Byte.parseByte(tmp[4].trim());
            criticalRate = Short.parseShort(tmp[5].trim());
            failRate = Short.parseShort(tmp[6].trim());
            minLevel = Short.parseShort(tmp[tmp.length - 2].trim());
        }catch(NumberFormatException e){
            Loggin.error("cannot parse spell '" + data + "'", e);
            return null;
        }
        
        Set<EffectData> effects = parseSpellEffect(model.id, tmp[0], area.substring(0, area.length() / 2), normalTargets);
        Set<EffectData> criticals = parseSpellEffect(model.id, tmp[1], area.substring(area.length() / 2), criticalTargets);
        
        return new GameSpell(
                model, 
                level, 
                effects, 
                criticals,
                PACost,
                POMin,
                POMax, 
                criticalRate,
                failRate,
                minLevel
        );
    }
    
    static private Set<EffectData> parseSpellEffect(int spellID, String strEffect, String area, String targets){
        Set<EffectData> effects = new HashSet<>();
        
        if(strEffect.isEmpty() || strEffect.equals("-1"))
            return effects;
        
        String[] effectsArray = Utils.split(strEffect, "|");
        String[] targetsArray = Utils.split(targets, ";");
        
        for(int i = 0; i < effectsArray.length; ++i){
            if(effectsArray[i].isEmpty() || effectsArray[i].equals("-1"))
                continue;
            
            String curArea = "Pa";
            if(area.length() >= i * 2 + 2){
                curArea = area.substring(i * 2, i * 2 + 2);
            }
            
            byte target = 0;
            
            try{
                target = Byte.parseByte(targetsArray[i]);
            }catch(Exception e){}
            
            EffectData effect = EffectFactory.parseSpellEffect(spellID, effectsArray[i], curArea, target);
            
            if(effect == null)
                continue;
            
            effects.add(effect);
        }
        
        return effects;
    }
    
    static public void preloadSpells(){
        Shell.print(I18n.tr(Commons.LOADING, I18n.tr(Commons.SPELLS)), Shell.GraphicRenditionEnum.YELLOW);
        List<Spell> models = DAOFactory.spell().getAll();
        
        new ForkJoinPool().invoke(new SpellLoader(models, 0, models.size()));
        
        Shell.println(I18n.tr(Commons.SPELLS_LOADED, models.size()), Shell.GraphicRenditionEnum.GREEN);
    }
    
    static private class SpellLoader extends RecursiveAction{
        final private List<Spell> models;
        final private int start;
        final private int end;
        
        final static private int MAX_PER_TASK = 400;

        public SpellLoader(List<Spell> models, int start, int end) {
            this.models = models;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int count = end - start;
            
            if(count < MAX_PER_TASK){
                load();
                return;
            }
            
            int middle = count / 2;
            
            SpellLoader loader1 = new SpellLoader(models, start, start + middle);
            SpellLoader loader2 = new SpellLoader(models, start + middle, end);
            loader2.fork();
            loader1.compute();
            loader2.join();
        }
        
        private void load(){
            for(int i = start; i < end; ++i){
                Spell model = models.get(i);
                spells.put(model.id, getSpellByModel(model));
            }
        }
        
    }
}
