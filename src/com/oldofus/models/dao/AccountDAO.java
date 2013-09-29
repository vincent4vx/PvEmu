package com.oldofus.models.dao;

import java.sql.PreparedStatement;
import com.oldofus.jelly.database.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.oldofus.jelly.Loggin;
import com.oldofus.jelly.database.Database;
import com.oldofus.models.Account;

public class AccountDAO extends DAO<Account> {

    private final Map<String, Account> accountsByName = new ConcurrentHashMap<>();
    private final Map<Integer, Account> accountsById = new ConcurrentHashMap<>();
    private PreparedStatement getByNameStatement = null;

    public AccountDAO() {
        getByNameStatement = Database.prepare("SELECT * FROM accounts WHERE account = ?");
    }

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
            try {
                synchronized (getByNameStatement) {
                    getByNameStatement.setString(1, name);
                    ResultSet RS = getByNameStatement.executeQuery();
                    if (RS.next()) {
                        return createByResultSet(RS);
                    } else {
                        return null;
                    }
                }
            } catch (SQLException ex) {
                Loggin.error("Impossible de charge le compte " + name, ex);
                return null;
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
