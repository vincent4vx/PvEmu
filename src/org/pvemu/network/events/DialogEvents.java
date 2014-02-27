package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

@Deprecated
public class DialogEvents {
    
    public static void onSendQuestion(IoSession session, int qID){
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
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
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
                return;
        }
        
        p.current_npc_question = null;
        
        GamePacketEnum.DIALOG_LEAVE.send(session);
    }
    
}
