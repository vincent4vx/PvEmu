package server.events;

import game.objects.GameMap;
import game.objects.Player;
import jelly.Loggin;
import jelly.Utils;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class MapEvents {

    public static void onAddMap(IoSession session) {
        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            return;
        }

        StringBuilder str = new StringBuilder();

        str.append(p.curCell.getID()).append(";").append(0).append(";");
        str.append("0").append(";");//FIXME:?
        str.append(p.getID()).append(";").append(p.getName()).append(";").append(p.getClassID());

        //30^100,1247;
        //FIXME pnj suiveur ? 
        str.append("").append(";"); //title
        str.append(p.getGfxID()).append("^").append(100) //gfxID^size //FIXME ,GFXID pnj suiveur
                .append(",").append("")
                //.append(",").append("1247") // mob suvieur1
                //.append(",").append("1503") //mob suiveur2
                //.append(",").append("1451") //mob suiveur 3
                //.append(",").append("1186") // mob suiveur 4
                //.append(",").append("8013") // MS5
                //.append(",").append("8018") // MS6
                //.append(",").append("8017") // MS7 ... Infini quoi
                .append(";");
        str.append(p.getSexe()).append(";");
        str.append(0).append(","); //alignement
        str.append("0").append(",");//FIXME:?
        str.append((false ? 0 : "0")).append(","); //grade
        str.append(p.getLevel() + p.getID());
        if (false && 0 > 0) { //déshoneur
            str.append(",");
            str.append(0 > 0 ? 1 : 0).append(';');
        } else {
            str.append(";");
        }
        //str.append(_lvl).append(";");
        str.append(Utils.implode(";", p.getColors())).append(";");
        str.append("").append(";"); //stuff
        /*if (Ancestra.AURA_SYSTEM) {
            if (hasEquiped(10054) || hasEquiped(10055) || hasEquiped(10056) || hasEquiped(10058) || hasEquiped(10061) || hasEquiped(10102)) {
                str.append(3).append(";");
            } else {
                str.append((_lvl > 99 ? (_lvl > 199 ? (2) : (1)) : (0))).append(";");
            }
        } else {
            str.append("0;");
        }*/
        str.append("0;");
        str.append(";");//Emote
        str.append(";");//Emote timer
        /*if (this._guildMember != null && this._guildMember.getGuild().getMembers().size() > 9)//>9TODO:
        {
            str.append(this._guildMember.getGuild().get_name()).append(";").append(this._guildMember.getGuild().get_emblem()).append(";");
        } else {
            str.append(";;");
        }*/
        str.append(";;");
        str.append(0).append(";");//Restriction
        //str.append((_onMount && _mount != null ? _mount.get_color(parsecolortomount()) : "")).append(";");
        str.append(";");
        str.append(";");
        
        for (Player P : p.curMap.getPlayers().values()) {
            if (P.getSession() != null) {
                GamePacketEnum.MAP_ADD_PLAYER.send(P.getSession(), str.toString());
            }
        }
    }
    
    public static void onArrivedOnMap(IoSession session, int mapID, int cellID){   
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            session.close(true);
            return;
        }
        
        try{
            p.curMap = DAOFactory.map().getById(mapID).getGameMap();
            p.curCell = p.curMap.getCellById(cellID);
        }catch(NullPointerException e){
            session.close(true);
            return;
        }
        
        if(p.curCell == null){
            session.close(true);
            return;
        }
        
        p.curMap.addPlayer(p, cellID);
        
        GamePacketEnum.MAP_DATA.send(session, p.curMap.getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }
    
    public static void onArrivedInGame(IoSession session){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            session.close(true);
            return;
        }
        
        p.curMap.addPlayer(p, p.curCell.getID());
           
        GamePacketEnum.MAP_DATA.send(session, p.curMap.getMapDataPacket());
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }
    
    public static void onInitialize(IoSession session){
        onAddMap(session);
        GamePacketEnum.MAP_LOADED.send(session);
    }
    
    public static void onArrivedOnCell(IoSession session, int cellID){
        Player p = (Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        p.curCell.removePlayer(p.getID());
        p.curCell = p.curMap.getCellById(cellID);
        p.curCell.addPlayer(p);
        
        Loggin.debug("Joueur %s arrivé sur la cellule %d avec succès !", new Object[]{p.getName(), cellID});
    }
}
