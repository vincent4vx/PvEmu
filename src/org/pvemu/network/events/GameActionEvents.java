package org.pvemu.network.events;

import org.pvemu.game.GameActionHandler.GameAction;
import org.pvemu.game.objects.Player;
import java.util.concurrent.atomic.AtomicReference;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.jelly.utils.Pathfinding;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

@Deprecated
public class GameActionEvents {

    @Deprecated
    public static void onMoveAction(IoSession session, GameAction GA) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }


        AtomicReference<String> rPath = new AtomicReference<>((String) GA.args[0]);
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
        GA.attach("ori", Utils.parseBase64Char(rPath.get().charAt(rPath.get().length() - 3)));
        GA.save();

        for (Player P : p.getMap().getPlayers().values()) {
            if (P.getSession() != null) {
                GamePacketEnum.GAME_ACTION.send(P.getSession(), param.toString());
            }
        }
    }
    
    @Deprecated
    public static void onMapAction(Player p, GameAction GA){
        short cellID = Short.parseShort((String)GA.args[0]);
        
        if(Pathfinding.isAdjacentCells(p.getCell().getID(), cellID)){
            GA.apply(p, true, null);
        }else{
            GA.save();
        }
    }

    /**
     * Envoi la GameAction au client
     *
     * @param session
     * @param actionID
     * @param params
     */
    @Deprecated
    public static void onSendGameAction(IoSession session, GameAction GA) {
        if (session == null) {
            return;
        }

        GamePacketEnum.GAME_ACTION.send(session, GA.id + ";" + GA.actionID + ";" + Utils.implode(";", GA.args));
    }

    /**
     * Crée une GA, la sauvegarde et l'envoi au client
     *
     * @param session
     * @param actionID
     * @param args
     */
    public static void onCreateGameAction(IoSession session, int actionID, Object... args) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        GameAction GA = new GameAction(p.getActions(), actionID, args);
        GA.save();
        onSendGameAction(session, GA);
    }
}
