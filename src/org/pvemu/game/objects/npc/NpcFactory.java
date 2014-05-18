/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.npc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.models.MapNpcs;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.NpcTemplate;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class NpcFactory {
    final static private Map<Integer, NpcTemplate> templates = new ConcurrentHashMap<>();
    final static private Map<Integer, NpcQuestion> questions = new ConcurrentHashMap<>();
    
    static public void preloadNpcs(){
        Shell.print(I18n.tr(Commons.LOADING, I18n.tr(Commons.NPCS)), Shell.GraphicRenditionEnum.YELLOW);
        
        for(NpcTemplate template : DAOFactory.npcTemplate().getAll())
            templates.put(template.id, template);
        
        Shell.println(I18n.tr(Commons.NPCS_LOADED, templates.size()), Shell.GraphicRenditionEnum.GREEN);
        
        Shell.print(I18n.tr(Commons.LOADING, I18n.tr(Commons.NPCS)), Shell.GraphicRenditionEnum.YELLOW);
        
        for(NpcQuestion question : DAOFactory.question().getAll())
            questions.put(question.id, question);
        
        Shell.println(I18n.tr(Commons.QUESTIONS_LOADED, questions.size()), Shell.GraphicRenditionEnum.GREEN);
    }
    
    static private NpcTemplate getTemplateById(int id){
        if(!templates.containsKey(id)){
            NpcTemplate n = DAOFactory.npcTemplate().find(id);
            
            if(n != null)
                templates.put(id, n);
        }
        
        return templates.get(id);
    }
    
    static private NpcQuestion getQuestionById(int id){
        if(!questions.containsKey(id)){
            NpcQuestion q = DAOFactory.question().find(id);
            
            if(q != null)
                questions.put(id, q);
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
