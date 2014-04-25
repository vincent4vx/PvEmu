/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.models;

/**
 *
 * @author vincent
 */
public class Character implements org.pvemu.jelly.database.Model {

    public int id;
    public String name;
    public int accountId;
    public short level = 1;
    public long experience;
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

    @Override
    public String toString() {
        return "Character{" + "id=" + id + ", name=" + name + ", accountId=" + accountId + ", level=" + level + ", experience=" + experience + ", color1=" + color1 + ", color2=" + color2 + ", color3=" + color3 + ", gfxid=" + gfxid + ", sexe=" + sexe + ", classId=" + classId + ", lastMap=" + lastMap + ", lastCell=" + lastCell + ", startMap=" + startMap + ", startCell=" + startCell + ", baseStats=" + baseStats + ", orientation=" + orientation + ", spellPoints=" + spellPoints + ", boostPoints=" + boostPoints + ", kamas=" + kamas + '}';
    }
}
