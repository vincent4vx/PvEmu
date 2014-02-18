package org.pvemu.network.events;

import org.pvemu.game.objects.Player;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.pvemu.jelly.Commands;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Utils;
import org.pvemu.models.Account;
import org.apache.mina.core.session.IoSession;
import org.pvemu.commands.CommandsHandler;
import org.pvemu.commands.ConsoleAsker;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

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

    public static void onMessage(IoSession session, String packet) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        String[] args = Utils.split(packet, "|");//packet.split("\\|");
        
        if(args.length < 2){
            return;
        }
        
        if(args[1].charAt(0) == '!'){
            switch(args[1].toLowerCase()){
                case "!console":
                    session.write("BAPtest");
                    session.write("BAT2Welcome !");
                    break;
            }
            return;
        }

        switch (args[0]) {
            case "*": //canal noir (map)
                StringBuilder b = new StringBuilder();
                b.append("|").append(p.getID()).append("|").append(p.getName()).append("|").append(args[1]);
                String msg = b.toString();
                GamePacketEnum.CHAT_MESSAGE_OK.sendToMap(p.getMap(), msg);
                break;
        }
    }

    public static void onAdminCommand(IoSession session, String command) {
        Account acc = SessionAttributes.ACCOUNT.getValue(session);//(Account) session.getAttribute("account");

        if (acc == null) {
            session.close(false);
            return;
        }
        
        if(acc.level < 1){
            return;
        }

        //Commands.exec(command, acc.level, session);
        CommandsHandler.instance().execute(command, new ConsoleAsker(acc));
    }
    
    public static void onWriteConsole(IoSession session, String msg){
        for (String line : Utils.split(msg, "\n")/*msg.split("\n")*/) {
            if (line.isEmpty()) {
                break;
            }

            for (int i = 0; i < line.length(); i += 150) {
                int endIndex = i + 150;
                if (line.length() < endIndex) {
                    endIndex = line.length();
                }
                String submsg = line.substring(i, endIndex).trim();
                GamePacketEnum.BASIC_CONSOLE_WRITE.send(session, submsg);
            }
        }
        
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
    
    public static void onSmiley(IoSession session, String packet){
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        GamePacketEnum.CHAT_SMILEY.sendToMap(p.getMap(), new StringBuilder().append(p.getID()).append('|').append(packet));
    }
}
