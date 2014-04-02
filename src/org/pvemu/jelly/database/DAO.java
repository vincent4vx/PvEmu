package org.pvemu.jelly.database;

import java.sql.ResultSet;

public abstract class DAO<T extends Model> {

    protected abstract String tableName();

    protected String primaryKey() {
        return "id";
    }
    
    abstract protected T createByResultSet(ResultSet RS);
}
