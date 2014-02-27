/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.network.generators;

import java.util.HashMap;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.utils.Utils;

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
    public String generateGM(Player p) {
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
        str.append(generateAccessories(p)).append(";"); //stuff
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
    
    public String generateAccessories(Player p){
        StringBuilder s = new StringBuilder();
        HashMap<Byte, GameItem> wornItems = p.getInventory().getItemsByPos();

        if (wornItems.containsKey(GameItem.POS_ARME)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_ARME).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_COIFFE)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_COIFFE).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_CAPE)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_CAPE).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_FAMILIER)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_FAMILIER).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_BOUCLIER)) {
            s.append(wornItems.get(GameItem.POS_BOUCLIER).getItemStats().getID());
        }

        return s.toString();
    }

    public String generateAs(Player p) {        
        StringBuilder ASData = new StringBuilder();
        ASData.append("0,0").append("|");
        ASData.append(0).append("|").append(0).append("|").append(0).append("|");
        ASData.append(0).append("~").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append((false ? "1" : "0")).append("|");

        ASData.append(p.getPDVMax()).append(",").append(p.getPDVMax()).append("|");
        ASData.append(10000).append(",10000|");

        ASData.append(p.getInitiative()).append("|");
        ASData.append(p.getProspection()).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.PA)).append(",").append(p.getStuffStats().get(Stats.Element.PA)).append(",").append(0).append(",").append(0).append(",").append(p.getTotalStats().get(Stats.Element.PA)).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.PM)).append(",").append(p.getStuffStats().get(Stats.Element.PM)).append(",").append(0).append(",").append(0).append(",").append(p.getTotalStats().get(Stats.Element.PM)).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.FORCE)).append(",").append(p.getStuffStats().get(Stats.Element.FORCE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.VITA)).append(",").append(p.getStuffStats().get(Stats.Element.VITA)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.SAGESSE)).append(",").append(p.getStuffStats().get(Stats.Element.SAGESSE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.CHANCE)).append(",").append(p.getStuffStats().get(Stats.Element.CHANCE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.AGILITE)).append(",").append(p.getStuffStats().get(Stats.Element.AGILITE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.INTEL)).append(",").append(p.getStuffStats().get(Stats.Element.INTEL)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.PO)).append(",").append(p.getStuffStats().get(Stats.Element.PO)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.INVOC)).append(",").append(p.getStuffStats().get(Stats.Element.INVOC)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.DOMMAGE)).append(",").append(p.getStuffStats().get(Stats.Element.DOMMAGE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|"); //PDOM ?
        ASData.append("0,0,0,0|");//Maitrise ?
        ASData.append(p.getBaseStats().get(Stats.Element.PERDOM)).append(",").append(p.getStuffStats().get(Stats.Element.PERDOM)).append("," + "0").append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.SOIN)).append(",").append(p.getStuffStats().get(Stats.Element.SOIN)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.TRAP_DOM)).append(",").append(p.getStuffStats().get(Stats.Element.TRAP_DOM)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.TRAP_PERDOM)).append(",").append(p.getStuffStats().get(Stats.Element.TRAP_PERDOM)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|"); //?
        ASData.append(p.getBaseStats().get(Stats.Element.CC)).append(",").append(p.getStuffStats().get(Stats.Element.CC)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(p.getBaseStats().get(Stats.Element.EC)).append(",").append(p.getStuffStats().get(Stats.Element.EC)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");


        return ASData.toString();
    }
}
