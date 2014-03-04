/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MapSender {    
    public void addGMable(GameMap map, GMable ga){
        GamePacketEnum.MAP_ELEM.sendToMap(map, GeneratorsRegistry.getMap().generateSingleGM(ga));
    }
    
    public void getAllGMable(GameMap map, IoSession session){
        GamePacketEnum.MAP_ELEM.send(session, GeneratorsRegistry.getMap().generateAllMapElem(map));
    }
    
    public void removeGMable(GameMap map, GMable ga){
        GamePacketEnum.MAP_REMOVE.sendToMap(map, ga.getID());
    }
    
    public void mapData(GameMap map, IoSession session){
        GamePacketEnum.MAP_DATA.send(session, GeneratorsRegistry.getMap().generateMapData(map));
    }
    
    public void fightCount(GameMap map, IoSession session){
        GamePacketEnum.MAP_FIGHT_COUNT.send(session);
    }
}
