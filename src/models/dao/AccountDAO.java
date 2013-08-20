package models.dao;

import java.sql.PreparedStatement;
import jelly.database.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.database.Database;
import models.Account;

public class AccountDAO extends DAO<Account> {
    private final Map<String, Account> accountsByName = new ConcurrentHashMap<>();
    private final Map<Integer, Account> accountsById = new ConcurrentHashMap<>();
    
    private PreparedStatement getByNameStatement = null;

    @Override
    protected String tableName() {
        return "accounts";
    }

    @Override
    protected Account createByResultSet(ResultSet RS) {
        try{
            Account a = new Account();
            
            a.id = RS.getInt("id");
            a.account = RS.getString("account");
            a.level = RS.getShort("level");
            a.pass = RS.getString("pass");
            a.pseudo = RS.getString("pseudo");
            a.question = RS.getString("question");
            a.response = RS.getString("response");
            
            accountsByName.put(RS.getString("account").toLowerCase(), a);
            accountsById.put(RS.getInt("id"), a);
            
            return a;
        }catch(SQLException e){
            e.printStackTrace();
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
     * @param name
     * @return 
     */
    public Account getByName(String name){
        if(!accountsByName.containsKey(name.toLowerCase())){
            if(getByNameStatement == null){
                getByNameStatement = Database.prepare("SELECT * FROM accounts WHERE account = ?");
            }
            try {
                getByNameStatement.setString(1, name);
                ResultSet RS = getByNameStatement.executeQuery();
                if(RS.next()){
                    return createByResultSet(RS);
                }else{
                    return null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return accountsByName.get(name);
    }
    
    /**
     * Charge le compte suivent l'id (bdd ou HashMap)
     * @param id
     * @return 
     */
    public Account getById(int id){
        if(!accountsById.containsKey(id)){
            return find(id);
        }
        return accountsById.get(id); 
    }
    
}
