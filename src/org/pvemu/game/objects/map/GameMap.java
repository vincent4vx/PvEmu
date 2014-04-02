package org.pvemu.game.objects.map;

import org.pvemu.game.objects.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.MapModel;
import org.pvemu.models.dao.DAOFactory;

public final class GameMap {
    short id;
    final private MapModel model;
    final private List<MapCell> cells; //300 cells. devrait allez pour la plupart des maps
    final private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<Integer, GMable> gms = new ConcurrentHashMap<>();
    private int lastGMId = 0;

    public GameMap(short id, MapModel model, List<MapCell> cells) {
        this.id = id;
        this.model = model;
        this.cells = cells;
    } 

    /*public GameMap(MapModel model) {
        this.model = model;
        id = this.model.id;

        for (int f = 0; f < this.model.mapData.length(); f += 10) {
            String CellData = this.model.mapData.substring(f, f + 10);
            cells.add(new MapCell(this, (short) (f / 10), CellData));
        }
        
        this.model.mapData = null;

        for (Trigger T : DAOFactory.trigger().getByMapID(id)) {
            MapCell cell = getCellById(T.cellID);

            if (cell != null) {
                cell.addTrigger(T);
            }
        }
        
        for(MapNpcs MN : DAOFactory.mapNpcs().getByMapId(id)){
            lastGMId--;
            gms.put(lastGMId, new GameNpc(MN, lastGMId));
        }
    }*/
    
    public int getNextGmId(){
        return lastGMId - 1;
    }
    
    public void addGMable(GMable gm){
        gms.put(gm.getID(), gm);
        
        if(gm.getID() < lastGMId)
            lastGMId = gm.getID();
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

    public byte getWidth() {
        return model.width;
    }

    public byte getHeigth() {
        return model.heigth;
    }

    /**
     * Vérifie si la destination est valide ou non
     *
     * @param mapID
     * @param cellID
     * @return
     */
    public static boolean isValidDest(short mapID, short cellID) {
        //GameMap map = DAOFactory.map().getById(mapID).getGameMap();
        GameMap map = MapFactory.getById(mapID);

        if (map == null) { //map inexistante
            return false;
        }

        if (map.cells.size() < cellID) { //cellule inexistante
            return false;
        }

        return map.cells.get(cellID).isWalkable();
    }

    public short getID() {
        return id;
    }
}
