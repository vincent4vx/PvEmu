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
    
    final static private byte RES_NEUTRE = 0;
    final static private byte RES_TERRE  = 1; 
    final static private byte RES_FEU    = 2;
    final static private byte RES_EAU    = 3;
    final static private byte RES_AIR    = 4;
    final static private byte RES_PA     = 5;
    final static private byte RES_PM     = 6;
    
    final static private byte PA = 0;
    final static private byte PM = 1;
    
    final static Map<Integer, List<MonsterTemplate>> templatesById = new HashMap<>();
    
    static public List<MonsterTemplate> getTemplatesByMonsterId(int monsterId){
        List<MonsterTemplate> templates = templatesById.get(monsterId);
        
        if(templates == null){
            Monster model = DAOFactory.monster().find(monsterId);
            
            if(model == null){
                Loggin.debug("cannot found monster %d", monsterId);
                return templates;
            }
            
        }
        
        return templates;
    }
    
    static private List<MonsterTemplate> getTemplatesByModel(Monster model){
        List<MonsterTemplate> templates = new ArrayList<>();
        
        String[] grades = Utils.split(model.grades, "|");
        String[] stats = Utils.split(model.stats, "|");
        String[] pdvs = Utils.split(model.pdvs, "|");
        String[] points = Utils.split(model.points, "|");
        String[] inits = Utils.split(model.inits, "|");
        String[] exps = Utils.split(model.exps, "|");
        for(int i = 0; 
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
            
            //TODO: resistances
            
            statsObj.add(Element.VITA, Short.parseShort(pdvs[i]));
            statsObj.add(Element.INIT, Short.parseShort(inits[i]));
            
            int xp = Integer.parseInt(exps[i]);
            
            templates.add(new MonsterTemplate(model, level, statsObj, xp));
        }
        
        return templates;
        

    }
}
