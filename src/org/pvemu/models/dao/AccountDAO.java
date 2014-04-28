package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.jelly.database.UpdatableDAO;
import org.pvemu.models.Account;

public class AccountDAO extends UpdatableDAO<Account> {

    private final Map<String, Account> accountsByName = new ConcurrentHashMap<>();
    private final Map<Integer, Account> accountsById = new ConcurrentHashMap<>();
    final private Query getByName = DatabaseHandler.instance().prepareQuery("SELECT * FROM accounts WHERE account = ?");

    @Override
    protected String tableName() {
        return "accounts";
    }

    @Override
    protected Account createByResultSet(ResultSet RS) {
        try {
            Account a = new Account();

            a.id = RS.getInt("id");
            a.account = RS.getString("account");
            a.level = RS.getByte("level");
            a.pass = RS.getString("pass");
            a.pseudo = RS.getString("pseudo");
            a.question = RS.getString("question");
            a.response = RS.getString("response");

            accountsByName.put(RS.getString("account").toLowerCase(), a);
            accountsById.put(RS.getInt("id"), a);

            return a;
        } catch (SQLException e) {
            Loggin.error("Impossible de charger le compte", e);
            return null;
        }
    }

    @Override
    public boolean update(Account obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Account obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Charge le compte suivant le username (bdd ou HashMap)
     *
     * @param name
     * @return
     */
    public Account getByName(String name) {
        if (!accountsByName.containsKey(name.toLowerCase())) {
            ReservedQuery query = getByName.reserveQuery();
            try {
                    query.getStatement().setString(1, name);
                    ResultSet RS = query.getStatement().executeQuery();
                    if (RS.next()) {
                        return createByResultSet(RS);
                    } else {
                        return null;
                    }
            }catch (SQLException ex) {
                Loggin.error("Cannot load account " + name, ex);
                return null;
            }finally{
                query.release();
            }
        }
        return accountsByName.get(name);
    }

    /**
     * Charge le compte suivent l'id (bdd ou HashMap)
     *
     * @param id
     * @return
     */
    public Account getById(int id) {
        if (!accountsById.containsKey(id)) {
            return find(id);
        }
        return accountsById.get(id);
    }
}
