/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.spell;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.utils.Crypt;
import org.pvemu.common.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MoveSpellPacket implements InputPacket{

    @Override
    public String id() {
        return "SM";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        if(player == null)
            return;
        
        String[] tmp = Utils.split(extra, "|", 2);
        
        int spellID;
        byte pos;
        
        try{
            spellID = Integer.parseInt(tmp[0]);
            pos = Byte.parseByte(tmp[1]);
        }catch(Exception e){
            return;
        }
        
        char cpos = Crypt.HASH[pos % Crypt.HASH.length];
        player.getSpellList().moveSpell(spellID, cpos);
    }
    
}
