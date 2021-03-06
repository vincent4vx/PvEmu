package org.pvemu.game.objects.map;

import org.pvemu.game.objects.player.Player;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.objects.monster.MonsterTemplate;
import org.pvemu.common.Loggin;
import org.pvemu.models.MapModel;
import org.pvemu.network.game.output.GameSendersRegistry;

public final class GameMap implements Environment{
    short id;
    final private MapModel model;
    final private List<MapCell> cells;
    final private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<Integer, GMable> gms = new ConcurrentHashMap<>();
    final private List<MonsterTemplate> availableMonsters;
    final private Map<Integer, MonsterGroup> monsterGroups = new ConcurrentHashMap<>();
    final private Map<Short, MonsterGroup> monsterGroupsPosition = new ConcurrentHashMap<>();
    final private Map<Integer, Fight> fights = new ConcurrentHashMap<>();
    final private List<Short>[] places;
    private int lastGMId = 0;

    GameMap(short id, MapModel model, List<MapCell> cells, List<MonsterTemplate> availableMonsters, List<Short>[] places) {
        this.id = id;
        this.model = model;
        this.cells = cells;
        this.places = places;
        this.availableMonsters = availableMonsters;
        refreshMobs();
        MapTimers.registerMonstersRespawnTimer(this);
    }
    
    /**
     * Get a new and free GMId
     * @return 
     */
    public int getNextGmId(){
        return lastGMId - 1;
    }
    
    /**
     * add a new GMable into map
     * @param gm 
     */
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
        monsterGroupsPosition.put(group.getCellId(), group);
    }
    
    public void removeMonsterGroup(MonsterGroup group){
        removeGMable(group);
        monsterGroups.remove(group.getID());
        monsterGroupsPosition.remove(group.getCellId());
    }
    
    public MonsterGroup getMonsterGroupByCell(short cell){
        return monsterGroupsPosition.get(cell);
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
    }

    /**
     * Supprime un joueur de la map
     *
     * @param player
     */
    public void removePlayer(Player player) {
        players.remove(player.getID());
        gms.remove(player.getID());
    }
    
    public void removeGMable(GMable gmable){
        gms.remove(gmable.getID());
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
        if (id < 0 || cells.size() < id) {
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
    
    /**
     * Refresh (fill) the monsters on map
     */
    public void refreshMobs(){
        if(monsterGroups.size() >= model.numgroup || availableMonsters.isEmpty())
            return;
        
        while(monsterGroups.size() < model.numgroup){
            addMonsterGroup(MonsterFactory.generateMonsterGroup(availableMonsters, this));
        }
    }
    
    /**
     * Get the current number of monster groups
     * @return 
     */
    public int getCurrentGroupCount(){
        return monsterGroups.size();
    }

    /**
     * Get the list of groups
     * @return 
     */
    public List<MonsterTemplate> getAvailableMonsters() {
        return availableMonsters;
    }
    
    /**
     * Get a new fight id
     * @return 
     */
    public int getFreeFightId(){
        int fightID = 0;
        
        while(fights.containsKey(fightID)){
            ++fightID;
        }
        
        return fightID;
    }
    
    /**
     * add a new fight into map
     * @param fight 
     */
    public void addFight(Fight fight){
        fights.put(fight.getId(), fight);
        GameSendersRegistry.getMap().fightCountToMap(this);
    }
    
    /**
     * Get the number of fight
     * @return 
     * @see org.pvemu.network.game.output.MapSender#fightCount(org.pvemu.game.objects.map.GameMap, org.apache.mina.core.session.IoSession) 
     */
    public int getFightCount(){
        return fights.size();
    }
    
    /**
     * Get fight by its id
     * @param fightID
     * @return 
     */
    public Fight getFight(int fightID){
        return fights.get(fightID);
    }
    
    /**
     * Get list of fights
     * @return 
     */
    public Collection<Fight> getFights(){
        return fights.values();
    }
    
    /**
     * remove a fight from map
     * @param fight 
     */
    public void removeFight(Fight fight){
        fights.remove(fight.getId());
        GameSendersRegistry.getMap().fightCountToMap(this);
    }

    /**
     * Gte fight places
     * @return 
     */
    public List<Short>[] getPlaces() {
        return places;
    }
    
    /**
     * Test if can start a fight
     * @return 
     */
    public boolean canFight(){
        if(places.length < 2)
            return false;
        
        for(List p : places){
            if(p.isEmpty())
                return false;
        }
        
        return true;
    }

    @Override
    public boolean canWalk(short cell) {
        MapCell c = getCellById(cell);
        
        return c != null && c.isWalkable();
    }

    @Override
    public short size() {
        return (short)cells.size();
    }
}
