package org.pvemu.game.objects.npc;

import org.pvemu.game.objects.map.GMable;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.MapNpcs;
import org.pvemu.models.NpcQuestion;
import org.pvemu.models.NpcTemplate;
import org.pvemu.models.dao.DAOFactory;

public class GameNpc implements GMable {

    final private int id;
    final private NpcTemplate template;
    final private byte orientation;
    final private short cellID;
    final private NpcQuestion question;

    public GameNpc(MapNpcs data, int id) {
        template = data.getTemplate();
        this.id = id;
        orientation = data.orientation;
        cellID = data.cellid;
        
        question = DAOFactory.question().getById(template.initQuestion);
    }

    public GameNpc(int id, NpcTemplate template, byte orientation, short cellID, NpcQuestion question) {
        this.id = id;
        this.template = template;
        this.orientation = orientation;
        this.cellID = cellID;
        this.question = question;
    }

    @Override
    public String getGMData() {
        if(template == null){
            Loggin.debug("NpcTemplate manquant !");
            return "";
        }
        StringBuilder packet = new StringBuilder();
        
        packet.append(cellID).append(";");
        packet.append(orientation).append(";");
        
        if(Constants.DOFUS_VER_ID >= 1200){
            packet.append("0").append(";");
        }
        
        packet.append(id).append(";");
        packet.append(template.id).append(";");
        packet.append("-4").append(";");//type = NPC

        StringBuilder taille = new StringBuilder();
        if (template.scaleX == template.scaleY) {
            taille.append(template.scaleY);
        } else {
            taille.append(template.scaleX).append("x").append(template.scaleY);
        }
        packet.append(template.gfxID).append("^").append(taille.toString()).append(";");
        packet.append(template.sex).append(";");
        packet.append((template.color1 != -1 ? Integer.toHexString(template.color1) : "-1")).append(";");
        packet.append((template.color2 != -1 ? Integer.toHexString(template.color2) : "-1")).append(";");
        packet.append((template.color3 != -1 ? Integer.toHexString(template.color3) : "-1")).append(";");
        packet.append(template.accessories).append(";");
        packet.append("").append(";"); //extra clip
        //packet.append(_template.get_customArtWork());
        
        return packet.toString();
    }
    
    /**
     * Retourne la question principale du pnj
     * @return 
     */
    public NpcQuestion getQuestion(){
        return question;
    }

    @Override
    public short getCellId() {
        return cellID;
    }

    @Override
    public byte getOrientation() {
        return orientation;
    }

    @Override
    public String getName() {
        return "" + template.id;
    }    

    @Override
    public int getID() {
        return id;
    }

    @Override
    public byte getAlignment() {
        return -1;
    }
}
