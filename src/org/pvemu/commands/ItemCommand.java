/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import java.util.List;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.parser.ParserError;
import org.pvemu.commands.parser.variable.VariableUtils;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.factory.ItemsFactory;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;
import org.pvemu.models.ItemTemplate;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ItemCommand extends Command {

    @Override
    public String name() {
        return "item";
    }

    @Override
    public String[] usage() {
        return new String[]{
            I18n.tr(Commands.ITEM_USAGE1),
            I18n.tr(Commands.ITEM_USAGE2)
        };
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException {
        int itemID = args.getInteger(1);
        int qu = args.getInteger(2, 1);
        boolean max = args.getArgument(3, "rand").equals("max");
        
        List<Player> players;
        
        try{
            players = args.getPlayerList(4, VariableUtils.getMe(asker));
        }catch(ParserError e){
            asker.writeError(e.getMessage());
            return;
        }
        
        ItemTemplate template = DAOFactory.item().getById(itemID);
        
        if(template == null){
            asker.writeError(I18n.tr(Commands.ITEM_NOT_FOUND, itemID));
            return;
        }
        
        for(Player player : players){
            GameItem item = ItemsFactory.createItem(player.getInventory(), template, qu, max);
            asker.write(I18n.tr(Commands.ITEM_GENERATED, template.name, player.getName()));
            GameSendersRegistry.getInformativeMessage().info(player.getSession(), 21, item.getEntry().qu, itemID);
            ActionsRegistry.getObject().addItem(item, player);
        }
    }

    @Override
    public Filter conditions() {
        return FilterFactory.moderatorAskerFilter();
    }
    
}
