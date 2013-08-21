package models.dao;

import jelly.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.Loggin;
import models.Character;

public class CharacterDAO extends jelly.database.DAO<Character> {
    private PreparedStatement findByNameStatement = null;
    private PreparedStatement getByAccountId = null;
    private PreparedStatement createStatement = null;
    private PreparedStatement countNameStatement = null;
    private PreparedStatement countByAccountStatement = null;
    private PreparedStatement updateStatement = null;
    
    private ConcurrentHashMap<Integer, Character> charactersById = new ConcurrentHashMap<>();

    @Override
    protected String tableName(){
        return "characters";
    }

    @Override
    protected Character createByResultSet(ResultSet RS){
        try{
            Character p = new Character();

            p.id = RS.getInt("id");
            p.name = RS.getString("name");
            p.accountId = RS.getInt("account");
            p.classId = RS.getByte("class");
            p.color1 = RS.getInt("color1");
            p.color2 = RS.getInt("color2");
            p.color3 = RS.getInt("color3");
            p.gfxid = RS.getInt("gfxid");
            p.sexe = RS.getByte("sexe");
            p.level = RS.getInt("level");
            p.lastMap = RS.getShort("lastMap");
            p.lastCell = RS.getShort("lastCell");
            
            charactersById.put(p.id, p);

            return p;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean create(Character p){
        if(createStatement == null){
            createStatement = Database.prepareInsert("INSERT INTO characters(name, class, sexe, color1, color2, color3, account, gfxid, lastMap, lastCell, startMap, startCell) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
        }
        try {
            createStatement.setString(1, p.name);
            createStatement.setShort(2, p.classId);
            createStatement.setShort(3, p.sexe);
            createStatement.setInt(4, p.color1);
            createStatement.setInt(5, p.color2);
            createStatement.setInt(6, p.color3);
            createStatement.setInt(7, p.accountId);
            createStatement.setInt(8, p.gfxid);
            createStatement.setShort(9, p.lastMap);
            createStatement.setShort(10, p.lastCell);
            createStatement.setShort(11, p.startMap);
            createStatement.setShort(12, p.startCell);
            
            int id = createStatement.executeUpdate();
            
            ResultSet RS = createStatement.getGeneratedKeys();
            
            if(RS.next()){
                id = RS.getInt(1);
            }
            
            if(id == 0){
                return false;
            }else{
                p.id = id;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, "Enregistrement impossible !", ex);
            return false;
        }
        
        return true;
    }

    @Override
    public boolean update(Character P){
        try{
            Loggin.debug("Sauvegarde de %s", P.name);
            if(updateStatement == null){
                updateStatement = Database.prepare("UPDATE characters SET level = ?, gfxid = ?, lastMap = ?, lastCell = ?, startMap = ?, startCell = ? WHERE id = ?");
            }
            
            updateStatement.setInt(1, P.level);
            updateStatement.setInt(2, P.gfxid);
            updateStatement.setShort(3 , P.lastMap);
            updateStatement.setShort(4, P.lastCell);
            updateStatement.setShort(5, P.startMap);
            updateStatement.setShort(6, P.startCell);
            updateStatement.setInt(7, P.id);
            
            updateStatement.execute();
            
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Charge le perso par son nom
     * @param name
     * @return 
     */
    public Character findByName(String name){
        try {
            if(findByNameStatement == null){
                findByNameStatement = Database.prepare("SELECT * FROM characters WHERE name = ?");
            }

            findByNameStatement.setString(1, name);
            ResultSet RS = findByNameStatement.executeQuery();

            if(!RS.next()){
                return null;
            }

            return createByResultSet(RS);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    /**
     * Charge un perso par son ID
     * @param id
     * @return 
     */
    public Character getById(int id){
        if(!charactersById.containsKey(id)){
            return find(id);
        }
        return charactersById.get(id);
    }
    
    /**
     * Récupère les perso d'un compte
     * @param accountId
     * @return 
     */
    public ArrayList<Character> getByAccountId(int accountId){
        ArrayList<Character> players = new ArrayList<>();
        
        try{
            if(getByAccountId == null){
                getByAccountId = Database.prepare("SELECT * FROM characters WHERE account = ?");
            }
            
            getByAccountId.setInt(1, accountId);
            ResultSet RS = getByAccountId.executeQuery();
            
            while(RS.next()){
                Character c = createByResultSet(RS);
                if(c != null)
                    players.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return players;
    }
    
    /**
     * Test si un nom existe en bdd
     * @param name
     * @return 
     */
    public boolean nameExists(String name){
        try{
            if(countNameStatement == null){
                countNameStatement = Database.prepare("SELECT COUNT(*) AS count FROM characters WHERE name = ?");
            }
            
            countNameStatement.setString(1, name);
            ResultSet RS = countNameStatement.executeQuery();
            if(!RS.next()){
                return true; //ne devrait pas arriver :/
            }
            
            return RS.getInt("count") > 0;
        }catch(Exception e){
            return true;
        }
    }
    
    /**
     * Compte le nombre de perso dans un compte
     * @param accountId
     * @return 
     */
    public int countByAccount(int accountId){
        try{
            if(countByAccountStatement == null){
                countByAccountStatement = Database.prepare("SELECT COUNT(*) AS count FROM characters WHERE account = ?");
            }
            
            countByAccountStatement.setInt(1, accountId);
            ResultSet RS = countByAccountStatement.executeQuery();
            if(!RS.next()){
                return 0;
            }
            return RS.getInt("count");
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
