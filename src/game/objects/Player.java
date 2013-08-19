package game.objects;

import game.objects.dep.Creature;
import game.objects.dep.Stats.Element;
import models.Character;
import models.MapModel;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;

public class Player extends Creature {

    private Character _character;
    private byte classID;
    private byte sexe;
    private int id;
    public GameMap curMap;
    public GameMap.Cell curCell;
    private IoSession session = null;
    private String chanels = "*#$:?";

    public Player(Character c) {
        _character = c;
        gfxID = c.gfxid;
        level = c.level;
        name = c.name;
        classID = c.classId;
        sexe = c.sexe;
        id = c.id;

        colors[0] = c.color1 == -1 ? "-1" : Integer.toHexString(c.color1);
        colors[1] = c.color2 == -1 ? "-1" : Integer.toHexString(c.color2);
        colors[2] = c.color3 == -1 ? "-1" : Integer.toHexString(c.color3);
        
        MapModel m = DAOFactory.map().getById(c.lastMap);
        if(m != null){
            curMap = m.getGameMap();
        }
        
        if(curMap != null){
            curCell = curMap.getCellById(c.lastCell);
        }
    }
    
    public byte getClassID(){
        return classID;
    }
    
    public byte getSexe(){
        return sexe;
    }
    
    public Character getCharacter(){
        return _character;
    }
    
    public int getID(){
        return id;
    }
    
    public IoSession getSession(){
        return session;
    }
    
    public void setSession(IoSession session){
        this.session = session;
    }
    
    public String getStatsPacket() {

        StringBuilder ASData = new StringBuilder();
        ASData.append("0,0").append("|");
        ASData.append(0).append("|").append(0).append("|").append(0).append("|");
        ASData.append(0).append("~").append(0).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(0 + ",").append((false ? "1" : "0")).append("|");
        int pdv = 100;
        int pdvMax = 100;

        ASData.append(pdv).append(",").append(pdvMax).append("|");
        ASData.append(10000).append(",10000|");

        ASData.append(getInitiative()).append("|");
        ASData.append(getProspection()).append("|");
        ASData.append(baseStats.get(Element.PA)).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(getTotalStats().get(Element.PA)).append("|");
        ASData.append(baseStats.get(Element.PM)).append(",").append(0).append(",").append(0).append(",").append(0).append(",").append(getTotalStats().get(Element.PM)).append("|");
        ASData.append(baseStats.get(Element.FORCE)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.VITA)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.SAGESSE)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.CHANCE)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.AGILITE)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.INTEL)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.PO)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.INVOC)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.DOMMAGE)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|"); //PDOM ?
        ASData.append("0,0,0,0|");//Maitrise ?
        ASData.append(baseStats.get(Element.PERDOM)).append(",").append(0).append("," + "0").append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.SOIN)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.TRAP_DOM)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.TRAP_PERDOM)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(0).append(",").append(0).append(",").append(0).append(",").append(0).append("|"); //?
        ASData.append(baseStats.get(Element.CC)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
        ASData.append(baseStats.get(Element.EC)).append(",").append(0).append(",").append(0).append(",").append(0).append("|");
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
    
    /**
     * Retourne la propection du joueur
     * @return 
     */
    public int getProspection(){
        int p = getTotalStats().get(Element.PROSPEC);
        p += Math.ceil(getTotalStats().get(Element.CHANCE) / 10);
        
        return p;
    }
    
    /**
     * cannaux utilisés
     * @return 
     */
    public String getChanels(){
        return chanels;
    }
    
    public void addChanel(char c){
        chanels += c;
    }
    
    public void removeChanel(char c){
        chanels.replace(String.valueOf(c), "");
    }
}
