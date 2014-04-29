/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InformativeMessageSender {
    public void info(IoSession session, int msgID, Object... args){
        GamePacketEnum.INFORMATION_MESSAGE.send(session, GeneratorsRegistry.getInformativeMessage().generateIm(msgID, args));
    }
    
    public void error(IoSession session, int msgID, Object... args){
        GamePacketEnum.ERROR_INFORMATION_MESSAGE.send(session, GeneratorsRegistry.getInformativeMessage().generateIm(msgID, args));
    }
    
    public void pvp(IoSession session, int msgID, Object... args){
        GamePacketEnum.PVP_INFORMATION_MESSAGE.send(session, GeneratorsRegistry.getInformativeMessage().generateIm(msgID, args));
    }
    
    public void info(IoSession session, Pair<Integer, Object[]>[] msgs){
        GamePacketEnum.INFORMATION_MESSAGE.send(session, GeneratorsRegistry.getInformativeMessage().generateIm(msgs));
    }
    
    public void error(IoSession session, Pair<Integer, Object[]>[] msgs){
        GamePacketEnum.ERROR_INFORMATION_MESSAGE.send(session, GeneratorsRegistry.getInformativeMessage().generateIm(msgs));
    }
    
    public void pvp(IoSession session, Pair<Integer, Object[]>[] msgs){
        GamePacketEnum.PVP_INFORMATION_MESSAGE.send(session, GeneratorsRegistry.getInformativeMessage().generateIm(msgs));
    }
}
