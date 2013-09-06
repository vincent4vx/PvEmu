package server.realm;

import jelly.Constants;
import jelly.Jelly;
import jelly.Loggin;
import jelly.Utils;
import models.Account;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.MinaIoHandler;
import server.events.AccountEvents;

/**
 *
 * @author vincent
 */
public class RealmIoHandler extends MinaIoHandler {

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        String HC = Utils.str_aleat(32);
        session.setAttribute("HC", HC);
        RealmPacketEnum.HELLO_CONNECTION.send(session, HC);
    }
    
    @Override
    public void sessionClosed(IoSession session){
        if(session.containsAttribute("account")){
            Account acc = (Account)session.getAttribute("account");
            if(acc != null){
                acc.removeSession();
            }
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
                if (!packet.equals(Constants.DOFUS_VER)) {
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
                    if (acc == null || !acc.passValid(pass, (String) session.getAttribute("HC"))) {
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
                    switch (packet.charAt(0)) {
                        case 'A':
                            switch (packet.charAt(1)) {
                                case 'x':
                                    RealmPacketEnum.SERVER_LIST.send(session);
                                    break;
                                case 'X':
                                    AccountEvents.onServerSelected(session, packet.substring(2));
                                    break;
                            }
                            break;
                    }
                }
            }
        }
    }
}
