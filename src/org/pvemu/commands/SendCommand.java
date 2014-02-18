/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.game.World;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Utils;

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
    
}
