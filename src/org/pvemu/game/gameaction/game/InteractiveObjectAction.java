/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.gameaction.game;

import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InteractiveObjectAction implements GameAction<Player> {

    @Override
    public short id() {
        return GameActionsRegistry.INTERACTIVE_OBJECT;
    }

    @Override
    public void start(GameActionData<Player> data) {
        if (data.getPerformer().getActionsManager().isWalking()) {
            data.getPerformer().getActionsManager().addPendingAction(data);
        } else {
            end(data, true, null);
        }
    }

    @Override
    public void end(GameActionData<Player> data, boolean success, String[] args) {
        Loggin.debug("IOAction sur la map (cell = %s, action = %s)", data.getArgument(0), data.getArgument(1));
        short cellID = Short.parseShort(data.getArgument(0));
        int action = Integer.parseInt(data.getArgument(1));

        if (!MapUtils.isAdjacentCells(data.getPerformer().getCell().getID(), cellID)) {
            Loggin.debug("Personnage trop loin pour effectuer l'action !");
        }

        MapCell cell = data.getPerformer().getMap().getCellById(cellID);

        if (cell == null) {
            return;
        }

        InteractiveObject IO = cell.getObj();

        if (IO == null) {
            return;
        }

        IO.startAction(data.getPerformer(), action);
    }

}
