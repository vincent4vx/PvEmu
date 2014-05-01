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
import org.pvemu.jelly.Loggin;
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
        for(byte i = 0; 
                i < grades.length
                && i < stats.length
                && i < pdvs.length
                && i < points.length
                && i < inits.length
                && i < exps.length;
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
            
            statsObj.add(Element.PA, Short.parseShort(curPoints[PA]));
            statsObj.add(Element.PM, Short.parseShort(curPoints[PM]));
            
            statsObj.add(Element.FORCE, Short.parseShort(basicStats[FORCE]));
            statsObj.add(Element.INTEL, Short.parseShort(basicStats[INTEL]));
            statsObj.add(Element.CHANCE, Short.parseShort(basicStats[CHANCE]));
            statsObj.add(Element.SAGESSE, Short.parseShort(basicStats[SAGESSE]));
            statsObj.add(Element.AGILITE, Short.parseShort(basicStats[AGILITE]));
            
            statsObj.add(Element.RES_NEUTRAL, Short.parseShort(resis[RES_NEUTRAL]));
            statsObj.add(Element.RES_FIRE, Short.parseShort(resis[RES_FIRE]));
            statsObj.add(Element.RES_GROUND, Short.parseShort(resis[RES_GROUND]));
            statsObj.add(Element.RES_WATER, Short.parseShort(resis[RES_WATER]));
            statsObj.add(Element.RES_AIR, Short.parseShort(resis[RES_AIR]));
            
            statsObj.add(Element.VITA, Short.parseShort(pdvs[i]));
            statsObj.add(Element.INIT, Short.parseShort(inits[i]));
            
            int xp = Integer.parseInt(exps[i]);
            
            gradesObj.addTemplate(new MonsterTemplate(i, model, level, statsObj, xp));
        }
    }
}
