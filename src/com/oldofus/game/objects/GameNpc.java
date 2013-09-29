package com.oldofus.game.objects;

import com.oldofus.game.objects.dep.GMable;
import com.oldofus.jelly.Constants;
import com.oldofus.jelly.Loggin;
import com.oldofus.models.MapNpcs;
import com.oldofus.models.NpcQuestion;
import com.oldofus.models.NpcTemplate;
import com.oldofus.models.dao.DAOFactory;

public class GameNpc implements GMable {

    private int id;
    private NpcTemplate _template;
    private byte orientation;
    private short cellID;
    private NpcQuestion _question;

    public GameNpc(MapNpcs data, int id) {
        _template = data.getTemplate();
        this.id = id;
        orientation = data.orientation;
        cellID = data.cellid;
        
        _question = DAOFactory.question().getById(_template.initQuestion);
    }

    @Override
    public String getGMData() {
        if(_template == null){
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
        packet.append(_template.id).append(";");
        packet.append("-4").append(";");//type = NPC

        StringBuilder taille = new StringBuilder();
        if (_template.scaleX == _template.scaleY) {
            taille.append(_template.scaleY);
        } else {
            taille.append(_template.scaleX).append("x").append(_template.scaleY);
        }
        packet.append(_template.gfxID).append("^").append(taille.toString()).append(";");
        packet.append(_template.sex).append(";");
        packet.append((_template.color1 != -1 ? Integer.toHexString(_template.color1) : "-1")).append(";");
        packet.append((_template.color2 != -1 ? Integer.toHexString(_template.color2) : "-1")).append(";");
        packet.append((_template.color3 != -1 ? Integer.toHexString(_template.color3) : "-1")).append(";");
        packet.append(_template.accessories).append(";");
        packet.append("").append(";"); //extra clip
        //packet.append(_template.get_customArtWork());
        
        return packet.toString();
    }
    
    /**
     * Retourne la question principale du pnj
     * @return 
     */
    public NpcQuestion getQuestion(){
        return _question;
    }

    @Override
    public int getID() {
        return id;
    }
}
