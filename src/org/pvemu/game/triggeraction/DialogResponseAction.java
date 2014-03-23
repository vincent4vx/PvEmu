/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.triggeraction;

import org.pvemu.game.objects.Player;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.YesFilter;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DialogResponseAction implements TriggerAction {

    @Override
    public short actionId() {
        return 1;
    }

    @Override
    public void perform(Trigger trigger, Player player) {
        if (trigger.getArgs().length < 1 || trigger.getArgs()[0].equalsIgnoreCase("DV")) {
            player.current_npc_question = null;
            GamePacketEnum.DIALOG_LEAVE.send(player.getSession());
            return;
        }
        try {
            int id = Integer.parseInt(trigger.getArgs()[0]);
            NpcQuestion question = DAOFactory.question().getById(id);
            GameSendersRegistry.getDialog().question(player.getSession(), question);
            player.current_npc_question = question;
        } catch (NumberFormatException e) {
        }
    }

    @Override
    public Filter condition() {
        return new YesFilter();
    }

}
