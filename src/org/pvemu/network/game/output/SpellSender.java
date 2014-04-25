/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellSender {
    public void boostSpell(IoSession session, int spellID, byte level){
        GamePacketEnum.SPELL_UPGRADE_OK.send(
                session,
                GeneratorsRegistry.getSpell().generateBoostSpell(spellID, level)
        );
    }
}
