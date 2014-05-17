package org.pvemu.game;

import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.common.Loggin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.map.MapFactory;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.game.objects.npc.NpcFactory;
import org.pvemu.game.objects.spell.SpellFactory;
import org.pvemu.game.triggeraction.TriggerFactory;
import org.pvemu.common.Shell;
import org.pvemu.common.Shell.GraphicRenditionEnum;

/**
 * registry class
 * @note singleton
 * @author vincent
 */
public class World {
    private static final World instance = new World();

    private final ConcurrentHashMap<String, Player> online  = new ConcurrentHashMap<>();
    
    private World(){}
    
    public void create(boolean preload){
        Stats.loadElements();
        
        if(preload)
            preload();
    }

    private void preload() {
        Shell.println("\n====> Préchargement <====", GraphicRenditionEnum.BOLD);
        SpellFactory.preloadSpells();
        MonsterFactory.preloadMonsters();
        NpcFactory.preloadNpcs();
        TriggerFactory.preloadTriggers();
        MapFactory.preloadMaps();
        Shell.print("Execution du GC : ", GraphicRenditionEnum.YELLOW);
        System.gc();
        Shell.println("Ok", GraphicRenditionEnum.GREEN);
    }

    /**
     * add a new player into the list
     * @param p
     */
    public void addOnline(Player p) {
        online.put(p.getName().toLowerCase(), p);
    }

    /**
     * delete a player from online list
     * @param p
     */
    public void removeOnline(Player p) {
        online.remove(p.getName().toLowerCase());
    }

    /**
     * get the online list
     * @return
     */
    public Collection<Player> getOnlinePlayers() {
        return online.values();
    }
    
    /**
     * get an online player by his name
     * @param name
     * @return
     */
    public Player getOnlinePlayer(String name) {
        return online.get(name.toLowerCase());
    }

    /**
     * Sauvegarde the world
     */
    public void save() {
        Shell.println("Sauvegarde des personnages...", GraphicRenditionEnum.YELLOW);
        for (Player player : online.values()) {
            player.save();
        }
        Shell.println("Sauvegarde terminé !", GraphicRenditionEnum.GREEN);
    }

    /**
     * logout all online players
     */
    public void kickAll() {
        Shell.print("Déconnexion des clients: ", GraphicRenditionEnum.RED);
        try {
            for (Player P : online.values()) {
                if (P.getSession() != null) {
                    P.getSession().close(true).awaitUninterruptibly();
                } else {
                    ActionsRegistry.getPlayer().logout(P);
                    ActionsRegistry.getAccount().logout(P.getAccount());
                }
            }
            Shell.println("Ok !", GraphicRenditionEnum.GREEN);
        } catch (Exception e) {
            Loggin.error("World.kickAll()", e);
        }
    }
    
    /**
     * Destroy the world :'(
     */
    public void destroy(){
        Shell.println("Destruction du monde...", GraphicRenditionEnum.RED);
        save();
        kickAll();
    }
    
    public static World instance(){
        return instance;
    }
}
