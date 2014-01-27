package com.oldofus.game;

import com.oldofus.game.objects.Player;
import com.oldofus.jelly.Loggin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import com.oldofus.jelly.Shell;
import com.oldofus.jelly.Shell.GraphicRenditionEnum;
import com.oldofus.jelly.database.Database;
import com.oldofus.models.dao.DAOFactory;

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
        for (Player P : online.values()) {
            DAOFactory.character().update(P.getCharacter());
        }
        Shell.println("Sauvegarde terminé !", GraphicRenditionEnum.GREEN);
        Database.setAutocommit(false);
    }

    /**
     * Déconnecte TOUTE les personnes connectés (avant de fermer le serveur)
     */
    public void kickAll() {
        try {
            for (Player P : online.values()) {
                if (P.getSession() != null) {
                    P.getSession().close(true);
                } else {
                    P.logout();
                }
            }
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
