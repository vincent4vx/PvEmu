package game;

import game.objects.Player;
import jelly.Loggin;

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
                    p.teleport(
                            Short.parseShort(a.args[0]),
                            Short.parseShort(a.args[1]));
                } catch (NumberFormatException e) {
                }
                break;
        }
    }
}
