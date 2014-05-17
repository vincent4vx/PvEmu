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
import org.pvemu.models.Experience;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ExperienceDAO extends FindableDAO<Experience>{

    @Override
    protected String tableName() {
        return "experience";
    }

    @Override
    protected String primaryKey() {
        return "lvl";
    }

    @Override
    protected Experience createByResultSet(ResultSet RS) {
        try {
            Experience xp = new Experience();
            
            xp.lvl = RS.getShort("lvl");
            xp.player = RS.getLong("player");
            xp.job = RS.getLong("job");
            xp.honnor = RS.getLong("honnor");
            
            return xp;
        } catch (SQLException ex) {
            Loggin.error("cannot load experience !", ex);
            return null;
        }
    }
    
}
