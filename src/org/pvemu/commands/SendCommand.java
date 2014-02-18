/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.game.World;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Utils;
import org.pvemu.jelly.filters.AskerFilter;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.comparators.MoreThanComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SendCommand extends Command {

    @Override
    public String name() {
        return "send";
    }

    @Override
    public void perform(String[] args, Asker asker) {
        if(args.length < 3){
            asker.writeError("Commande invalide. veillez vous référer à 'help send' pour plus d'informations.");
            return;
        }
        
        String target = args[1];
        String[] packet = new String[args.length - 2];
        System.arraycopy(args, 2, packet, 0, packet.length);
        
        Player p = World.instance().getOnlinePlayer(target);
        
        if(p == null){
            asker.writeError("Joueur '" + target + "' inexistant, ou non connecté !");
            return;
        }
        
        p.getSession().write(Utils.join(packet, " "));
        asker.write("Packet envoyé !");
    }

    @Override
    public Filter conditions() {
        AskerFilter filter = new AskerFilter();
        filter.setLevel(new MoreThanComparator((byte)3));
        
        return filter;
    }
    
}
