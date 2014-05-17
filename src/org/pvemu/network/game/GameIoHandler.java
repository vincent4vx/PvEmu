package org.pvemu.network.game;

import org.pvemu.game.objects.player.Player;
import org.pvemu.common.Jelly;
import org.pvemu.common.Loggin;
import org.pvemu.models.Account;
import org.apache.mina.core.session.IoSession;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.network.MinaIoHandler;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.input.GameInputHandler;

public class GameIoHandler extends MinaIoHandler {

    public static int SENT = 0;
    public static int RECV = 0;
    public static int CON = 0;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        CON++;
        GamePacketEnum.HELLO_GAME.send(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        PlayerFighter fighter = SessionAttributes.FIGHTER.getValue(session);
        
        if(fighter != null)
            fighter.leave();
        
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        if(player != null)
            ActionsRegistry.getPlayer().logout(player);
        
        Account account = SessionAttributes.ACCOUNT.getValue(session);
        
        if(account != null)
            ActionsRegistry.getAccount().logout(account);
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        SENT++;
        Loggin.game("Send >> %s", message);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        RECV++;
        if (!Jelly.running) { //si serveur offline, on ne recoit rien
            return;
        }
        String packet = ((String) message).trim();
        if (packet.length() > 1) {
            Loggin.game("Recv << %s", packet);
            if (!packet.startsWith("AT") && !SessionAttributes.ACCOUNT.exists(session)) {
                session.close(true);
                return;
            }
            GameInputHandler.instance().parsePacket(packet, session);
        }
    }
}
