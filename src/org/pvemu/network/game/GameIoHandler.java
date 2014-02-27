package org.pvemu.network.game;

import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Jelly;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.Account;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.MinaIoHandler;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.events.MapEvents;
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
        MapEvents.onRemoveMap(session);

        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            Account acc = SessionAttributes.ACCOUNT.getValue(session);//(Account) session.getAttribute("account");
            if (acc != null) {
                acc.removeSession();
            }
            return;
        }

        Loggin.debug("Déconnexion de %s", new Object[]{p.getName()});
        p.getCharacter().logout();
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
            Loggin.game("Recv << " + packet);
            if (!packet.startsWith("AT") && !SessionAttributes.ACCOUNT.exists(session)) {
                session.close(true);
                return;
            }
            GameInputHandler.instance().parsePacket(packet, session);
        }
    }
}
