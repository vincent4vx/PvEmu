/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.npc;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.jelly.Shell;
import org.pvemu.models.MapNpcs;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.NpcTemplate;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class NpcFactory {
    final static private Map<Integer, NpcTemplate> templates = new HashMap<>();
    final static private Map<Integer, NpcQuestion> questions = new HashMap<>();
    
    static public void preloadNpcs(){
        Shell.print("Loading npc templates : ", Shell.GraphicRenditionEnum.YELLOW);
        
        for(NpcTemplate template : DAOFactory.npcTemplate().getAll())
            templates.put(template.id, template);
        
        Shell.println(templates.size() + " npc loaded", Shell.GraphicRenditionEnum.GREEN);
        
        Shell.print("Loading npc questions : ", Shell.GraphicRenditionEnum.YELLOW);
        
        for(NpcQuestion question : DAOFactory.question().getAll())
            questions.put(question.id, question);
        
        Shell.println(questions.size() + " questions loaded", Shell.GraphicRenditionEnum.GREEN);
    }
    
    static private NpcTemplate getTemplateById(int id){
        if(!templates.containsKey(id)){
            templates.put(id, DAOFactory.npcTemplate().find(id));
        }
        
        return templates.get(id);
    }
    
    static private NpcQuestion getQuestionById(int id){
        if(!questions.containsKey(id)){
            questions.put(id, DAOFactory.question().find(id));
        }
        
        return questions.get(id);
    }
    
    static public GameNpc getNpcByMapNpc(MapNpcs data, int id){
        NpcTemplate template = getTemplateById(data.npcid);
        
        if(template == null)
            return null;
        
        NpcQuestion question = getQuestionById(template.initQuestion);
        
        if(question == null)
            return null;
        
        return new GameNpc(id, template, data.orientation, data.cellid, question);
    }
}
