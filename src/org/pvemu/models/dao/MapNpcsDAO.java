package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DAO;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.models.MapNpcs;

public class MapNpcsDAO extends DAO<MapNpcs> {
    final private Query getByMapId = DatabaseHandler.instance().prepareQuery("SELECT * FROM map_npcs WHERE mapid = ?");

    @Override
    protected String tableName() {
        return "map_npcs";
    }

    @Override
    protected MapNpcs createByResultSet(ResultSet RS) {
        try {
            MapNpcs MN = new MapNpcs();
            
            MN.cellid = RS.getShort("cellid");
            MN.mapid = RS.getShort("mapid");
            MN.npcid = RS.getInt("npcid");
            MN.orientation = RS.getByte("orientation");
            
            return MN;
        } catch (SQLException ex) {
            Loggin.error("Impossible de charger le npc !", ex);
            return null;
        }
    }
    
    public ArrayList<MapNpcs> getByMapId(short mapID){
        ArrayList<MapNpcs> list = new ArrayList<>();
        
        ReservedQuery query = getByMapId.reserveQuery();
        try {
            query.getStatement().setShort(1, mapID);
            
            ResultSet RS = query.getStatement().executeQuery();
            
            while(RS.next()){
                MapNpcs MN = createByResultSet(RS);
                
                if(MN != null){
                    list.add(MN);
                }
            }
        } catch (SQLException ex) {
            Loggin.error("Cannot load npcs on map " + mapID, ex);
        }finally{
            query.release();
        }
        
        return list;
    }
    
    public Map<Short, List<MapNpcs>> getAll(){
        try{
            ResultSet RS = DatabaseHandler.instance().executeQuery("SELECT * FROM "  + tableName());
            Map<Short, List<MapNpcs>> npcs = new HashMap<>();
            
            while(RS.next()){
                MapNpcs npc = createByResultSet(RS);
                
                if(npc == null)
                    continue;
                
                List<MapNpcs> map = npcs.get(npc.mapid);
                
                if(map == null){
                    map = new ArrayList<>();
                    npcs.put(npc.mapid, map);
                }
                
                map.add(npc);
            }
            
            return npcs;
        }catch(SQLException e){
            Loggin.error("Cannot load npcs", e);
            return null;
        }
    }
}
