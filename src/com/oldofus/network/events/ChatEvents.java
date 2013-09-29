package com.oldofus.network.events;

import com.oldofus.jelly.Utils;
import org.apache.mina.core.session.IoSession;
import com.oldofus.network.game.GamePacketEnum;

public class ChatEvents {
    public static void onSendInfoMessage(IoSession session, int msgID, Object ... args){
        GamePacketEnum.INFORMATION_MESSAGE.send(session, new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)));
    }
    
    public static void onSendErrorMessage(IoSession session, int msgID, Object ... args){
        GamePacketEnum.ERROR_INFORMATION_MESSAGE.send(session, new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)));
    }
    
    public static void onSendPvpMessage(IoSession session, int msgID, Object ... args){
        GamePacketEnum.PVP_INFORMATION_MESSAGE.send(session, new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)));
    }
}
