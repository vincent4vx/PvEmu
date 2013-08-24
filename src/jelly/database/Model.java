package jelly.database;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public interface Model {

    public abstract int getPk();

    public abstract void clear();
}
