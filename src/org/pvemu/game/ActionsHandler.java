package org.pvemu.game;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.events.DialogEvents;
import org.pvemu.network.events.GameActionEvents;

public class ActionsHandler {

    public static class Action {

        public short actionID;
        public String[] args;
        public String conditions;

        public Action(short actionID, String[] args, String conditions) {
            this.actionID = actionID;
            this.args = args;
            this.conditions = conditions;
        }

        /**
         * Effectue l'action sur le personnage
         *
         * @param p
         */
        public void performAction(Player p) {
            ActionsHandler.performAction(this, p);
        }
    }

    /**
     * Effectue une action
     *
     * @param a
     * @param p
     */
    public static void performAction(Action a, Player p) {
        switch (a.actionID) {
            case 0: //téléportation
                if (a.args.length < 2) {
                    return;
                }
                Loggin.debug("Téléportation de %s vers map : %s; cell : %s", new Object[]{p.getName(), a.args[0], a.args[1]});
                try {
                    ActionsRegistry.getPlayer().teleport(
                            p,
                            Short.parseShort(a.args[0]),
                            Short.parseShort(a.args[1]));
                } catch (NumberFormatException e) {}
                break;
            case 1: //dialogue NPC
                if(a.args.length < 1 || a.args[0].equalsIgnoreCase("DV")){
                    DialogEvents.onLeave(p.getSession());
                    return;
                }
                try{
                    int id = Integer.parseInt(a.args[0]);
                    DialogEvents.onSendQuestion(p.getSession(), id);
                }catch(NumberFormatException e){}
                break;
            case 69: //téléportation astrub
                //GameActionEvents.onSendGameAction(p.getSession(), 0, 2, p.getID(), 7);
                GameActionEvents.onCreateGameAction(p.getSession(), 2, p.getID(), 7);
                break;
            default:
                Loggin.debug("ActionID %d inconnue (args = %s)", a.actionID, Utils.implode(";", a.args));
        }
    }
}
