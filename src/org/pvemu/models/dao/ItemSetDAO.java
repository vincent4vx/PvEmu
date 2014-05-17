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
import org.pvemu.models.ItemSet;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ItemSetDAO extends FindableDAO<ItemSet>{

    @Override
    protected String tableName() {
        return "itemsets";
    }

    @Override
    protected ItemSet createByResultSet(ResultSet RS) {
        try{
            ItemSet is = new ItemSet();
            
            is.id = RS.getInt("id");
            is.name = RS.getString("name");
            is.items = RS.getString("items");
            is.bonus = RS.getString("bonus");
            
            return is;
        }catch(SQLException e){
            Loggin.error("Cannot load item set", e);
            return null;
        }
    }
    
}
