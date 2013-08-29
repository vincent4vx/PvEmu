/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import game.World;
import game.objects.Player;
import jelly.Jelly;
import jelly.Loggin;
import models.dao.DAOFactory;

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
    public String baseStats;
    private Player _player = null;

    /**
     * retourne les données du personnage pour packet ALK
     *
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
        perso.append(getPlayer().getGMStuff()).append(";");
        perso.append(0).append(";");
        perso.append("1;");//ServerID
        perso.append(";");//DeathCount	this.deathCount;
        perso.append(";");//LevelMax
        return perso.toString();
    }

    public Player getPlayer() {
        if (_player == null) {
            _player = new Player(this);
        }
        return _player;
    }

    public void logout() {
        if (_player == null) {
            return;
        }

        _player.getAccount().removeSession();
        World.removeOnline(_player);
        _player.getSession().removeAttribute("player");
        _player.getSession().removeAttribute("account");
        _player.setSession(null);
        _player = null;

        if (Jelly.running) {
            if (!DAOFactory.character().update(this)) {
                Loggin.debug("Sauvegarde impossible de %s", name);
            }
        }
    }

    public void clear() {
        id = 0;
    }

    public int getPk() {
        return id;
    }
}
