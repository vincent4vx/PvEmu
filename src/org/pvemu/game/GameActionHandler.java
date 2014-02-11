package org.pvemu.game;

import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.InteractiveObject;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.dep.ClassData;
import org.pvemu.game.objects.map.MapCell;
import java.util.Collection;
import java.util.HashMap;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.network.events.ChatEvents;
import org.pvemu.network.events.MapEvents;

public class GameActionHandler {

    private HashMap<Integer, GameAction> actions = new HashMap<>();

    public static class GameAction {

        public GameActionHandler _handler;
        public int actionID;
        public Object[] args;
        public int id = 0;
        private HashMap<Object, Object> _attachement = new HashMap<>();

        public GameAction(GameActionHandler GAH, int actionID, Object[] args) {
            _handler = GAH;
            this.actionID = actionID;
            this.args = args;

            Object[] keys = GAH.actions.keySet().toArray();
            if (keys.length > 0) {
                id = (int) keys[keys.length - 1] + 1;
            }
        }

        public void attach(Object key, Object o) {
            _attachement.put(key, o);
        }

        public Object get(Object key) {
            return _attachement.get(key);
        }

        /**
         * sauvegarde la GA, et la met en attente
         */
        public void save() {
            _handler.actions.put(id, this);
        }

        /**
         * Supprime la GA de la liste d'attente
         */
        public void delete() {
            _handler.actions.remove(id);
        }

        /**
         * Applique la GA sur le personnage
         *
         * @param p
         */
        public void apply(Player p, boolean success, String[] data) {
            switch (actionID) {
                case 1: //déplacement
                    if (success) {
                        short cellDest = (Short) get("dest");
                        MapEvents.onArrivedOnCell(p.getSession(), cellDest);
                    } else {
                        short cellDest = Short.parseShort(data[1]);
                        MapEvents.onArrivedOnCell(p.getSession(), cellDest);
                    }
                    p.orientation = (byte) get("ori");
                    
                    for(GameAction GA : _handler.getAll()){
                        if(GA.actionID != 500){
                            continue;
                        }
                        if(success){
                            GA.apply(p, true, null);
                        }
                        GA.delete();
                    }
                    break;
                case 2: //cinématiques
                    switch ((int)args[1]) {
                        case 7: //téléportation incarnam => astrub
                            short[] mapData = ClassData.getStatuesPos(p.getClassID());
                            p.teleport(mapData[0], mapData[1]);
                            p.setStartPos(mapData);
                            ChatEvents.onSendInfoMessage(p.getSession(), 6);
                            break;
                    }
                    break;
                case 500:
                    Loggin.debug("Action sur la map (cell = %s, action = %s)", args[0], args[1]);
                    short cellID = Short.parseShort((String)args[0]);
                    int action = Integer.parseInt((String)args[1]);
                    
                    if(!Pathfinding.isAdjacentCells(p.getCell().getID(), cellID)){
                        Loggin.debug("Personnage trop loin pour effectuer l'action !");
                    }
                    
                    MapCell cell = p.getMap().getCellById(cellID);
                    
                    if(cell == null){
                        return;
                    }
                    
                    InteractiveObject IO = cell.getObj();
                    
                    if(IO == null){
                        return;
                    }
                    
                    IO.startAction(p, action);
                    break;
            }
        }
    }

    /**
     * récupère une game action
     *
     * @param p
     * @param id
     * @return
     */
    public GameAction get(int id) {
        return actions.get(id);
    }

    /**
     * Retourne la liste des GA
     *
     * @return
     */
    public Collection<GameAction> getAll() {
        return actions.values();
    }
}
