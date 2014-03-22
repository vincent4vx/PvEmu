package org.pvemu.game.objects;

import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.GameActionHandler;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.dep.ClassData;
import org.pvemu.game.objects.dep.Creature;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.dep.Stats.Element;
import org.pvemu.game.objects.inventory.Inventory;
import org.pvemu.game.objects.inventory.Inventoryable;
import org.pvemu.game.objects.map.MapCell;
import java.util.Map.Entry;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.Account;
import org.pvemu.models.Character;
import org.pvemu.models.MapModel;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.Filterable;
import org.pvemu.network.generators.GeneratorsRegistry;


public class Player extends Creature implements GMable, Inventoryable, Filterable {

    final private Character character;
    private GameMap curMap;
    private MapCell curCell;
    private IoSession session = null;
    private String chanels = "*#$:?i^!%";
    final private Account account;
    public String restriction = "6bk";
    public Byte orientation = 2;
    final private Inventory inventory;
    private Stats stuffStats;
    final private GameActionHandler actions = new GameActionHandler();
    public NpcQuestion current_npc_question = null;
    private Exchange exchange = null;

    public Player(Character c) {
        character = c;
        gfxID = c.gfxid;
        level = c.level;
        name = c.name;
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

        account = DAOFactory.account().getById(character.accountId);

        inventory = new Inventory(this);
        loadStats();
    }

    /**
     * Charge les stats du perso
     */
    private void loadStats() {
        if (character.baseStats == null || character.baseStats.isEmpty()) {
            ClassData.setStartStats(this);
        } else {
            for (String data : character.baseStats.split("\\|")) {
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
    public void loadStuffStats() {
        stuffStats = new Stats();
        for (byte pos : inventory.getItemsByPos().keySet()) {
            if(!ItemPosition.getByPosID(pos).isWearPlace())
                continue;
            
            for(GameItem item : inventory.getItemsOnPos(pos)){
                stuffStats.addAll(item.getStats());
            }
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
        character.lastMap = map.getID();
    }

    public MapCell getCell() {
        return curCell;
    }

    public void setCell(MapCell cell) {
        curCell = cell;
        character.lastCell = cell.getID();
    }

    public Byte getClassID() {
        return character.classId;
    }

    public Byte getSexe() {
        return character.sexe;
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public Integer getID() {
        return character.id;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public Account getAccount() {
        return account;
    }

    /**
     * Génère et retourne le param du packet GM
     * @return param du packet GM en string
     * @deprecated Utiliser de préférence le generator correspondant (PlayerGenerator)
     */
    @Override
    @Deprecated
    public String getGMData() {
        return GeneratorsRegistry.getPlayer().generateGM(this);
    }

//    /**
//     * retourne le stuff pour les packets d'affichage
//     *
//     * @return
//     */
//    @Deprecated
//    public String getGMStuff() {
//        StringBuilder s = new StringBuilder();
//        HashMap<Byte, GameItem> wornItems = _inventory.getItemsByPos();
//
//        if (wornItems.containsKey(GameItem.POS_ARME)) {
//            s.append(Integer.toHexString(wornItems.get(GameItem.POS_ARME).getItemStats().getID()));
//        }
//        s.append(',');
//        if (wornItems.containsKey(GameItem.POS_COIFFE)) {
//            s.append(Integer.toHexString(wornItems.get(GameItem.POS_COIFFE).getItemStats().getID()));
//        }
//        s.append(',');
//        if (wornItems.containsKey(GameItem.POS_CAPE)) {
//            s.append(Integer.toHexString(wornItems.get(GameItem.POS_CAPE).getItemStats().getID()));
//        }
//        s.append(',');
//        if (wornItems.containsKey(GameItem.POS_FAMILIER)) {
//            s.append(Integer.toHexString(wornItems.get(GameItem.POS_FAMILIER).getItemStats().getID()));
//        }
//        s.append(',');
//        if (wornItems.containsKey(GameItem.POS_BOUCLIER)) {
//            s.append(wornItems.get(GameItem.POS_BOUCLIER).getItemStats().getID());
//        }
//
//        return s.toString();
//    }

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
        return chanels + (account.level > 0 ? "@¤" : "");
    }

    public void addChanel(char c) {
        chanels += c;
    }

    public void removeChanel(char c) {
        chanels = chanels.replace(String.valueOf(c), "");
    }
    
    /**
     * Sauvegarde la start map indiqué :
     * data = {mapID, cellID}
     * @param data 
     */
    public void setStartPos(short[] data){
        character.startMap = data[0];
        character.startCell = data[1];
        DAOFactory.character().update(character);
    }

    /**
     * Prépare la déconnexion
     */
    public void logout() {
        character.logout();
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
        for (GameItem item : inventory.getItems()) {
            pods += item.getPods();
        }
        return pods;
    }
    
    /**
     * Sauvegarde le personnage
     */
    public synchronized void save(){
        Loggin.debug("Sauvegarde de %s", character.name);
        StringBuilder stats = new StringBuilder();
        
        for(Entry<Element, Short> e : baseStats.getAll()){
            int val = e.getValue();
            if(val == 0){
                continue;
            }
            stats.append(e.getKey().getId(false)).append(';').append(val).append('|');
        }
        
        character.baseStats = stats.toString();
        inventory.save();
        
        character.orientation = orientation;
        
        DAOFactory.character().update(character);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public byte getOwnerType() {
        return 1;
    }

    /*@Override
    public void onQuantityChange(int id, int qu) {
        ObjectEvents.onQuantityChange(session, id, qu);
    }

    @Override
    public void onAddItem(OldGameItem GI) {
        ObjectEvents.onAdd(session, GI);
    }

    @Override
    public void onDeleteItem(int id) {
        ObjectEvents.onRemove(session, id);
    }

    @Override
    public void onMoveItemSuccess(OldGameItem GI, byte pos) {
        if(GI.isWearable()){
            loadStuffStats();
            CharacterEvents.onStatsChange(session, this);
            ObjectEvents.onWeightChange(session, this);
        }
        if(GI.isWearable() && (pos == OldGameItem.POS_ARME || pos == OldGameItem.POS_COIFFE || pos == OldGameItem.POS_CAPE || pos == OldGameItem.POS_FAMILIER || pos == -1)){
            //ObjectEvents.onAccessoriesChange(this);
            GameSendersRegistry.getObject().accessories(this);
        }
    }

    @Override
    public void onMoveItem(int id, byte pos) {
        ObjectEvents.onMove(session, id, pos);
    }*/
    
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
        return exchange;
    }
    
    public void startExchange(Player target){
        exchange = new Exchange(this, target);
    }
    
    /**
     * Arrête l'échange en cours
     */
    public void stopExchange(){
        if(exchange == null){
            return;
        }
        exchange = null;
    }

    @Override
    public boolean corresponds(Filter filter) {
        return filter.corresponds(this);
    }
}
