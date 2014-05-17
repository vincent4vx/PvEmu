package org.pvemu.network.realm;

import org.pvemu.common.Constants;
import org.pvemu.common.Jelly;
import org.pvemu.common.Loggin;
import org.pvemu.common.utils.Utils;
import org.pvemu.models.Account;
import org.pvemu.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.MinaIoHandler;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.realm.input.RealmInputHandler;

/**
 *
 * @author vincent
 */
public class RealmIoHandler extends MinaIoHandler {

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        String HC = Utils.str_aleat(32);
        SessionAttributes.CONNEXION_KEY.setValue(HC, session);
        RealmPacketEnum.HELLO_CONNECTION.send(session, HC);
    }
    
    @Override
    public void sessionClosed(IoSession session){
        Account account = SessionAttributes.ACCOUNT.getValue(session);
        
        if(account != null){
            account.removeSession();
            Loggin.debug("Logout de %s", account.pseudo);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        Loggin.realm("Send >> %s", message);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        if (!Jelly.running) {
            return;
        }
        String packet = ((String) message).trim();
        if (packet.length() > 0) {
            Loggin.realm("Recv << " + packet);

            if (!session.containsAttribute("good_version")) {
                if (!packet.equals(Constants.DOFUS_VER.toString())) {
                    RealmPacketEnum.REQUIRE_VERSION.send(session);
                    session.close(true);
                } else {
                    session.setAttribute("good_version");
                }
            } else {
                String[] data = packet.split("\n");
                if (data.length == 2) {
                    String username = data[0].trim();
                    String pass = data[1].trim();
                    Account acc = DAOFactory.account().getByName(username);
                    if (acc == null || !acc.passValid(pass, SessionAttributes.CONNEXION_KEY.getValue(session)/*(String) session.getAttribute("HC")*/)) {
                        RealmPacketEnum.LOGIN_ERROR.send(session);
                        session.close(true);
                        return;
                    }

                    if (acc.getSession() == null) {
                        acc.setSession(session);
                        RealmPacketEnum.COMMUNITY.send(session);
                        RealmPacketEnum.PSEUDO.send(session, acc.pseudo);
                        RealmPacketEnum.HOSTS_LIST.send(session);
                        RealmPacketEnum.QUESTION.send(session, acc.question);
                        RealmPacketEnum.LOGIN_OK.send(session, acc.level > 0 ? "1" : "0");
                    } else {
                        RealmPacketEnum.LOGIN_ALREADY_CONNECTED.send(session);
                        session.close(false);
                    }
                } else {
                    RealmInputHandler.instance().parsePacket(packet, session);
                }
            }
        }
    }
}
