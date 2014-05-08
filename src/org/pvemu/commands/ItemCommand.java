/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.ClientAsker;
import java.util.ArrayList;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.game.World;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.factory.ItemsFactory;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.FilterFactory;
import org.pvemu.models.ItemTemplate;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.SessionAttributes;
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
            "Ajoute un item à un joueur.",
            "item [itemID]  {{quantité = 1 {stats : max|rand = rand} {player1 = %me {player2...}}}} : ajoute l'item [itemID] aux joueurs {player1 {player2...}} avec des stats {stats}"
        };
    }
    
    private void perform(ArgumentList args, ClientAsker asker) throws CommandArgumentException {        
        int itemID = args.getInteger(1);
        int qu = args.getInteger(2, 1);
        ArrayList<String> names = new ArrayList<>();
        boolean max = false;
        
        itemID = args.getInteger(1);
        

        if(args.size() > 3){
            max = args.getArgument(3).equalsIgnoreCase("max");
        }

        if(args.size() > 4){
            for(int i = 4; i < args.size(); ++i)
                names.add(args.getArgument(i));
        }else{
            names.add(SessionAttributes.PLAYER.getValue(asker.getAccount().getSession()).getName());
        }
        
        ItemTemplate template = DAOFactory.item().getById(itemID);
        
        if(template == null){
            asker.writeError("L'item '" + itemID + "' est inexistant !");
            return;
        }
        
        for(String name : names){
            Player player = World.instance().getOnlinePlayer(name);
            
            if(player == null){
                asker.writeError("Joueur '" + name + "' introuvable !");
                continue;
            }
            
            GameItem item = ItemsFactory.createItem(player.getInventory(), template, qu, max);
            asker.write("L'item '" + template.name + "' généré avec succès pour le joueur '" + name + "'");
            GameSendersRegistry.getInformativeMessage().info(player.getSession(), 21, item.getEntry().qu, itemID);
            //player.getInventory().addItem(item);
            ActionsRegistry.getObject().addItem(item, player);
        }
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException {
        if(!(asker instanceof ClientAsker)){
            asker.writeError("Erreur : vous devez être en jeu !");
            return;
        }
        
        perform(args, (ClientAsker)asker);
    }

    @Override
    public Filter conditions() {
        return FilterFactory.moderatorAskerFilter();
    }
    
}
