/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.pvemu.common.Loggin;
import org.pvemu.common.database.FindableDAO;
import org.pvemu.models.Spell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellDAO extends FindableDAO<Spell> {

    @Override
    protected String tableName() {
        return "spells";
    }

    @Override
    protected Spell createByResultSet(ResultSet RS) {
        Spell spell = new Spell();
        try {
            spell.id = RS.getInt("id");
            spell.name = RS.getString("name");
            spell.sprite = RS.getShort("sprite");
            spell.spriteInfos = RS.getString("spriteInfos");
            spell.lvl1 = RS.getString("lvl1");
            spell.lvl2 = RS.getString("lvl2");
            spell.lvl3 = RS.getString("lvl3");
            spell.lvl4 = RS.getString("lvl4");
            spell.lvl5 = RS.getString("lvl5");
            spell.lvl6 = RS.getString("lvl6");
            spell.effectTarget = RS.getString("effectTarget");
        } catch (SQLException ex) {
            Loggin.error("Cannot load the spell", ex);
        }
        
        return spell;
    }
    
}
