/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.models;

import org.pvemu.game.objects.player.Player;

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
    public int spellPoints;
    public int boostPoints;
    public int kamas;

    @Override
    public void clear() {
        id = 0;
    }

    @Override
    public int getPk() {
        return id;
    }
}
