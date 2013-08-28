package server.events;

import jelly.Utils;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class ChatEvents {
    public static void onSendInfoMessage(IoSession session, int msgID, String ... args){
        GamePacketEnum.INFORMATION_MESSAGE.send(session, new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)));
    }
    
    public static void onSendErrorMessage(IoSession session, int msgID, String ... args){
        GamePacketEnum.ERROR_INFORMATION_MESSAGE.send(session, new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)));
    }
    
    public static void onSendPvpMessage(IoSession session, int msgID, String ... args){
        GamePacketEnum.PVP_INFORMATION_MESSAGE.send(session, new StringBuilder().append(msgID).append(';').append(Utils.implode("~", args)));
    }
}
