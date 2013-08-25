package jelly.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DAO<T extends Model> {

    protected PreparedStatement findStatement = null;
    protected PreparedStatement deleteStatement = null;

    protected abstract String tableName();

    protected String primaryKey() {
        return "id";
    }

    protected abstract T createByResultSet(ResultSet RS);

    /**
     * Cherche un élément par sa clé primaire.
     * Ne pas utiliser directement, car n'enregistre pas les résultat
     * @param pk
     * @return
     * @deprecated
     */
    @Deprecated
    public T find(int pk) {
        if (primaryKey().isEmpty()) {
            return null;
        }

        if (findStatement == null) {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM ");
            query.append(tableName());
            query.append(" WHERE ");
            query.append(primaryKey());
            query.append(" = ?");

            findStatement = Database.prepare(query.toString());
        }

        try {
            findStatement.setInt(1, pk);
            ResultSet RS = findStatement.executeQuery();

            if (!RS.next()) {
                return null;
            }

            return createByResultSet(RS);
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<T> getAll() {
        ArrayList<T> list = new ArrayList<>();

        ResultSet RS = Database.query("SELECT * FROM " + tableName());

        try {
            while (RS.next()) {
                list.add(createByResultSet(RS));
            }
        } catch (SQLException ex) {
        }

        return list;
    }

    public boolean delete(int pk) {
        try {
            if (primaryKey().isEmpty()) {
                return false;
            }

            if (deleteStatement == null) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ");
                query.append(tableName());
                query.append(" WHERE ");
                query.append(primaryKey());
                query.append(" = ?");

                deleteStatement = Database.prepare(query.toString());
            }

            deleteStatement.setInt(1, pk);
            return deleteStatement.execute();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(T obj) {
        if (delete(obj.getPk())) {
            obj.clear();
            return true;
        } else {
            return false;
        }
    }

    public abstract boolean update(T obj);

    public abstract boolean create(T obj);
}
