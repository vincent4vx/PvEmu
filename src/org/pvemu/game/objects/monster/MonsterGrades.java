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

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterGrades {
    final private Map<Short, MonsterTemplate> templatesByLevel = new HashMap<>();
    final private List<MonsterTemplate> templates = new ArrayList<>();

    MonsterGrades() {}
    
    public MonsterTemplate getByLevel(short level){
        return templatesByLevel.get(level);
    }
    
    public void addTemplate(MonsterTemplate template){
        templates.add(template);
        templatesByLevel.put(template.getLevel(), template);
    }
    
    public MonsterTemplate getTemplate(int index){
        return templates.get(index);
    }
    
    public List<MonsterTemplate> getAllTemplates(){
        return templates;
    }
}
