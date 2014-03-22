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
public class BasicSender {
    public void date(IoSession session){
        GamePacketEnum.BASIC_DATE.send(session, GeneratorsRegistry.getBasic().generateDate());
    }
    
    public void time(IoSession session){
        GamePacketEnum.BASIC_TIME.send(session, GeneratorsRegistry.getBasic().generateTime());
    }
}
