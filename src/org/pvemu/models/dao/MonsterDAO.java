/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.FindableDAO;
import org.pvemu.models.Monster;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterDAO extends FindableDAO<Monster> {
    final private Map<Integer, Monster> monstersById = new HashMap<>();
    
    public MonsterDAO() {
    }

    @Override
    protected String tableName() {
        return "monsters";
    }

    @Override
    protected Monster createByResultSet(ResultSet RS) {
        Monster monster = new Monster();
        try {
            monster.id = RS.getInt("id");
            monster.name = RS.getString("name");
            monster.gfxID = RS.getShort("gfxID");
            monster.align = RS.getByte("align");
            monster.grades = RS.getString("grades");
            monster.colors = RS.getString("colors");
            monster.stats = RS.getString("stats");
            monster.spells = RS.getString("spells");
            monster.pdvs = RS.getString("pdvs");
            monster.points = RS.getString("points");
            monster.inits = RS.getString("inits");
            monster.minKamas = RS.getInt("minKamas");
            monster.maxKamas = RS.getInt("maxKamas");
            monster.exps = RS.getString("exps");
            monster.AI_type = RS.getByte("AI_type");
            monster.capturable = RS.getBoolean("capturable");
            
            monstersById.put(monster.id, monster);
        } catch (SQLException ex) {
            Loggin.error("Erreur durant le chargement du monstre.", ex);
        }
        
        return monster;
    }
    
}
