package game.objects;

import game.objects.dep.ItemStats;
import game.objects.dep.ClassData;
import game.objects.dep.Creature;
import game.objects.dep.GMable;
import game.objects.dep.Stats;
import game.objects.dep.Stats.Element;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import jelly.Loggin;
import jelly.Utils;
import models.Account;
import models.Character;
import models.InventoryEntry;
import models.MapModel;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.events.CharacterEvents;
import server.events.MapEvents;
import server.events.ObjectEvents;

public class Player extends Creature implements GMable {

    private Character _character;
    private byte classID;
    private byte sexe;
    private int id;
    private GameMap curMap;
    private GameMap.Cell curCell;
    private IoSession session = null;
    private String chanels = "*#$:?i^!%";
    private Account _account;
    public String restriction = "6bk";
    public byte orientation = 2;
    /**
     * Liste des items par id
     */
    private HashMap<Integer, GameItem> inventory = new HashMap<>();
    /**
     * Liste des items par itemStats
     */
    private HashMap<ItemStats, GameItem> itemsByStats = new HashMap<>();
    /**
     * équipements portés Position => Item
     */
    private HashMap<Byte, GameItem> wornItems = new HashMap<>();
    private Stats stuffStats;

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

        loadInventory();
        loadStats();
    }

    private void loadInventory() {
        for (InventoryEntry I : DAOFactory.inventory().getByPlayerId(id)) {
            GameItem GI = I.getGameItem();
            inventory.put(I.id, GI);
            itemsByStats.put(GI.getItemStats(), GI);

            if (I.position != -1) { //si équipé
                if (!GI.canMove(I.position)) { //si impossible à équiper on remet dans inventaire
                    I.position = -1;
                    DAOFactory.inventory().update(I);
                    continue;
                }
                if (!GI.isWearable()) {//si ce n'est pa un équipement
                    continue;
                }
                if (I.qu > 1) { //si + de 1
                    int qu = I.qu - 1;
                    /*GI.changeQuantity(1, false);
                     addItem(I.getItemStats(), qu); //on les remet dans l'inventaire "normal"*/
                    moveItem(I.id, qu, (byte) -1);
                }
                wornItems.put(I.position, GI);
            }
        }
    }

    /**
     * Ajoute un item à l'inventaire (position -1)
     *
     * @param item
     * @param qu
     */
    public void addItem(ItemStats item, int qu) {
        if (item == null || qu < 1) {
            return;
        }
        if (itemsByStats.containsKey(item)) { //item existe déjà, on augemente le nombre
            itemsByStats.get(item).addQuantity(qu, true);
        } else { //sinon, on crée les new objects
            GameItem GI = new GameItem(this, item, qu, (byte) -1);
            itemsByStats.put(item, GI);
            inventory.put(GI.getID(), GI);
        }
    }

    /**
     * Déplace un item (packet Object Move)
     *
     * @param id
     * @param qu
     * @param pos
     * @return
     */
    public boolean moveItem(int id, int qu, byte pos) {
        if (qu < 1) {
            return false;
        }
        if (!inventory.containsKey(id)) { //on n'a pas l'item dans son inventaire
            return false;
        }
        GameItem GI = inventory.get(id);
        InventoryEntry I = GI.getInventory();
        if (I.position == pos) {
            return false; //déplace au même endroit ?? (ne devrait pas arriver)
        }
        if (!GI.canMove(pos)) {
            return false;
        }
        if (qu > I.qu) { //peu pas déplacer plus que ce que l'on a
            qu = I.qu;
        }
        if (GI.isWearable() && qu > 1 && pos != -1) { //impossible d'équiper 2 fois le même item
            return false;
        }
        if (qu == I.qu) { //si même qu, on déplace tout
            return moveGameItem(GI, pos);
        }
        if (GI.isWearable() && pos != -1 && wornItems.containsKey(pos)) { //si un item déjà équipé
            if (wornItems.get(pos).getItemStats().equals(GI.getItemStats())) {
                return false; //sert à rien d'équiper 2 fois le même item
            }
            itemFreePlace(pos); //sinon libère le place
        }
        //sinon crée new GI
        GameItem nGI = new GameItem(this, GI.getItemStats(), qu, pos);
        inventory.put(nGI.getID(), nGI);
        itemsByStats.put(nGI.getItemStats(), nGI);
        GI.changeQuantity(I.qu - qu, true); //on change quantité
        if (nGI.isWearable() && pos != -1) {
            return equipGameItem(nGI, pos); //on équipe
        }
        return true;
    }

    /**
     * Déplace un GameItem (TOUT les items qu'il comporte)
     *
     * @param GI
     * @param pos
     * @return false en erreur, true sinon
     */
    public boolean moveGameItem(GameItem GI, byte pos) {
        if (GI == null) {
            return false;
        }
        if (!GI.canMove(pos)) {
            return false;
        }
        if (GI.isWearable()) { //si c'est un équipement
            if (pos != -1 && GI.getInventory().qu > 1) { //impossible d'équiper 2 fois le même item
                return false;
            } else if (pos == -1) { //on vire l'équipement
                unequip(GI.getInventory().position);
            } else if (pos != -1 && wornItems.containsKey(pos)) { //place déjà prise
                if (wornItems.get(pos).getItemStats().equals(GI.getItemStats())) {
                    return false; //même stats, pas besoin de changer
                }
                itemFreePlace(pos); //sinon on libère l'ancien item
            }
            if (pos != -1) { //on équipe
                if (!equipGameItem(GI, pos)) {
                    return false;
                }
            }
        }
        //vérification OK, on le déplace
        GameItem oGI = GI.clone(); //clone le GI pour ne pas le modifier
        ItemStats IS = GI.getItemStats();
        itemsByStats.remove(oGI.getItemStats());
        IS.setPosition(pos);
        if (itemsByStats.containsKey(IS)) { //si item déjà existant en inventaire
            int qu = oGI.getInventory().qu;
            deleteGameItem(oGI); //supprime l'ancien GI (évite duplications)
            itemsByStats.get(IS).addQuantity(qu, true); //on ajoute la quantité
            return true;
        }
        //sinon, simple transfert
        GI.move(pos);
        itemsByStats.put(IS, GI);
        return true;
    }

    /**
     * Supprime proprement le GameItem
     *
     * @param GI
     */
    private void deleteGameItem(GameItem GI) {
        Loggin.debug("supprime GI %d", GI.getID());
        inventory.remove(GI.getID());
        if (itemsByStats.get(GI.getItemStats()) == GI) {
            itemsByStats.remove(GI.getItemStats());
        }
        if (wornItems.get(GI.getInventory().position) == GI) { //si équipement porté
            unequip(GI.getInventory().position);
        }
        GI.delete();
    }

    /**
     * Equipe l'item /!\ Ne pas utiliser directement (ne libère pas la place)
     *
     * @param GI
     * @param pos
     * @return true si tout ce passe bien, false si déjà équipé item similaire
     */
    private boolean equipGameItem(GameItem GI, byte pos) {
        if (!GI.isWearable() || !GI.canMove(pos) || wornItems.containsKey(pos)) {
            return false;
        }
        wornItems.put(pos, GI);
        loadStuffStats();
        CharacterEvents.onStatsChange(session, this);
        ObjectEvents.onWeightChange(session, this);

        if (pos == GameItem.POS_ARME || pos == GameItem.POS_CAPE || pos == GameItem.POS_BOUCLIER || pos == GameItem.POS_FAMILIER || pos == GameItem.POS_COIFFE) {
            ObjectEvents.onAccessoriesChange(this);
        }
        return true;
    }

    /**
     * Enlève un équipement de la liste des équipements portés et recharge les
     * stats /!\ ne remet pas dans l'inventaire /!\
     *
     * @param pos
     */
    private void unequip(byte pos) {
        if (wornItems.remove(pos) == null) {
            return;
        }

        loadStuffStats();
        CharacterEvents.onStatsChange(session, this);
        ObjectEvents.onWeightChange(session, this);
        if (pos == GameItem.POS_ARME || pos == GameItem.POS_CAPE || pos == GameItem.POS_BOUCLIER || pos == GameItem.POS_FAMILIER || pos == GameItem.POS_COIFFE) {
            ObjectEvents.onAccessoriesChange(this);
        }
    }

    /**
     * Libère la place pour un autre item et remet dans l'inventaire
     *
     * @param pos
     */
    private void itemFreePlace(byte pos) {
        if (pos == -1 || !wornItems.containsKey(pos)) {
            return;
        }
        GameItem GI = wornItems.remove(pos);
        moveGameItem(GI, (byte) -1);
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
                    int qu = Integer.parseInt(arr[1]);
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
        for (GameItem GI : wornItems.values()) {
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

    public GameMap getMap() {
        return curMap;
    }

    public void setMap(GameMap map) {
        curMap = map;
        _character.lastMap = map.getID();
    }

    public GameMap.Cell getCell() {
        return curCell;
    }

    public void setCell(GameMap.Cell cell) {
        curCell = cell;
        _character.lastCell = cell.getID();
    }

    public byte getClassID() {
        return classID;
    }

    public byte getSexe() {
        return sexe;
    }

    public Character getCharacter() {
        return _character;
    }

    @Override
    public int getID() {
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

    public String getStatsPacket() {

        StringBuilder ASData = new StringBuilder();
        ASData.append("0,0").append("|");
        ASData.append(0).append("|").append(0).append("|").append(0).append("|");
        ASData.append(0).append("~").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append((false ? "1" : "0")).append("|");

        ASData.append(getPDVMax()).append(",").append(getPDVMax()).append("|");
        ASData.append(10000).append(",10000|");

        ASData.append(getInitiative()).append("|");
        ASData.append(getProspection()).append("|");
        ASData.append(baseStats.get(Element.PA)).append(",").append(stuffStats.get(Element.PA)).append(",").append(0).append(",").append(0).append(",").append(getTotalStats().get(Element.PA)).append("|");
        ASData.append(baseStats.get(Element.PM)).append(",").append(stuffStats.get(Element.PM)).append(",").append(0).append(",").append(0).append(",").append(getTotalStats().get(Element.PM)).append("|");
        ASData.append(baseStats.get(Element.FORCE)).append(",").append(stuffStats.get(Element.FORCE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.VITA)).append(",").append(stuffStats.get(Element.VITA)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.SAGESSE)).append(",").append(stuffStats.get(Element.SAGESSE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.CHANCE)).append(",").append(stuffStats.get(Element.CHANCE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.AGILITE)).append(",").append(stuffStats.get(Element.AGILITE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.INTEL)).append(",").append(stuffStats.get(Element.INTEL)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.PO)).append(",").append(stuffStats.get(Element.PO)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.INVOC)).append(",").append(stuffStats.get(Element.INVOC)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.DOMMAGE)).append(",").append(stuffStats.get(Element.DOMMAGE)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|"); //PDOM ?
        ASData.append("0,0,0,0|");//Maitrise ?
        ASData.append(baseStats.get(Element.PERDOM)).append(",").append(stuffStats.get(Element.PERDOM)).append("," + "0").append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.SOIN)).append(",").append(stuffStats.get(Element.SOIN)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.TRAP_DOM)).append(",").append(stuffStats.get(Element.TRAP_DOM)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.TRAP_PERDOM)).append(",").append(stuffStats.get(Element.TRAP_PERDOM)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|"); //?
        ASData.append(baseStats.get(Element.CC)).append(",").append(stuffStats.get(Element.CC)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.EC)).append(",").append(stuffStats.get(Element.EC)).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|");


        return ASData.toString();
    }

    @Override
    public String getGMData() {
        StringBuilder str = new StringBuilder();

        str.append(curCell.getID()).append(";").append(orientation).append(";");
        str.append("0").append(";");//FIXME:?
        str.append(id).append(";").append(name).append(";").append(classID);

        //30^100,1247;
        //FIXME pnj suiveur ? 
        str.append(",18").append(";"); //title
        str.append(gfxID).append("^").append(100) //gfxID^size //FIXME ,GFXID pnj suiveur
                //                .append(",").append("1247") // mob suvieur1
                //                .append(",").append("1503") //mob suiveur2
                //                .append(",").append("1451") //mob suiveur 3
                //                .append(",").append("1186") // mob suiveur 4
                //                .append(",").append("8013") // MS5
                //                .append(",").append("8018") // MS6
                //                .append(",").append("8017") // MS7 ... Infini quoi
                .append(";");
        str.append(sexe).append(";");
        str.append(0).append(","); //alignement
        str.append("0").append(",");//FIXME:?
        str.append((false ? 0 : "0")).append(","); //grade
        str.append(getLevel() + getID());
        if (false && 0 > 0) { //déshoneur
            str.append(",");
            str.append(0 > 0 ? 1 : 0).append(';');
        } else {
            str.append(";");
        }
        //str.append(_lvl).append(";");
        str.append(Utils.implode(";", colors)).append(";");
        str.append(getGMStuff()).append(";"); //stuff
         /*if (hasEquiped(10054) || hasEquiped(10055) || hasEquiped(10056) || hasEquiped(10058) || hasEquiped(10061) || hasEquiped(10102)) {
         str.append(3).append(";");
         } else {*/
        str.append((level > 99 ? (level > 199 ? (2) : (1)) : (0))).append(";");
        //}
        //str.append("0;");
        str.append(";");//Emote
        str.append(";");//Emote timer
        /*if (this._guildMember != null && this._guildMember.getGuild().getMembers().size() > 9)//>9TODO:
         {
         str.append(this._guildMember.getGuild().get_name()).append(";").append(this._guildMember.getGuild().get_emblem()).append(";");
         } else {
         str.append(";;");
         }*/
        str.append(";;");
        str.append(restriction).append(";");//Restriction
        //str.append((_onMount && _mount != null ? _mount.get_color(parsecolortomount()) : "")).append(";");
        str.append(";");
        str.append(";");

        return str.toString();
    }

    /**
     * retourne le stuff pour les packets d'affichage
     *
     * @return
     */
    public String getGMStuff() {
        StringBuilder s = new StringBuilder();

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
    public int getProspection() {
        int p = getTotalStats().get(Element.PROSPEC);
        p += Math.ceil(getTotalStats().get(Element.CHANCE) / 10);

        return p;
    }
    
    /**
     * Retourne le nombre de pdv max du perso
     * @return 
     */
    public int getPDVMax(){
        return (level - 1) * ClassData.VITA_PER_LVL + ClassData.BASE_VITA + getTotalStats().get(Element.VITA);
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
        for (GameItem GI : inventory.values()) {
            pods += GI.getPods();
        }
        return pods;
    }

    /**
     * Retourne tout les items du joueur
     *
     * @return
     */
    public Collection<GameItem> getInventory() {
        return inventory.values();
    }
    
    /**
     * Sauvegarde le personnage
     */
    public synchronized void save(){
        Loggin.debug("Sauvegarde de %s", _character.name);
        StringBuilder stats = new StringBuilder();
        
        for(Entry<Element, Integer> e : baseStats.getAll()){
            int val = e.getValue();
            if(val == 0){
                continue;
            }
            stats.append(e.getKey().getId(false)).append(';').append(val).append('|');
        }
        
        _character.baseStats = stats.toString();
        
        for(GameItem GI : inventory.values()){
            DAOFactory.inventory().update(GI.getInventory());
        }
        
        _character.orientation = orientation;
        
        DAOFactory.character().update(_character);
    }
}
