package com.oldofus.models.dao;

import com.oldofus.jelly.database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import com.oldofus.jelly.Loggin;
import com.oldofus.models.Character;

public class CharacterDAO extends com.oldofus.jelly.database.DAO<Character> {

    private PreparedStatement findByNameStatement = null;
    private PreparedStatement getByAccountId = null;
    private PreparedStatement createStatement = null;
    private PreparedStatement countNameStatement = null;
    private PreparedStatement countByAccountStatement = null;
    private PreparedStatement updateStatement = null;
    private ConcurrentHashMap<Integer, Character> charactersById = new ConcurrentHashMap<>();

    public CharacterDAO() {
        createStatement = Database.prepareInsert("INSERT INTO characters(name, class, sexe, color1, color2, color3, account, gfxid, lastMap, lastCell, startMap, startCell) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
        updateStatement = Database.prepare("UPDATE characters SET level = ?, gfxid = ?, lastMap = ?, lastCell = ?, startMap = ?, startCell = ?, baseStats = ?, orientation = ? WHERE id = ?");
        findByNameStatement = Database.prepare("SELECT * FROM characters WHERE name = ?");
        getByAccountId = Database.prepare("SELECT * FROM characters WHERE account = ?");
        countNameStatement = Database.prepare("SELECT COUNT(*) AS count FROM characters WHERE name = ?");
        countByAccountStatement = Database.prepare("SELECT COUNT(*) AS count FROM characters WHERE account = ?");
    }

    @Override
    protected String tableName() {
        return "characters";
    }

    @Override
    protected Character createByResultSet(ResultSet RS) {
        try {
            Character p = new Character();

            p.id = RS.getInt("id");
            p.name = RS.getString("name");
            p.accountId = RS.getInt("account");
            p.classId = RS.getByte("class");
            p.color1 = RS.getInt("color1");
            p.color2 = RS.getInt("color2");
            p.color3 = RS.getInt("color3");
            p.gfxid = RS.getShort("gfxid");
            p.sexe = RS.getByte("sexe");
            p.level = RS.getShort("level");
            p.lastMap = RS.getShort("lastMap");
            p.lastCell = RS.getShort("lastCell");
            p.baseStats = RS.getString("baseStats");
            p.orientation = RS.getByte("orientation");

            charactersById.put(p.id, p);

            return p;
        } catch (SQLException e) {
            Loggin.error("Impossible de charger le personnage !", e);
            return null;
        }
    }

    @Override
    public boolean create(Character p) {
        try {
            synchronized (createStatement) {
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

                if (RS.next()) {
                    id = RS.getInt(1);
                }

                if (id == 0) {
                    return false;
                } else {
                    p.id = id;
                }
            }
        } catch (SQLException ex) {
            Loggin.error("Création du personnage impossible !", ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Character P) {
        try {
            synchronized (updateStatement) {
                updateStatement.setInt(1, P.level);
                updateStatement.setInt(2, P.gfxid);
                updateStatement.setShort(3, P.lastMap);
                updateStatement.setShort(4, P.lastCell);
                updateStatement.setShort(5, P.startMap);
                updateStatement.setShort(6, P.startCell);
                updateStatement.setString(7, P.baseStats);
                updateStatement.setByte(8, P.orientation);

                updateStatement.setInt(9, P.id);

                updateStatement.execute();
            }

            return true;
        } catch (SQLException e) {
            Loggin.error("Impossible de sauvegarder le personnage " + P.name, e);
            return false;
        }
    }

    /**
     * Charge le perso par son nom
     *
     * @param name
     * @return
     */
    public Character findByName(String name) {
        try {
            synchronized (findByNameStatement) {
                findByNameStatement.setString(1, name);
                ResultSet RS = findByNameStatement.executeQuery();

                if (!RS.next()) {
                    return null;
                }

                return createByResultSet(RS);
            }
        } catch (SQLException ex) {
            Loggin.error("Impossible de trouver le personnage " + name, ex);
            return null;
        }
    }

    /**
     * Charge un perso par son ID
     *
     * @param id
     * @return
     */
    public Character getById(int id) {
        if (!charactersById.containsKey(id)) {
            return find(id);
        }
        return charactersById.get(id);
    }

    /**
     * Récupère les perso d'un compte
     *
     * @param accountId
     * @return
     */
    public HashMap<Integer, Character> getByAccountId(int accountId) {
        HashMap<Integer, Character> players = new HashMap<>();

        try {
            synchronized (getByAccountId) {
                getByAccountId.setInt(1, accountId);
                ResultSet RS = getByAccountId.executeQuery();

                while (RS.next()) {
                    Character c = createByResultSet(RS);
                    if (c != null) {
                        players.put(c.id, c);
                    }
                }
            }
        } catch (SQLException e) {
            Loggin.error("Impossible de charger la liste des personnages du compte " + accountId, e);
        }

        return players;
    }

    /**
     * Test si un nom existe en bdd
     *
     * @param name
     * @return
     */
    public boolean nameExists(String name) {
        try {
            synchronized (countNameStatement) {
                countNameStatement.setString(1, name);
                ResultSet RS = countNameStatement.executeQuery();
                if (!RS.next()) {
                    return true; //ne devrait pas arriver :/
                }

                return RS.getInt("count") > 0;
            }
        } catch (Exception e) {
            Loggin.error("Impossible de savoir l'existance du nom " + name, e);
            return true;
        }
    }

    /**
     * Compte le nombre de perso dans un compte
     *
     * @param accountId
     * @return
     */
    public int countByAccount(int accountId) {
        try {
            synchronized (countByAccountStatement) {
                countByAccountStatement.setInt(1, accountId);
                ResultSet RS = countByAccountStatement.executeQuery();
                if (!RS.next()) {
                    return 0;
                }
                return RS.getInt("count");
            }
        } catch (Exception e) {
            Loggin.error("Impossible de connaitre le nombre de personnages du compte " + accountId, e);
            return 0;
        }
    }
}
