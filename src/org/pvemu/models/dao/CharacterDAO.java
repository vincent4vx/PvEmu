package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.jelly.database.UpdatableDAO;
import org.pvemu.models.Character;

public class CharacterDAO extends UpdatableDAO<Character> {

    final private Query findByName = DatabaseHandler.instance().prepareQuery("SELECT * FROM characters WHERE name = ?");
    final private Query getByAccountId = DatabaseHandler.instance().prepareQuery("SELECT * FROM characters WHERE account = ?");
    final private Query create = DatabaseHandler.instance().prepareInsert("INSERT INTO characters(name, class, sexe, color1, color2, color3, account, gfxid, lastMap, lastCell, startMap, startCell, currentVita, level, experience) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    final private Query countName = DatabaseHandler.instance().prepareQuery("SELECT COUNT(*) AS count FROM characters WHERE name = ?");
    final private Query countByAccount = DatabaseHandler.instance().prepareQuery("SELECT COUNT(*) AS count FROM characters WHERE account = ?");
    final private Query update = DatabaseHandler.instance().prepareQuery("UPDATE characters SET level = ?, experience = ?, gfxid = ?, lastMap = ?, lastCell = ?, startMap = ?, startCell = ?, baseStats = ?, orientation = ?, boostPoints = ?, spellPoints = ?, currentVita = ? WHERE id = ?");
    final private ConcurrentHashMap<Integer, Character> charactersById = new ConcurrentHashMap<>();

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
            p.experience = RS.getLong("experience");
            p.lastMap = RS.getShort("lastMap");
            p.lastCell = RS.getShort("lastCell");
            p.startMap = RS.getShort("startMap");
            p.startCell = RS.getShort("startCell");
            p.baseStats = RS.getString("baseStats");
            p.orientation = RS.getByte("orientation");
            p.spellPoints = RS.getInt("spellPoints");
            p.boostPoints = RS.getInt("boostPoints");
            p.kamas = RS.getInt("kamas");
            p.currentVita = RS.getShort("currentVita");

            charactersById.put(p.id, p);

            return p;
        } catch (SQLException e) {
            Loggin.error("Impossible de charger le personnage !", e);
            return null;
        }
    }

    @Override
    public boolean create(Character p) {
        ReservedQuery query = create.reserveQuery();
        try {
            query.getStatement().setString(1, p.name);
            query.getStatement().setShort(2, p.classId);
            query.getStatement().setShort(3, p.sexe);
            query.getStatement().setInt(4, p.color1);
            query.getStatement().setInt(5, p.color2);
            query.getStatement().setInt(6, p.color3);
            query.getStatement().setInt(7, p.accountId);
            query.getStatement().setInt(8, p.gfxid);
            query.getStatement().setShort(9, p.lastMap);
            query.getStatement().setShort(10, p.lastCell);
            query.getStatement().setShort(11, p.startMap);
            query.getStatement().setShort(12, p.startCell);
            query.getStatement().setInt(13, p.currentVita);
            query.getStatement().setShort(14, p.level);
            query.getStatement().setLong(15, p.experience);

            query.getStatement().executeUpdate();

            ResultSet RS = query.getStatement().getGeneratedKeys();

            int id = 0;
            
            if (RS.next()) {
                id = RS.getInt(1);
            }

            if (id == 0) {
                return false;
            } else {
                p.id = id;
            }
        } catch (SQLException ex) {
            Loggin.error("Cannot create character", ex);
            return false;
        }finally{
            query.release();
        }

        return true;
    }

    @Override
    public boolean update(Character P) {
        ReservedQuery query = update.reserveQuery();
        try {
            int i = 0;
            query.getStatement().setShort(++i, P.level);
            query.getStatement().setLong(++i, P.experience);
            query.getStatement().setInt(++i, P.gfxid);
            query.getStatement().setShort(++i, P.lastMap);
            query.getStatement().setShort(++i, P.lastCell);
            query.getStatement().setShort(++i, P.startMap);
            query.getStatement().setShort(++i, P.startCell);
            query.getStatement().setString(++i, P.baseStats);
            query.getStatement().setByte(++i, P.orientation);
            query.getStatement().setInt(++i, P.boostPoints);
            query.getStatement().setInt(++i, P.spellPoints);
            query.getStatement().setInt(++i, P.currentVita);

            query.getStatement().setInt(++i, P.id);

            return query.getStatement().execute();
        } catch (SQLException e) {
            Loggin.error("Cannot save character " + P, e);
            return false;
        }finally{
            query.release();
        }
    }

    /**
     * Charge le perso par son nom
     *
     * @param name
     * @return
     */
    public Character findByName(String name) {
        ReservedQuery query = findByName.reserveQuery();
        try {
            query.getStatement().setString(1, name);
            ResultSet RS = query.getStatement().executeQuery();

            if (!RS.next()) {
                return null;
            }

            return createByResultSet(RS);
        } catch (SQLException ex) {
            Loggin.error("Cannot load character " + name, ex);
            return null;
        }finally{
            query.release();
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

        ReservedQuery query = getByAccountId.reserveQuery();
        try {
            query.getStatement().setInt(1, accountId);
            ResultSet RS = query.getStatement().executeQuery();

            while (RS.next()) {
                Character c = createByResultSet(RS);
                if (c != null) {
                    players.put(c.id, c);
                }
            }
        } catch (SQLException e) {
            Loggin.error("Cannot load character list for account " + accountId, e);
        }finally{
            query.release();
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
        ReservedQuery query = countName.reserveQuery();
        try {
            query.getStatement().setString(1, name);
            ResultSet RS = query.getStatement().executeQuery();
            if (!RS.next()) {
                return true; //ne devrait pas arriver :/
            }

            return RS.getInt("count") > 0;
        } catch (SQLException e) {
            Loggin.error("Cannot test name existance for " + name, e);
            return true;
        }finally{
            query.release();
        }
    }

    /**
     * Compte le nombre de perso dans un compte
     *
     * @param accountId
     * @return
     */
    public int countByAccount(int accountId) {
        ReservedQuery query = countByAccount.reserveQuery();
        try {
            query.getStatement().setInt(1, accountId);
            ResultSet RS = query.getStatement().executeQuery();
            if (!RS.next()) {
                return 0;
            }
            return RS.getInt("count");
        } catch (SQLException e) {
            Loggin.error("Cannot count characters for account " + accountId, e);
            return 0;
        }finally{
            query.release();
        }
    }
}
