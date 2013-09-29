package com.oldofus.network.game;

import com.oldofus.network.events.DialogEvents;
import com.oldofus.network.events.MapEvents;
import com.oldofus.network.events.EmotesEvents;
import com.oldofus.network.events.ExchangeEvents;
import com.oldofus.network.events.GameActionEvents;
import com.oldofus.network.events.ObjectEvents;
import com.oldofus.network.events.BasicEvents;
import com.oldofus.network.events.AccountEvents;
import com.oldofus.network.events.CharacterEvents;
import com.oldofus.game.objects.Player;
import java.net.InetSocketAddress;
import com.oldofus.jelly.Jelly;
import com.oldofus.jelly.Loggin;
import com.oldofus.models.Account;
import com.oldofus.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import com.oldofus.network.MinaIoHandler;

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

        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            Account acc = (Account) session.getAttribute("account");
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
            if (!packet.startsWith("AT") && !session.containsAttribute("account")) {
                session.close(true);
                return;
            }
            switch (packet.charAt(0)) {
                case 'A': //packet perso / compte
                    switch (packet.charAt(1)) {
                        case 'T': //attache account
                            AccountEvents.onAttach(session, packet.substring(2));
                            break;
                        case 'L':
                            AccountEvents.onCharactersList(session);
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
                        case 'D': //delete
                            CharacterEvents.onDelete(session, packet.substring(2));
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
                        case 'S':
                            BasicEvents.onSmiley(session, packet.substring(2));
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
                    switch (packet.charAt(1)) {
                        case 'M': //object move
                            ObjectEvents.onObjectMove(session, packet.substring(2));
                            break;
                    }
                    break;
                case 'p':
                    GamePacketEnum.PONG.send(session);
                    break;
                case 'e': //emotes
                    switch (packet.charAt(1)) {
                        case 'D': //direction
                            EmotesEvents.onDirection(session, packet.substring(2));
                            break;
                    }
                    break;
                case 'D': //dialogs
                    switch (packet.charAt(1)) {
                        case 'C':
                            DialogEvents.onCreate(session, packet.substring(2));
                            break;
                        case 'V':
                            DialogEvents.onLeave(session);
                            break;
                        case 'R':
                            DialogEvents.onResponse(session, packet.substring(2));
                            break;
                    }
                    break;
                case 'E': //exchange
                    switch(packet.charAt(1)){
                        case 'R':
                            ExchangeEvents.onRequest(session, packet.substring(2));
                            break;
                        case 'A':
                            ExchangeEvents.onAccept(session);
                            break;
                        case 'V':
                            ExchangeEvents.onLeave(session);
                            break;
                        case 'M':
                            ExchangeEvents.onMove(session, packet.substring(2));
                            break;
                    }
                    break;
            }
        }
    }
}
