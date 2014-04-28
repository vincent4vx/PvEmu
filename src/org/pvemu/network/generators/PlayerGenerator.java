/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.network.generators;

import java.util.List;
import org.pvemu.game.ExperienceHandler;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.game.objects.player.SpellList;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Experience;

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
        str.append(Utils.join(p.getColors(), ";")).append(";");
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
        
        for(ItemPosition pos : ItemPosition.getAccessoriePositions()){
            List<GameItem> items = p.getInventory().getItemsOnPos(pos);
            
            if(!items.isEmpty())
                s.append(Integer.toHexString(items.get(0).getTemplate().id));
            
            s.append(',');
        }

        return s.toString();
    }
    
    public String generateUpdateAccessories(Player player){
        return new StringBuilder().append(player.getID())
                .append('|')
                .append(generateAccessories(player))
                .toString();
    }

    public String generateAs(Player p) {        
        StringBuilder ASData = new StringBuilder();
        Pair<Experience, Experience> xps = ExperienceHandler.instance().getLevel(p.getLevel());
        ASData.append(p.getCharacter().experience).append(',').append(xps.getFirst().player).append(',').append(xps.getSecond().player).append("|"); //xp: cur/min/max
        ASData.append(p.getCharacter().kamas).append("|").append(p.getCharacter().boostPoints).append("|").append(p.getCharacter().spellPoints).append("|"); //kamas|boostPoints|spellPoints
        ASData.append(0).append("~").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append((false ? "1" : "0")).append("|");

        ASData.append(p.getCurrentVita()).append(",").append(p.getTotalStats().get(Stats.Element.VITA)).append("|");
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
    
    public String generateWeightUsed(Player player){
        return new StringBuilder(10).append(player.getUsedPods()).append('|').append(player.getTotalPods()).toString();
    }
    
    public String generateSelectionOk(Player player){
        return new StringBuilder(50).append('|').append(player.getID()).append("|").append(player.getName()).append("|")
                    .append(player.getLevel()).append("|").append(player.getClassID()).append("|")
                    .append(player.getSexe()).append("|").append(player.getGfxID()).append("|")
                    .append(Utils.join(player.getColors(), "|")).append("|")
                    .append(GeneratorsRegistry.getObject().generateInventory(player.getInventory())).toString();
    }
    
    public String generateSpellList(SpellList list){
        StringBuilder sb = new StringBuilder();
        
        for(GameSpell spell : list.getSpells()){
            sb.append(spell.getModel().id).append('~')
                    .append(spell.getLevel()).append('~')
                    .append(list.getSpellPosition(spell)).append(';');
        }
        
        return sb.toString();
    }
}
