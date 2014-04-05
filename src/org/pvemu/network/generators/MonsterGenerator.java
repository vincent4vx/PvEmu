/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.objects.monster.MonsterTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterGenerator {
    public String generateGM(MonsterGroup group){
        StringBuilder ids = new StringBuilder();
        StringBuilder gfxs = new StringBuilder();
        StringBuilder lvls = new StringBuilder();
        StringBuilder colors = new StringBuilder();
        
        boolean first = true;
        
        for(MonsterTemplate template : group.getMonsters()){
            if(!first){
                ids.append(',');
                gfxs.append(',');
                lvls.append(',');
            }
            
            ids.append(template.getModel().id);
            gfxs.append(template.getModel().gfxID).append("^100"); //gfx + size
            lvls.append(template.getLevel());
            colors.append(template.getModel().colors).append(";0,0,0,0;");
            
            first = false;
        }
        
        return group.getCell().getID() 
                + ";2;;" + group.getID() + ";" + ids
                + ";-3;" + gfxs + ";" + lvls + ";" + colors;
    }
}
