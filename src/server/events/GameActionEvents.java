package server.events;

import game.GameAction;
import game.objects.Player;
import java.util.concurrent.atomic.AtomicReference;
import jelly.Loggin;
import jelly.Utils;
import jelly.utils.Pathfinding;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class GameActionEvents {

    public static void onGameAction(IoSession session, String packet) {
        Player p = (Player) session.getAttribute("player");
        if (p == null) {
            return;
        }
        int actionID = 0;
        GameAction GA;
        try {
            actionID = Integer.parseInt(packet.substring(0, 3));
            String args = packet.substring(3);

            GA = new GameAction(p, actionID, args);
        } catch (Exception e) {
            e.printStackTrace();
            GamePacketEnum.GAME_ACTION_ERROR.send(session);
            return;
        }

        switch (actionID) {
            case 1: //déplacement
                onMoveAction(session, GA);
                break;
            default:
                Loggin.debug("GameAction non géré : %d", new Object[]{actionID});
                GamePacketEnum.GAME_ACTION_ERROR.send(session);
        }
    }

    public static void onGK(IoSession session, String packet) {
        boolean ok = packet.charAt(0) == 'K';

        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        int actionID = 0;
        String[] args;

        try {
            args = packet.substring(1).split("\\|");
            actionID = Integer.parseInt(args[0]);
        } catch (Exception e) {
            return;
        }

        GameAction GA = GameAction.get(p, actionID);

        if (GA == null) {
            Loggin.debug("GameAction %d non trouvée !", new Object[]{actionID});
            return;
        }

        switch (GA.actionID) {
            case 1: //déplacement
                if (ok) {
                    short cellDest = (Short) GA.get("dest");
                    MapEvents.onArrivedOnCell(session, cellDest);
                } else {
                    short cellDest = Short.parseShort(args[1]);
                    MapEvents.onArrivedOnCell(session, cellDest);
                }
                p.orientation = (byte)GA.get("ori");
                break;
        }
        GA.delete();
    }

    private static void onMoveAction(IoSession session, GameAction GA) {
        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            return;
        }


        AtomicReference<String> rPath = new AtomicReference<>(GA.args);
        int steps = Pathfinding.isValidPath(p.getMap(), p.getCell().getID(), rPath);

        Loggin.debug("Tentative de déplacement de %s de %d en %d étapes", new Object[]{p.getName(), p.getCell().getID(), steps});

        if (steps == -1000 || steps == 0) {
            Loggin.debug("Path invalide !");
            GamePacketEnum.GAME_ACTION_ERROR.send(session);
            return;
        }

        StringBuilder param = new StringBuilder();

        param.append(GA.id).append(";1;").append(p.getID()).append(";a").append(Pathfinding.cellID_To_Code(p.getCell().getID())).append(rPath.get());

        short cellDest = Pathfinding.cellCode_To_ID(rPath.get().substring(rPath.get().length() - 2));

        GA.attach("dest", cellDest);
        GA.attach("ori", Utils.parseBase64Char(rPath.get().charAt(rPath.get().length()-3)));
        GA.save();

        for (Player P : p.getMap().getPlayers().values()) {
            if (P.getSession() != null) {
                GamePacketEnum.GAME_ACTION.send(P.getSession(), param.toString());
            }
        }
    }
    
    /**
     * Envoi la GameAction au client
     * @param session
     * @param actionID
     * @param params 
     */
    public static void onSendGameAction(IoSession session, int actionID, Object ... params){
        if(session == null){
            return;
        }
        
        GamePacketEnum.GAME_ACTION.send(session, actionID + ";" + Utils.implode(";", params));
    }
}
