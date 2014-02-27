package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.pvemu.jelly.Constants;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

@Deprecated
public class BasicEvents {

    public static void onDate(IoSession session) {
        Date actDate = new Date();
        StringBuilder p = new StringBuilder();


        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        p.append(Integer.parseInt(dateFormat.format(actDate)) - 1370).append("|");

        dateFormat = new SimpleDateFormat("MM");
        String mois = (Integer.parseInt(dateFormat.format(actDate)) - 1) + "";

        if (mois.length() < 2) {
            p.append(0);
        }

        p.append(mois).append("|");

        dateFormat = new SimpleDateFormat("dd");
        String jour = Integer.parseInt(dateFormat.format(actDate)) + "";
        if (jour.length() < 2) {
            p.append(0);
        }

        p.append(jour).append("|");

        GamePacketEnum.BASIC_DATE.send(session, p.toString());
        GamePacketEnum.BASIC_TIME.send(session, String.valueOf(actDate.getTime() + 3600000));
    }
    
    public static void onPrompt(IoSession session){
        GamePacketEnum.BASIC_CONSOLE_PROMPT.send(session, Constants.NAME);
    }

    public static void onServerMessage(IoSession session, String message, Collection<Player> players) {
        String name = "Système";

        if (session != null) {
            Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");
            if (p != null) {
                name = p.getName();
            }
        }

        String msg = String.format("<font color='#%s'><b>[%s]</b> %s</font>", Constants.COLOR_RED, name, message);
        GamePacketEnum.SERVER_MESSAGE.sendToPlayerList(players, msg);
    }
}
