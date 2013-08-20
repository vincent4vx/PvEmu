/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import game.objects.Player;
import game.objects.dep.ClassData;
import java.util.Random;
import jelly.Config;
import models.dao.DAOFactory;
import server.game.GamePacketEnum;

/**
 *
 * @author vincent
 */
public class Character implements jelly.database.Model {

    public int id;
    public String name;
    public int accountId;
    public int level = 1;
    public int color1;
    public int color2;
    public int color3;
    public int gfxid;
    public byte sexe;
    public byte classId;
    public short lastMap;
    public short lastCell;
    public short startMap;
    public short startCell;
    
    private Player _player = null;

    /**
     * retourne les données du personnage pour packet ALK
     * @return 
     */
    public String getForALK() {
        StringBuilder perso = new StringBuilder();
        perso.append("|");
        perso.append(id).append(";");
        perso.append(name).append(";");
        perso.append(level).append(";");
        perso.append(gfxid).append(";");
        perso.append((color1 != -1 ? Integer.toHexString(color1) : "-1")).append(";");
        perso.append((color2 != -1 ? Integer.toHexString(color2) : "-1")).append(";");
        perso.append((color3 != -1 ? Integer.toHexString(color3) : "-1")).append(";");
        perso.append("").append(";");
        perso.append(0).append(";");
        perso.append("1;");//ServerID
        perso.append(";");//DeathCount	this.deathCount;
        perso.append(";");//LevelMax
        return perso.toString();
    }
    
    public Player getPlayer(){
        if(_player == null){
            _player = new Player(this);
        }
        return _player;
    }
    
    public void logout(){
        if(_player == null){
            return;
        }
        
        _player.getSession().removeAttribute("player");
        _player.getSession().removeAttribute("account");
        _player.setSession(null);
        _player = null;
    }

    public void clear() {
        id = 0;
    }

    public int getPk() {
        return id;
    }
}
