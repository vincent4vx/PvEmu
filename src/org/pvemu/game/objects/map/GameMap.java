package org.pvemu.game.objects.map;

import org.pvemu.game.objects.player.Player;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.objects.monster.MonsterTemplate;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.MapModel;

public final class GameMap {
    short id;
    final private MapModel model;
    final private List<MapCell> cells;
    final private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<Integer, GMable> gms = new ConcurrentHashMap<>();
    final private List<MonsterTemplate> availableMonsters;
    final private Map<Integer, MonsterGroup> monsterGroups = new ConcurrentHashMap<>();
    private int lastGMId = 0;

    GameMap(short id, MapModel model, List<MapCell> cells, List<MonsterTemplate> availableMonsters) {
        this.id = id;
        this.model = model;
        this.cells = cells;
        this.availableMonsters = availableMonsters;
        refreshMobs();
    }
    
    public int getNextGmId(){
        return lastGMId - 1;
    }
    
    public void addGMable(GMable gm){
        gms.put(gm.getID(), gm);
        
        if(gm.getID() < lastGMId)
            lastGMId = gm.getID();
    }
    
    public void addMonsterGroup(MonsterGroup group){
        if(group == null)
            return;
        
        addGMable(group);
        monsterGroups.put(group.getID(), group);
    }

    /**
     * Ajoute un joueur à la map
     *
     * @param p
     * @param cellID
     */
    public void addPlayer(Player p, short cellID) {
        players.put(p.getID(), p);
        gms.put(p.getID(), p);
        getCellById(cellID).addPlayer(p);
    }

    /**
     * Supprime un joueur de la map
     *
     * @param p
     */
    public void removePlayer(Player p) {
        players.remove(p.getID());
        gms.remove(p.getID());
        if (p.getCell() != null) {
            p.getCell().removePlayer(p.getID());
        }
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }
    
    /**
     * Retourne le liste des GMables (pour envoyer packet GM par exmple)
     * @return 
     */
    public Collection<GMable> getGMables(){
        return gms.values();
    }

    public MapModel getModel() {
        return model;
    }
    
    /**
     * Retourn le GMable d'id indiqué
     * @param id
     * @return 
     */
    public GMable getGMable(int id){
        return gms.get(id);
    }

    /**
     * Retourne la cellule par son ID, si elle existe
     *
     * @param id
     * @return
     */
    public MapCell getCellById(short id) {
        if (cells.size() < id) {
            Loggin.debug("CellID invalide : %d, max : %d", id, cells.size());
            return null;
        }

        return cells.get(id);
    }

    List<MapCell> getCells() {
        return cells;
    }

    public byte getWidth() {
        return model.width;
    }

    public byte getHeigth() {
        return model.heigth;
    }

    public short getID() {
        return id;
    }
    
    public void refreshMobs(){
        if(monsterGroups.size() >= model.numgroup || availableMonsters.isEmpty())
            return;
        
        while(monsterGroups.size() < model.numgroup){
            addMonsterGroup(MonsterFactory.generateMonsterGroup(availableMonsters, this));
        }
    }
}
