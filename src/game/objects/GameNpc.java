package game.objects;

import game.objects.dep.GMable;
import jelly.Loggin;
import models.MapNpcs;
import models.NpcTemplate;

public class GameNpc implements GMable {

    private int id;
    private NpcTemplate _template;
    private byte orientation;
    private short cellID;

    public GameNpc(MapNpcs data, int id) {
        _template = data.getTemplate();
        this.id = id;
        orientation = data.orientation;
        cellID = data.cellid;
    }

    @Override
    public String getGMData() {
        if(_template == null){
            Loggin.debug("NpcTemplate manquant !");
            return "";
        }
        StringBuilder packet = new StringBuilder();
        
        packet.append("+");
        packet.append(cellID).append(";");
        packet.append(orientation).append(";");
        packet.append("0").append(";");
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

    @Override
    public int getID() {
        return id;
    }
}
