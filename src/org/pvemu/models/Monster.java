/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models;

import org.pvemu.common.database.Model;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Monster implements Model {
    public int id;
    public String name;
    public short gfxID;
    public byte align;
    public String grades;
    public String colors;
    public String stats;
    public String spells;
    public String pdvs;
    public String points;
    public String inits;
    public int minKamas;
    public int maxKamas;
    public String exps;
    public byte AI_type;
    public boolean capturable;

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Monster{" + "id=" + id + ", name=" + name + ", gfxID=" + gfxID + ", align=" + align + ", grades=" + grades + ", colors=" + colors + ", stats=" + stats + ", spells=" + spells + ", pdvs=" + pdvs + ", points=" + points + ", inits=" + inits + ", minKamas=" + minKamas + ", maxKamas=" + maxKamas + ", exps=" + exps + ", AI_type=" + AI_type + ", capturable=" + capturable + '}';
    }
    
}
