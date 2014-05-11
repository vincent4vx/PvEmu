/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.jelly.database.UpdatableDAO;
import org.pvemu.models.LearnedSpell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LearnedSpellDAO extends UpdatableDAO<LearnedSpell>{
    final private Query update = DatabaseHandler.instance().prepareQuery("UPDATE " + tableName() + " SET level = ?, position = ? WHERE player = ? AND spell = ?");
    final private Query create = DatabaseHandler.instance().prepareQuery("INSERT INTO " + tableName() + "(player, spell, level, position) VALUES(?,?,?,?)");
    final private Query getLearnedSpells = DatabaseHandler.instance().prepareQuery("SELECT * FROM " + tableName() + " WHERE player = ?");
    
    @Override
    public boolean update(LearnedSpell obj) {
        ReservedQuery query = update.reserveQuery();
        try {
            query.getStatement().setByte(1, obj.level);
            query.getStatement().setString(2, String.valueOf(obj.position));
            query.getStatement().setInt(3, obj.player);
            query.getStatement().setInt(4, obj.spell);
            
            return query.getStatement().execute();
        } catch (SQLException ex) {
            Loggin.error("Cannot save " + obj, ex);
            return false;
        }finally{
            query.release();
        }
    }

    @Override
    public boolean create(LearnedSpell obj) {
        ReservedQuery query = create.reserveQuery();
        try {
            query.getStatement().setInt(1, obj.player);
            query.getStatement().setInt(2, obj.spell);
            query.getStatement().setByte(3, obj.level);
            query.getStatement().setString(4, String.valueOf(obj.position));
            
            return query.getStatement().execute();
        } catch (SQLException ex) {
            Loggin.error("Cannot create " + obj, ex);
            return false;
        }finally{
            query.release();
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
    
    /**
     * Get spell list of a player
     * @param player
     * @return 
     */
    public Collection<LearnedSpell> getLearnedSpells(int player){
        Collection<LearnedSpell> list = new HashSet<>();
        
        ReservedQuery query = getLearnedSpells.reserveQuery();
        try {
            query.getStatement().setInt(1, player);
            ResultSet RS = query.getStatement().executeQuery();
            
            while(RS.next()){
                LearnedSpell ls = createByResultSet(RS);
                
                if(ls != null)
                    list.add(ls);
            }
            
        } catch (SQLException ex) {
            Loggin.error("cannot load learned spell of " + player, ex);
        }finally{
            query.release();
        }
        
        return list;
    }
}
