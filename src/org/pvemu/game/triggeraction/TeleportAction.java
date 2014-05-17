/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.triggeraction;

import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.Loggin;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.YesFilter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TeleportAction implements TriggerAction {

    @Override
    public short actionId() {
        return 0;
    }

    @Override
    public void perform(Trigger trigger, Player player) {
        if (trigger.getArgs().length < 2) {
            return;
        }
        
        Loggin.debug("Téléportation de %s vers map : %s; cell : %s", new Object[]{player.getName(), trigger.getArgs()[0], trigger.getArgs()[1]});
        try {
            ActionsRegistry.getPlayer().teleport(
                    player,
                    Short.parseShort(trigger.getArgs()[0]),
                    Short.parseShort(trigger.getArgs()[1]));
        } catch (NumberFormatException e) {
        }
    }

    @Override
    public Filter condition() {
        return new YesFilter();
    }

}
