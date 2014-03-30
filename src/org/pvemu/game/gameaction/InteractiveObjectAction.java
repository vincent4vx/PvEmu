/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.gameaction;

import org.pvemu.game.objects.map.InteractiveObject;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Pathfinding;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InteractiveObjectAction implements GameAction {

    @Override
    public short id() {
        return 500;
    }

    @Override
    public void start(GameActionData data) {
        if (data.getPlayer().getActionsManager().isWalking()) {
            data.getPlayer().getActionsManager().addPendingAction(data);
        } else {
            end(data, true, null);
        }
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
        Loggin.debug("IOAction sur la map (cell = %s, action = %s)", data.getArgument(0), data.getArgument(1));
        short cellID = Short.parseShort(data.getArgument(0));
        int action = Integer.parseInt(data.getArgument(1));

        if (!Pathfinding.isAdjacentCells(data.getPlayer().getCell().getID(), cellID)) {
            Loggin.debug("Personnage trop loin pour effectuer l'action !");
        }

        MapCell cell = data.getPlayer().getMap().getCellById(cellID);

        if (cell == null) {
            return;
        }

        InteractiveObject IO = cell.getObj();

        if (IO == null) {
            return;
        }

        IO.startAction(data.getPlayer(), action);
    }

}
