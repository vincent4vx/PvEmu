/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.Database;
import org.pvemu.jelly.database.UpdatableDAO;
import org.pvemu.models.LearnedSpell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LearnedSpellDAO extends UpdatableDAO<LearnedSpell>{
    final private PreparedStatement updateStatement = Database.prepare("UPDATE " + tableName() + " SET level = ?, position = ? WHERE player = ? AND spell = ?");
    final private PreparedStatement createStatement = Database.prepare("INSERT INTO " + tableName() + "(player, spell, level, position) VALUES(?,?,?,?)");
    final private PreparedStatement getLearnedSpellsStatement = Database.prepare("SELECT * FROM " + tableName() + " WHERE player = ?");
    
    @Override
    public boolean update(LearnedSpell obj) {
        Loggin.debug("saving %s", obj);
        try {
            updateStatement.setByte(1, obj.level);
            updateStatement.setString(2, String.valueOf(obj.position));
            updateStatement.setInt(3, obj.player);
            updateStatement.setInt(4, obj.spell);
            
            return updateStatement.execute();
        } catch (SQLException ex) {
            Loggin.error("Cannot save " + obj, ex);
            return false;
        }
    }

    @Override
    public boolean create(LearnedSpell obj) {
        Loggin.debug("creating new %s", obj);
        try {
            createStatement.setInt(1, obj.player);
            createStatement.setInt(2, obj.spell);
            createStatement.setByte(3, obj.level);
            createStatement.setString(4, String.valueOf(obj.position));
            
            return createStatement.execute();
        } catch (SQLException ex) {
            Loggin.error("Cannot create " + obj, ex);
            return false;
        }
    }

    @Override
    protected String tableName() {
        return "learned_spells";
    }

    @Override
    protected LearnedSpell createByResultSet(ResultSet RS) {
        try {
            LearnedSpell spell = new LearnedSpell();
            
            spell.player = RS.getInt("player");
            spell.spell = RS.getInt("spell");
            spell.level = RS.getByte("level");
            spell.position = RS.getString("position").charAt(0);
            
            return spell;
        } catch (SQLException ex) {
            Loggin.error("cannot load the learned spell", ex);
            return null;
        }
    }

    @Override
    public LearnedSpell find(int pk) {
        throw new UnsupportedOperationException("cannot find with single primary key");
    }
    
    public Collection<LearnedSpell> getLearnedSpells(int player){
        Collection<LearnedSpell> list = new HashSet<>();
        try {
            getLearnedSpellsStatement.setInt(1, player);
            ResultSet RS = getLearnedSpellsStatement.executeQuery();
            
            while(RS.next()){
                LearnedSpell ls = createByResultSet(RS);
                
                if(ls != null)
                    list.add(ls);
            }
            
        } catch (SQLException ex) {
            Loggin.error("cannot load learned spell of " + player, ex);
        }
        
        return list;
    }
}
