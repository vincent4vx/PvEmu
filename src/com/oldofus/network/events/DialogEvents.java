package com.oldofus.network.events;

import com.oldofus.game.objects.GameNpc;
import com.oldofus.game.objects.Player;
import com.oldofus.game.objects.dep.GMable;
import java.util.ArrayList;
import com.oldofus.models.NpcQuestion;
import com.oldofus.models.NpcResponseAction;
import com.oldofus.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import com.oldofus.network.game.GamePacketEnum;

public class DialogEvents {
    public static void onCreate(IoSession session, String packet){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.current_npc_question != null){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            onLeave(session);
            return;         
        }
        
        int id = 0;
        
        try{
            id = Integer.parseInt(packet);
        }catch(NumberFormatException e){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            return;
        }
        
        GMable gma = p.getMap().getGMable(id);
        
        if(gma == null || !(gma instanceof GameNpc)){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            return;
        }
        
        GameNpc GN = (GameNpc)gma;
        NpcQuestion Q = GN.getQuestion();
        
        if(Q == null){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            return;
        }
        
        p.current_npc_question = Q;
        
        GamePacketEnum.DIALOG_CREATE.send(session, id);
        GamePacketEnum.DIALOG_QUESTION.send(session, new StringBuilder().append(Q.id).append('|').append(Q.responses).append(";4840").toString());
    }
    
    public static void onSendQuestion(IoSession session, int qID){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null || p.current_npc_question == null){
            return;
        }
        
        NpcQuestion Q = DAOFactory.question().getById(qID);
        
        if(Q == null){
            onLeave(session);
            return;
        }
        
        p.current_npc_question = Q;
        GamePacketEnum.DIALOG_QUESTION.send(session, new StringBuilder().append(Q.id).append('|').append(Q.responses).append(";4840").toString());
    }
    
    public static void onLeave(IoSession session){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
                return;
        }
        
        p.current_npc_question = null;
        
        GamePacketEnum.DIALOG_LEAVE.send(session);
    }
    
    public static void onResponse(IoSession session, String packet){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.current_npc_question == null){
            onLeave(session);
            return;
        }
        
        try{
            String params[] = packet.split("\\|");
            int qID = Integer.parseInt(params[0]);
            int rID = Integer.parseInt(params[1]);
            
            if(qID != p.current_npc_question.id){
                onLeave(session);
                return;
            }
            
            ArrayList<NpcResponseAction> NRA_l = p.current_npc_question.getResponseActions(rID);
            
            if(NRA_l == null){
                onLeave(session);
                return;
            }
            
            boolean close = true;
            
            for(NpcResponseAction NRA : NRA_l){
                NRA.getAction().performAction(p);
                if(NRA.action_id == 1){
                    close = false;
                }
            }
            
            if(close){
                onLeave(session);
            }
        }catch(Exception e){
            onLeave(session);
        }
    }
}
