/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oldofus.network.generators;

import com.oldofus.game.objects.Player;
import com.oldofus.jelly.Constants;
import com.oldofus.jelly.Utils;
import com.oldofus.jelly.scripting.hooks.PlayerHooks;

/**
 * Génère les packets pour les joueurs
 *
 * @author vincent
 */
public class PlayerGenerator {

    /**
     * Génère le packet GM (add to map)
     *
     * @param p Le personnage cible
     * @return le paramettre du packet GM
     */
    public static String generateGM(Player p) {
        {
            String packet;
            if ((packet = PlayerHooks.callGMHook(p)) != null && packet.length() > 1) {
                return packet;
            }
        }
        StringBuilder str = new StringBuilder();

        str.append(p.getCell().getID()).append(";").append(p.orientation).append(";");

        if (Constants.DOFUS_VER_ID >= 1200) {
            str.append("0").append(";");//FIXME: BonusValue /!\ uniquement à partir de Dofus 1.20 /!\
        }

        str.append(p.getID()).append(";").append(p.getName()).append(";").append(p.getClassID());

        //30^100,1247;
        //FIXME pnj suiveur ? 
        str.append("").append(";"); //title
        str.append(p.getGfxID());//.append("^").append(100) //gfxID^size //FIXME ,GFXID pnj suiveur
        //                .append(",").append("1247") // mob suvieur1
        //                .append(",").append("1503") //mob suiveur2
        //                .append(",").append("1451") //mob suiveur 3
        //                .append(",").append("1186") // mob suiveur 4
        //                .append(",").append("8013") // MS5
        //                .append(",").append("8018") // MS6
        //                .append(",").append("8017") // MS7 ... Infini quoi
        if (Constants.DOFUS_VER_ID >= 1100) {
            str.append('^').append(100); //size
        }
        str.append(";");
        str.append(p.getSexe()).append(";");
        str.append(0).append(","); //alignement
        str.append("0").append(",");//FIXME:?
        str.append((false ? 0 : "0")).append(","); //grade
        str.append(p.getLevel() + p.getID());
        if (false && 0 > 0) { //déshoneur
            str.append(",");
            str.append(0 > 0 ? 1 : 0).append(';');
        } else {
            str.append(";");
        }
        //str.append(_lvl).append(";");
        str.append(Utils.implode(";", p.getColors())).append(";");
        str.append(p.getGMStuff()).append(";"); //stuff
         /*if (hasEquiped(10054) || hasEquiped(10055) || hasEquiped(10056) || hasEquiped(10058) || hasEquiped(10061) || hasEquiped(10102)) {
         str.append(3).append(";");
         } else {*/
        str.append((p.getLevel() > 99 ? (p.getLevel() > 199 ? (2) : (1)) : (0))).append(";");
        //}
        //str.append("0;");
        str.append(";");//Emote
        str.append(";");//Emote timer
        /*if (this._guildMember != null && this._guildMember.getGuild().getMembers().size() > 9)//>9TODO:
         {
         str.append(this._guildMember.getGuild().get_name()).append(";").append(this._guildMember.getGuild().get_emblem()).append(";");
         } else {
         str.append(";;");
         }*/
        str.append(";;");
        str.append(p.restriction).append(";");//Restriction
        //str.append((_onMount && _mount != null ? _mount.get_color(parsecolortomount()) : "")).append(";");
        str.append(";");
        str.append(";");

        return str.toString();
    }
}
