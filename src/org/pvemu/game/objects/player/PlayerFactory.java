/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapFactory;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.player.classes.ClassData;
import org.pvemu.game.objects.player.classes.ClassesHandler;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.Account;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class PlayerFactory {
    static public Player getPlayer(org.pvemu.models.Character character, Account account){
        GameMap map = getValidCurrentMap(character);
        ClassData cd = ClassesHandler.instance().getClass(character.classId);
        SpellList spells = new SpellList(character.id, DAOFactory.learnedSpell().getLearnedSpells(character.id));
        Player player = new Player(
                character,
                account,
                cd,
                getColors(character),
                getBaseStats(character.baseStats),
                spells,
                map,
                getValidCurrentCell(character, map)
        );
        
        player.getInventory().load();
        player.getItemSetHandler().loadItemSets();
        player.loadStuffStats();
        cd.learnClassSpells(player);
        player.setCurrentVita(character.currentVita);
        
        return player;
    }
    
    static private String[] getColors(org.pvemu.models.Character character){
        int[] tmp = new int[]{character.color1, character.color2, character.color3};
        String[] colors = new String[tmp.length];
        
        for(int i = 0; i < tmp.length; ++i){
            colors[i] = tmp[i] == -1 ? "-1" : Integer.toHexString(tmp[i]);
        }
        
        return colors;
    }
    
    static public Stats getBaseStats(String strStats){
        Stats stats = new Stats();
        
        if(strStats == null || strStats.isEmpty())
            return stats;
        
        for (String data : Utils.split(strStats, "|")) {
            if(data.isEmpty())
                continue;
            String[] arr = Utils.split(data, ";");
            
            if(arr.length < 2)
                continue;
            
            try {
                int elemID = Integer.parseInt(arr[0]);
                int qu = Integer.parseInt(arr[1]);
                stats.add(elemID, qu);
            } catch (Exception e) {
                Loggin.error("Cannot parse stats '" + data + "'", e);
            }
        }
        
        return stats;
    }
    
    static public GameMap getValidCurrentMap(org.pvemu.models.Character character){
        GameMap map = MapFactory.getById(character.lastMap);
        
        if(map == null){
            map = MapFactory.getById(character.startMap);
            character.lastCell = character.startCell;
        }
        
        return map;
    }
    
    static public MapCell getValidCurrentCell(org.pvemu.models.Character character, GameMap map){
        MapCell cell = map.getCellById(character.lastCell);
        
        if(cell == null){
            cell = MapUtils.getRandomValidCell(map);
        }
        
        return cell;
    }
}
