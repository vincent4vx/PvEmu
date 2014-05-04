/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.dep.Stats.Element;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.game.objects.spell.SpellFactory;
import org.pvemu.game.objects.spell.SpellLevels;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Monster;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class MonsterFactory {
    final static private byte FORCE   = 0;
    final static private byte SAGESSE = 1;
    final static private byte INTEL   = 2;
    final static private byte CHANCE  = 3;
    final static private byte AGILITE = 4;
    
    final static private byte RES_NEUTRAL = 0;
    final static private byte RES_GROUND  = 1; 
    final static private byte RES_FIRE    = 2;
    final static private byte RES_WATER   = 3;
    final static private byte RES_AIR     = 4;
    final static private byte RES_PA      = 5;
    final static private byte RES_PM      = 6;
    
    final static private byte PA = 0;
    final static private byte PM = 1;
    
    final static Map<Integer, MonsterGrades> monsters = new HashMap<>();
    
    static public MonsterGrades getMonsterGrades(int monsterId){
        if(!monsters.containsKey(monsterId)){
            MonsterGrades grades = new MonsterGrades();
            monsters.put(monsterId, grades);
            Monster model = DAOFactory.monster().find(monsterId);
            
            if(model == null){
                Loggin.debug("cannot found monster %d", monsterId);
                return grades;
            }
            
            populateGradesByModel(grades, model);
        }
        
        return monsters.get(monsterId);
    }
    
    static public void preloadMonsters(){
        Shell.print("Load monsters : ", Shell.GraphicRenditionEnum.YELLOW);
        List<Monster> models = DAOFactory.monster().getAll();
        
        for(Monster model : models){
            MonsterGrades grades = new MonsterGrades();
            monsters.put(model.id, grades);
            populateGradesByModel(grades, model);
        }
        Shell.println(models.size() + " monsters loaded", Shell.GraphicRenditionEnum.GREEN);
    }
    
    static public List<MonsterTemplate> parseMonsterList(String monsterList){
        List<MonsterTemplate> templates = new ArrayList<>();
        
        String[] datas = Utils.split(monsterList, "|");
        
        for(String data : datas){
            String[] tmp = Utils.split(data, ",");
            try{
                int id = Integer.parseInt(tmp[0]);
                MonsterGrades grades = getMonsterGrades(id);
                
                if(grades == null){
                    Loggin.debug("Cannot find monster %d", id);
                    continue;
                }
                
                if(tmp.length == 1){
                    templates.addAll(grades.getAllTemplates());
                }else{
                    MonsterTemplate tpl = grades.getByLevel(Short.parseShort(tmp[1]));
                    
                    if(tpl == null){
                        Loggin.debug("Cannot find grade lvl %s for monster %d", tmp[1], id);
                        continue;
                    }
                    templates.add(tpl);
                }
            }catch(NumberFormatException e){}
       }
        
        return templates;
    }
    
    static public MonsterGroup generateMonsterGroup(List<MonsterTemplate> monsters, GameMap map){
        if(monsters.isEmpty() || map.getModel().groupmaxsize == 0){
            return null;
        }
        int id = map.getNextGmId();
        MapCell cell = MapUtils.getRandomValidCell(map);
        
        int size = Utils.rand(1, map.getModel().groupmaxsize);
        
        List<MonsterTemplate> list = new ArrayList<>(size);
        
        for(int i = 0; i < size; ++i){
            list.add(monsters.get(Utils.rand(monsters.size())));
        }
        
        return new MonsterGroup(id, list, cell, map);
    }
    
    static private void populateGradesByModel(MonsterGrades gradesObj, Monster model){
        String[] grades = Utils.split(model.grades, "|");
        String[] stats = Utils.split(model.stats, "|");
        String[] pdvs = Utils.split(model.pdvs, "|");
        String[] points = Utils.split(model.points, "|");
        String[] inits = Utils.split(model.inits, "|");
        String[] exps = Utils.split(model.exps, "|");
        String[] spells = Utils.split(model.spells, "|");
        for(byte i = 0; 
                i < grades.length
                && i < stats.length
                && i < pdvs.length
                && i < points.length
                && i < inits.length
                && i < exps.length
                && i < spells.length;
                ++i){
            String[] tmp = Utils.split(grades[i], "@", 2);
            
            if(tmp.length < 2)
                continue;
            
            short level = Short.parseShort(tmp[0]);
            String[] resis = Utils.split(tmp[1], ";");
            
            if(resis.length < 7)
                continue;
            
            String[] basicStats = Utils.split(stats[i], ",");
            
            if(basicStats.length < 5)
                continue;
            
            String[] curPoints = Utils.split(points[i], ";");
            
            if(curPoints.length < 2)
                continue;
            
            Stats statsObj = new Stats();
            
            statsObj.add(Element.PA, Integer.parseInt(curPoints[PA].trim()));
            statsObj.add(Element.PM, Integer.parseInt(curPoints[PM].trim()));
            
            statsObj.add(Element.FORCE, Integer.parseInt(basicStats[FORCE].trim()));
            statsObj.add(Element.INTEL, Integer.parseInt(basicStats[INTEL].trim()));
            statsObj.add(Element.CHANCE, Integer.parseInt(basicStats[CHANCE].trim()));
            statsObj.add(Element.SAGESSE, Integer.parseInt(basicStats[SAGESSE].trim()));
            statsObj.add(Element.AGILITE, Integer.parseInt(basicStats[AGILITE].trim()));
            
            statsObj.add(Element.RES_NEUTRAL, Integer.parseInt(resis[RES_NEUTRAL].trim()));
            statsObj.add(Element.RES_FIRE, Integer.parseInt(resis[RES_FIRE].trim()));
            statsObj.add(Element.RES_GROUND, Integer.parseInt(resis[RES_GROUND].trim()));
            statsObj.add(Element.RES_WATER, Integer.parseInt(resis[RES_WATER].trim()));
            statsObj.add(Element.RES_AIR, Integer.parseInt(resis[RES_AIR].trim()));
            
            statsObj.add(Element.VITA, Integer.parseInt(pdvs[i].trim()));
            statsObj.add(Element.INIT, Integer.parseInt(inits[i].trim()));
            
            Map<Integer, GameSpell> spellList = new HashMap<>();
            for(String spellData : Utils.split(spells[i], ";")){
                if(spellData.isEmpty() || spellData.equals("-1"))
                    continue;
                
                tmp = Utils.split(spellData, "@");
                int spellID;
                byte spellLevel = 1;
                
                try{
                    spellID = Integer.parseInt(tmp[0].trim());
                    
                    if(tmp.length > 1)
                        spellLevel = Byte.parseByte(tmp[1].trim());
                }catch(NumberFormatException e){
                    Loggin.debug("Error during loading spells of %s grade : %d", model, i);
                    continue;
                }
                
                SpellLevels spellLevels = SpellFactory.getSpellLevelsById(spellID);
                
                if(spellLevels == null){
                    Loggin.debug("Cannot find spell %d", spellID);
                    continue;
                }
                
                GameSpell gs = spellLevels.getSpellByLevel(spellLevel);
                
                if(gs == null){
                    Loggin.debug("Cannot find spell %d at level %d", spellID, spellLevel);
                    continue;
                }
                
                spellList.put(spellID, gs);
            }
            
            int xp = Integer.parseInt(exps[i]);
            
            gradesObj.addTemplate(new MonsterTemplate(i, model, level, statsObj, spellList, xp));
        }
    }
}
