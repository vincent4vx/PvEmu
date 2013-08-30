package server.game;

import game.objects.Player;
import jelly.Jelly;
import jelly.Loggin;
import models.Account;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.MinaIoHandler;
import models.Character;
import server.events.*;

public class GameIoHandler extends MinaIoHandler {

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        GamePacketEnum.HELLO_GAME.send(session);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        MapEvents.onRemoveMap(session);

        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        Loggin.debug("Déconnexion de %s", new Object[]{p.getName()});
        p.getCharacter().logout();
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        Loggin.game("Send >> %s", message);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        if (!Jelly.running) { //si serveur offline, on ne recoit rien
            return;
        }
        String packet = ((String) message).trim();
        if (packet.length() > 1) {
            Loggin.game("Recv << %s", packet);
            if(!packet.startsWith("AT") && !session.containsAttribute("account")){
                session.close(true);
                return;
            }
            switch (packet.charAt(0)) {
                case 'A': //packet perso / compte
                    switch (packet.charAt(1)) {
                        case 'T': //attache account
                            try {
                                int id = Integer.parseInt(packet.substring(2));
                                Account acc = DAOFactory.account().getById(id);

                                if (acc == null || !acc.isWaiting()) {
                                    session.close(true);
                                    return;
                                }

                                acc.setSession(session);
                                GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
                            } catch (Exception e) {
                                session.close(true);
                            }
                            break;
                        case 'L':
                            Account acc = (Account)session.getAttribute("account");
                            GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
                            break;
                        case 'P': //name generator
                            CharacterEvents.onNameGenerator(session);
                            break;
                        case 'A': //character add
                            CharacterEvents.onCharacterAdd(session, packet.substring(2));
                            break;
                        case 'S': //character selected
                            CharacterEvents.onCharacterSelected(session, packet);
                            break;
                    }
                    break;
                case 'G': //game packets
                    switch (packet.charAt(1)) {
                        case 'C': //game create
                            CharacterEvents.onGameCreate(session);
                            break;
                        case 'I': //Game init map
                            MapEvents.onInitialize(session);
                            break;
                        case 'A':
                            GameActionEvents.onGameAction(session, packet.substring(2));
                            break;
                        case 'K':
                            GameActionEvents.onGK(session, packet.substring(2));
                            break;
                    }
                    break;
                case 'B':
                    switch (packet.charAt(1)) {
                        case 'D': //basic date
                            BasicEvents.onDate(session);
                            break;
                        case 'M': //message
                            BasicEvents.onMessage(session, packet.substring(2));
                            break;
                        case 'A': //commande admin
                            BasicEvents.onAdminCommand(session, packet.substring(2));
                            break;
                    }
                    break;
                case 'c': //chat
                    switch (packet.charAt(1)) {
                        case 'C': //add / remove chanel
                            session.write(packet);
                            break;
                    }
                    break;
                case 'O': //objets
                    switch(packet.charAt(1)){
                        case 'M': //object move
                            ObjectEvents.onObjectMove(session, packet.substring(2));
                            break;
                    }
                    break;
                case 'p':
                    GamePacketEnum.PONG.send(session);
                    break;
            }
        }
    }
}
