package org.pvemu.network.game.input.spell;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class BoostSpellPacket implements InputPacket{

    @Override
    public String id() {
        return "SB";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        if(player == null)
            return;
        
        int spellID;
        
        try{
            spellID = Integer.parseInt(extra);
        }catch(NumberFormatException e){
            GamePacketEnum.SPELL_UPGRADE_ERROR.send(session);
            return;
        }
        
        byte newLevel = player.getSpellList().boostSpell(player, spellID);
        
        if(newLevel == -1){
            GamePacketEnum.SPELL_UPGRADE_ERROR.send(session);
        }else{
            GameSendersRegistry.getSpell().boostSpell(session, spellID, newLevel);
            GameSendersRegistry.getPlayer().stats(player, session);
        }
    }
    
}
