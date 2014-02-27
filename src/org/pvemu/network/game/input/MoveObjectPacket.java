/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MoveObjectPacket implements InputPacket {

    @Override
    public String id() {
        return "OM";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        String[] data = Utils.split(extra, "|");//packet.split("\\|");
        int id;
        byte target;
        int qu = 1;
        try{
            id = Integer.parseInt(data[0]);
            target = Byte.parseByte(data[1]);
            if(data.length > 2){
                qu = Integer.parseInt(data[2]);
            }
        }catch(Exception e){
            return;
        }
        
        boolean result = p.getInventory().moveItem(id, qu, target);
        
        if(!result){
            Loggin.debug("Erreur lors du déplacement de l'objet %d", id);
        }else{
            Loggin.debug("Déplacement de l'objet %d OK !", id);
        }
        
    }
    
}
