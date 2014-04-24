/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models;

import org.pvemu.jelly.database.Model;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LearnedSpell implements Model{
    public int player;
    public int spell;
    public byte level;
    public char position;

    @Override
    public int getPk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        player = 0;
        spell = 0;
        level = 0;
        position = 'a';
    }

    @Override
    public String toString() {
        return "LearnedSpell{" + "player=" + player + ", spell=" + spell + ", level=" + level + ", position=" + position + '}';
    }
    
}
