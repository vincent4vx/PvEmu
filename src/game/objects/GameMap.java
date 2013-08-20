package game.objects;

import game.ActionsHandler.Action;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import jelly.Loggin;
import models.MapModel;
import models.Trigger;
import models.dao.DAOFactory;

public class GameMap {

    public static class Cell {

        protected GameMap _map;
        protected boolean walkable;
        protected int obj;
        protected boolean canSight = true;
        protected ConcurrentHashMap<Integer, Player> _players = new ConcurrentHashMap<>();
        protected int id;
        protected ArrayList<Action> _actions = new ArrayList<>();

        public Cell(GameMap map, int cellID, String CellData) {
            _map = map;
            id = cellID;
            
            walkable = (((parseHashChar(CellData.charAt(2))) & 56) >> 3) != 0;
            canSight = ((parseHashChar(CellData.charAt(0))) & 1) != 0;
            int layerObject2 = (((parseHashChar(CellData.charAt(0))) & 2) << 12) + (((parseHashChar(CellData.charAt(7))) & 1) << 12) + ((parseHashChar(CellData.charAt(8))) << 6) + parseHashChar(CellData.charAt(9));
            boolean layerObject2Interactive = (((parseHashChar(CellData.charAt(7))) & 2) >> 1) != 0;
            obj = (layerObject2Interactive ? layerObject2 : -1);  
        }

        private static byte parseHashChar(char c) {
            char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
            };

            int count = HASH.length;

            for (byte i = 0; i < count; i++) {
                if (HASH[i] == c) {
                    return i;
                }
            }

            return -1;
        }
        
        public int getID(){
            return id;
        }
        
        public boolean isWalkable(){
            return walkable;
        }
        
        public void removePlayer(int id){
            _players.remove(id);
        }
        
        public void addPlayer(Player p){
            _players.put(p.getID(), p);
        }
        
        /**
         * Ajoute une action sur la cellule
         * @param T
         */
        public void addTrigger(Trigger T){
            Action a = new Action(T.actionID, T.actionArgs.split(","), T.conditions);
            _actions.add(a);
        }
        
        /**
         * Effectue les actions sur la cellule (triggers)
         * @param p 
         */
        public void performCellAction(Player p){
            for(Action a : _actions){
                Loggin.debug("[%s] Déclanchement du trigger en [%d;%d] : actionID = %d", new Object[]{p.getName(), _map.id, id, a.actionID});
                a.performAction(p);
            }
        }
    }
    private MapModel _model;
    private ArrayList<Cell> _cells = new ArrayList<>(150); //300 cells. devrait allez pour la plupart des maps
    private int id;
    private ConcurrentHashMap<Integer, Player> _players = new ConcurrentHashMap<>();
    private String mapDataPacket = null;

    public GameMap(MapModel model) {
        _model = model;
        id = _model.id;
        
        for (int f = 0; f < _model.mapData.length(); f += 10) {
            String CellData = _model.mapData.substring(f, f + 10);
            _cells.add(new Cell(this, f / 10, CellData));
        }
        
        for(Trigger T : DAOFactory.trigger().getByMapID(id)){
            Cell cell = getCellById(T.cellID);
            
            if(cell != null){
                cell.addTrigger(T);
            }
        }
    }
    
    /**
     * Ajoute un joueur à la map
     * @param p
     * @param cellID 
     */
    public void addPlayer(Player p, int cellID){
        _players.put(p.getID(), p);
        getCellById(cellID)._players.put(p.getID(), p);
    }
    
    /**
     * Supprime un joueur de la map
     * @param p
     * @param cellID 
     */
    public void removePlayer(Player p, int cellID){
        _players.remove(p.getID());
        getCellById(cellID)._players.remove(p.getID());
    }
    
    public ConcurrentHashMap<Integer, Player> getPlayers(){
        return _players;
    }
    
    /**
     * Retourne la cellule par son ID, si elle existe
     * @param id
     * @return 
     */
    public Cell getCellById(int id){
        if(_cells.size() - 1 < id){
            return null;
        }
        
        return _cells.get(id);
    }
    
    public byte getWidth(){
        return _model.width;
    }
    
    public byte getHeigth(){
        return _model.heigth;
    }

    /**
     * Packet pour charger la map
     * @return 
     */
    public String getMapDataPacket(){
        if(mapDataPacket == null){
            StringBuilder p = new StringBuilder();
            p.append(id).append("|").append(_model.date).append("|").append(_model.key);
            mapDataPacket = p.toString();
        }
        return mapDataPacket;
    }
}
