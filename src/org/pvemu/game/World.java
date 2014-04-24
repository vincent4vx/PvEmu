package org.pvemu.game;

import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.jelly.Loggin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.Shell.GraphicRenditionEnum;
import org.pvemu.jelly.database.Database;
import org.pvemu.models.dao.DAOFactory;

/**
 * classe "registre"
 * @note singleton
 * @author vincent
 */
public class World {
    private static final World instance = new World();

    private final ConcurrentHashMap<String, Player> online  = new ConcurrentHashMap<>();
    
    /**
     * Constructeur privé (singleton)
     */
    private World(){}
    
    public void create(boolean preload){
        Stats.loadElements();
        
        if(preload)
            preload();
    }

    private void preload() {
        Shell.println("\n====> Préchargement <====", GraphicRenditionEnum.BOLD);
        Shell.print("Chargement des maps : ", GraphicRenditionEnum.YELLOW);
        Shell.println(DAOFactory.map().getAll().size() + " maps chargées", GraphicRenditionEnum.GREEN);
        Shell.print("Chargement des question : ", GraphicRenditionEnum.YELLOW);
        Shell.println(DAOFactory.question().getAll().size() + " question chargées", GraphicRenditionEnum.GREEN);
        Shell.print("Chargement des pnj : ", GraphicRenditionEnum.YELLOW);
        Shell.println(DAOFactory.npcTemplate().getAll().size() + " templates chargées", GraphicRenditionEnum.GREEN);
        Shell.print("Chargement des comptes : ", GraphicRenditionEnum.YELLOW);
        Shell.println(DAOFactory.account().getAll().size() + " comptes chargés", GraphicRenditionEnum.GREEN);
        Shell.print("Chargement des personnages : ", GraphicRenditionEnum.YELLOW);
        Shell.println(DAOFactory.character().getAll().size() + " personnages chargées", GraphicRenditionEnum.GREEN);
        Shell.print("Execution du GC : ", GraphicRenditionEnum.YELLOW);
        System.gc();
        Shell.println("Ok", GraphicRenditionEnum.GREEN);
    }

    /**
     * Ajoute un joueur dans la liste des joueurs en ligne
     *
     * @param p
     */
    public void addOnline(Player p) {
        online.put(p.getName().toLowerCase(), p);
    }

    /**
     * Supprime un joueur de la liste des connectés
     *
     * @param p
     */
    public void removeOnline(Player p) {
        online.remove(p.getName().toLowerCase());
    }

    /**
     * Retourne la liste de tout les joueurs en ligne
     *
     * @return
     */
    public Collection<Player> getOnlinePlayers() {
        return online.values();
    }

    /**
     * retourne la liste des joueurs correspondant au gm lvl donné
     *
     * @param c = : gm level strictement identique, + : gm level suppérieur, - :
     * gm level inférieur
     * @param level
     * @return
     */
    public Collection<Player> getOnlinePlayers(char c, byte level) {
        Collection<Player> players = new ArrayList<>();

        for (Player P : online.values()) {
            switch (c) {
                case '+':
                    if (P.getAccount().level > level) {
                        players.add(P);
                    }
                    break;
                case '-':
                    if (P.getAccount().level < level) {
                        players.add(P);
                    }
                    break;
                case '=':
                    if (P.getAccount().level == level) {
                        players.add(P);
                    }
                    break;
            }
        }

        return players;
    }

    /**
     * Retourne le personnage connecté avec le nom correspondant
     *
     * @param name
     * @return
     */
    public Player getOnlinePlayer(String name) {
        return online.get(name.toLowerCase());
    }

    /**
     * Sauvegarde le monde
     */
    public void save() {
        Database.setAutocommit(true);
        Shell.println("Sauvegarde des personnages...", GraphicRenditionEnum.YELLOW);
        for (Player player : online.values()) {
            player.save();
        }
        Shell.println("Sauvegarde terminé !", GraphicRenditionEnum.GREEN);
        Database.setAutocommit(false);
    }

    /**
     * Déconnecte TOUTE les personnes connectés (avant de fermer le serveur)
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
     * Détruit le monde
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
