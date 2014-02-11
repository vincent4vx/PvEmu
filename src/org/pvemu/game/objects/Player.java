package org.pvemu.game.objects;

import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.GameActionHandler;
import org.pvemu.game.objects.inventory.GameItem;
import org.pvemu.game.objects.dep.ClassData;
import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.dep.Stats.Element;
import org.pvemu.game.objects.inventory.Inventory;
import org.pvemu.game.objects.inventory.InventoryAble;
import org.pvemu.game.objects.map.MapCell;
import java.util.HashMap;
import java.util.Map.Entry;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.Account;
import org.pvemu.models.Character;
import org.pvemu.models.MapModel;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.events.CharacterEvents;
import org.pvemu.network.events.MapEvents;
import org.pvemu.network.events.ObjectEvents;
import org.pvemu.network.generators.GeneratorsFactory;
import org.pvemu.network.generators.PlayerGenerator;


public class Player extends Creature implements GMable, InventoryAble {

    private Character _character;
    private byte classID;
    private byte sexe;
    private int id;
    private GameMap curMap;
    private MapCell curCell;
    private IoSession session = null;
    private String chanels = "*#$:?i^!%";
    private Account _account;
    public String restriction = "6bk";
    public Byte orientation = 2;
    private Inventory _inventory;
    private Stats stuffStats;
    private GameActionHandler actions = new GameActionHandler();
    public NpcQuestion current_npc_question = null;
    private Exchange _exchange = null;

    public Player(Character c) {
        _character = c;
        gfxID = c.gfxid;
        level = c.level;
        name = c.name;
        classID = c.classId;
        sexe = c.sexe;
        id = c.id;
        orientation = c.orientation;

        colors[0] = c.color1 == -1 ? "-1" : Integer.toHexString(c.color1);
        colors[1] = c.color2 == -1 ? "-1" : Integer.toHexString(c.color2);
        colors[2] = c.color3 == -1 ? "-1" : Integer.toHexString(c.color3);

        MapModel m = DAOFactory.map().getById(c.lastMap);
        if (m != null) {
            curMap = m.getGameMap();
        }

        if (curMap != null) {
            curCell = curMap.getCellById(c.lastCell);
        }

        _account = DAOFactory.account().getById(_character.accountId);

        _inventory = new Inventory(this);
        loadStats();
    }

    /**
     * Charge les stats du perso
     */
    private void loadStats() {
        if (_character.baseStats == null || _character.baseStats.isEmpty()) {
            ClassData.setStartStats(this);
        } else {
            for (String data : _character.baseStats.split("\\|")) {
                try {
                    String[] arr = data.split(";");
                    int elemID = Integer.parseInt(arr[0]);
                    short qu = Short.parseShort(arr[1]);
                    baseStats.add(elemID, qu);
                } catch (Exception e) {
                }
            }
        }
        loadStuffStats();
    }

    /**
     * Charge les stats du stuff
     */
    private void loadStuffStats() {
        stuffStats = new Stats();
        for (GameItem GI : _inventory.getItemsByPos().values()) {
            if(GI.isWearable())
                stuffStats.addAll(GI.getItemStats().getStats());
        }
    }

    /**
     * Retourne toute les stats du perso
     *
     * @return
     */
    @Override
    public Stats getTotalStats() {
        Stats total = new Stats();
        return total.addAll(baseStats).addAll(stuffStats);
    }
    
    /**
     * Retourne les stats données uniquement par les équipements
     * @return 
     */
    public Stats getStuffStats(){
        return stuffStats;
    }

    public GameMap getMap() {
        return curMap;
    }

    public void setMap(GameMap map) {
        curMap = map;
        _character.lastMap = map.getID();
    }

    public MapCell getCell() {
        return curCell;
    }

    public void setCell(MapCell cell) {
        curCell = cell;
        _character.lastCell = cell.getID();
    }

    public Byte getClassID() {
        return classID;
    }

    public Byte getSexe() {
        return sexe;
    }

    public Character getCharacter() {
        return _character;
    }

    @Override
    public Integer getID() {
        return id;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public Account getAccount() {
        return _account;
    }

    /**
     * Génère et retourne le param du packet GM
     * @return param du packet GM en string
     * @deprecated Utiliser de préférence le generator correspondant (PlayerGenerator)
     */
    @Override
    @Deprecated
    public String getGMData() {
        return GeneratorsFactory.getPlayer().generateGM(this);
    }

    /**
     * retourne le stuff pour les packets d'affichage
     *
     * @return
     */
    public String getGMStuff() {
        StringBuilder s = new StringBuilder();
        HashMap<Byte, GameItem> wornItems = _inventory.getItemsByPos();

        if (wornItems.containsKey(GameItem.POS_ARME)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_ARME).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_COIFFE)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_COIFFE).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_CAPE)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_CAPE).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_FAMILIER)) {
            s.append(Integer.toHexString(wornItems.get(GameItem.POS_FAMILIER).getItemStats().getID()));
        }
        s.append(',');
        if (wornItems.containsKey(GameItem.POS_BOUCLIER)) {
            s.append(wornItems.get(GameItem.POS_BOUCLIER).getItemStats().getID());
        }

        return s.toString();
    }

    /**
     * Retourne la propection du joueur
     *
     * @return
     */
    public Short getProspection() {
        short p = getTotalStats().get(Element.PROSPEC);
        p += Math.ceil(getTotalStats().get(Element.CHANCE) / 10);

        return p;
    }
    
    /**
     * Retourne le nombre de pdv max du perso
     * @return 
     */
    public Short getPDVMax(){
        return (short)((level - 1) * ClassData.VITA_PER_LVL + ClassData.BASE_VITA + getTotalStats().get(Element.VITA));
    }

    /**
     * cannaux utilisés
     *
     * @return
     */
    public String getChanels() {
        return chanels + (_account.level > 0 ? "@¤" : "");
    }

    public void addChanel(char c) {
        chanels += c;
    }

    public void removeChanel(char c) {
        chanels = chanels.replace(String.valueOf(c), "");
    }

    /**
     * Téléporte le personnage
     *
     * @param mapID
     * @param cellID
     */
    public void teleport(short mapID, short cellID) {
        if (GameMap.isValidDest(mapID, cellID)) {
            MapEvents.onRemoveMap(session);
            MapEvents.onArrivedOnMap(session, mapID, cellID);
        }
    }
    
    /**
     * Sauvegarde la start map indiqué :
     * data = {mapID, cellID}
     * @param data 
     */
    public void setStartPos(short[] data){
        _character.startMap = data[0];
        _character.startCell = data[1];
        DAOFactory.character().update(_character);
    }

    /**
     * Prépare la déconnexion
     */
    public void logout() {
        _character.logout();
    }

    /**
     * Retourne ne nombre total de pods
     *
     * @return
     */
    public int getTotalPods() {
        int pods = getTotalStats().get(Element.PODS);
        pods += getTotalStats().get(Element.FORCE) * 5;

        return pods;
    }

    public int getUsedPods() {
        int pods = 0;
        for (GameItem GI : _inventory.getItems()) {
            pods += GI.getPods();
        }
        return pods;
    }
    
    /**
     * Sauvegarde le personnage
     */
    public synchronized void save(){
        Loggin.debug("Sauvegarde de %s", _character.name);
        StringBuilder stats = new StringBuilder();
        
        for(Entry<Element, Short> e : baseStats.getAll()){
            int val = e.getValue();
            if(val == 0){
                continue;
            }
            stats.append(e.getKey().getId(false)).append(';').append(val).append('|');
        }
        
        _character.baseStats = stats.toString();
        _inventory.save();
        
        _character.orientation = orientation;
        
        DAOFactory.character().update(_character);
    }

    @Override
    public Inventory getInventory() {
        return _inventory;
    }

    @Override
    public byte getOwnerType() {
        return 1;
    }

    @Override
    public void onQuantityChange(int id, int qu) {
        ObjectEvents.onQuantityChange(session, id, qu);
    }

    @Override
    public void onAddItem(GameItem GI) {
        ObjectEvents.onAdd(session, GI);
    }

    @Override
    public void onDeleteItem(int id) {
        ObjectEvents.onRemove(session, id);
    }

    @Override
    public void onMoveItemSuccess(GameItem GI, byte pos) {
        if(GI.isWearable()){
            loadStuffStats();
            CharacterEvents.onStatsChange(session, this);
            ObjectEvents.onWeightChange(session, this);
        }
        if(GI.isWearable() && (pos == GameItem.POS_ARME || pos == GameItem.POS_COIFFE || pos == GameItem.POS_CAPE || pos == GameItem.POS_FAMILIER || pos == -1)){
            ObjectEvents.onAccessoriesChange(this);
        }
    }

    @Override
    public void onMoveItem(int id, byte pos) {
        ObjectEvents.onMove(session, id, pos);
    }

    @Override
    public boolean canMoveItem(GameItem GI, int qu, byte pos) {
        
        return true;
    }
    
    /**
     * Retourne le GameActionHandler du joueur
     * @return 
     */
    public GameActionHandler getActions(){
        return actions;
    }
    
    /**
     * Retourne l'échange en cours (si il existe)
     * @return 
     */
    public Exchange getExchange(){
        return _exchange;
    }
    
    public void startExchange(Player target){
        _exchange = new Exchange(this, target);
    }
    
    /**
     * Arrête l'échange en cours
     */
    public void stopExchange(){
        if(_exchange == null){
            return;
        }
        _exchange = null;
    }
}
