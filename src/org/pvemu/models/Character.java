/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.models;

import org.pvemu.game.World;
//import org.pvemu.game.objects.item.OldGameItem;
import org.pvemu.game.objects.player.Player;
import java.util.HashMap;
import org.pvemu.jelly.Jelly;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author vincent
 */
public class Character implements org.pvemu.jelly.database.Model {

    public int id;
    public String name;
    public int accountId;
    public short level = 1;
    public int color1;
    public int color2;
    public int color3;
    public short gfxid;
    public byte sexe;
    public byte classId;
    public short lastMap;
    public short lastCell;
    public short startMap;
    public short startCell;
    public String baseStats;
    public byte orientation = 0;
    private Player _player = null;

    /**
     * retourne les données du personnage pour packet ALK
     *
     * @return
     */
    /*public String getForALK() {
        StringBuilder perso = new StringBuilder();
        perso.append("|");
        perso.append(id).append(";");
        perso.append(name).append(";");
        perso.append(level).append(";");
        perso.append(gfxid).append(";");
        perso.append((color1 != -1 ? Integer.toHexString(color1) : "-1")).append(";");
        perso.append((color2 != -1 ? Integer.toHexString(color2) : "-1")).append(";");
        perso.append((color3 != -1 ? Integer.toHexString(color3) : "-1")).append(";");
        perso.append(getAccessories()).append(";");
        perso.append(0).append(";");
        perso.append("1;");//ServerID
        perso.append(";");//DeathCount	this.deathCount;
        perso.append(";");//LevelMax
        return perso.toString();
    }*/
    
    /*@Deprecated
    public String getAccessories(){
        StringBuilder sb = new StringBuilder();
        
        HashMap<Byte, Integer> accessories = DAOFactory.inventory().getAccessoriesByPlayerId(id);
        
        if(accessories.containsKey(OldGameItem.POS_ARME)){
            sb.append(Integer.toHexString(accessories.get(OldGameItem.POS_ARME)));
        }
        sb.append(',');
        if(accessories.containsKey(OldGameItem.POS_COIFFE)){
            sb.append(Integer.toHexString(accessories.get(OldGameItem.POS_COIFFE)));
        }
        sb.append(',');
        if(accessories.containsKey(OldGameItem.POS_CAPE)){
            sb.append(Integer.toHexString(accessories.get(OldGameItem.POS_CAPE)));
        }
        sb.append(',');
        if(accessories.containsKey(OldGameItem.POS_FAMILIER)){
            sb.append(Integer.toHexString(accessories.get(OldGameItem.POS_FAMILIER)));
        }
        
        return sb.toString();
    }*/

    public Player getPlayer() {
        if (_player == null) {
            _player = new Player(this);
        }
        return _player;
    }

    @Override
    public void clear() {
        id = 0;
    }

    @Override
    public int getPk() {
        return id;
    }
}
