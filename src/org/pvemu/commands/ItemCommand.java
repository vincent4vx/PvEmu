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
    
    private void perform(String[] args, ClientAsker asker) {
        if(args.length < 2){
            asker.writeError("Arguments invalides !<br/>Faites `help item` pour plus d'informations.");
            return;
        }
        
        int itemID;
        int qu = 1;
        ArrayList<String> names = new ArrayList<>();
        boolean max = false;
        
        try{
            itemID = Integer.parseInt(args[1]);
            
            if(args.length > 2){
                qu = Integer.parseInt(args[2]);
            }
            
            if(args.length > 3){
                max = args[3].equalsIgnoreCase("max");
            }
            
            if(args.length > 4){
                for(int i = 4; i < args.length; ++i)
                    names.add(args[i]);
            }else{
                names.add(SessionAttributes.PLAYER.getValue(asker.getAccount().getSession()).getName());
            }
        }catch(NumberFormatException e){
            asker.writeError("Arguments invalides !");
            return;
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
            
            GameItem item = ItemsFactory.createItem(player, template, qu, max);
            asker.write("L'item '" + template.name + "' généré avec succès pour le joueur '" + name + "'");
            GameSendersRegistry.getInformativeMessage().info(player.getSession(), 21, item.getEntry().qu, itemID);
            //player.getInventory().addItem(item);
            ActionsRegistry.getObject().addItem(item, player);
        }
    }

    @Override
    public void perform(String[] args, Asker asker) {
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
