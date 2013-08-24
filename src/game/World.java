package game;

import game.objects.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import jelly.Loggin;
import jelly.Shell;
import jelly.Shell.GraphicRenditionEnum;
import jelly.database.Database;
import models.dao.DAOFactory;

public class World {

    private static ConcurrentHashMap<String, Player> _online = new ConcurrentHashMap<>();

    /**
     * Ajoute un joueur dans la liste des joueurs en ligne
     *
     * @param p
     */
    public static void addOnline(Player p) {
        _online.put(p.getName().toLowerCase(), p);
    }

    /**
     * Supprime un joueur de la liste des connectés
     *
     * @param p
     */
    public static void removeOnline(Player p) {
        _online.remove(p.getName().toLowerCase());
    }

    /**
     * Retourne la liste de tout les joueurs en ligne
     *
     * @return
     */
    public static Collection<Player> getOnlinePlayers() {
        return _online.values();
    }

    /**
     * retourne la liste des joueurs correspondant au gm lvl donné
     *
     * @param c = : gm level strictement identique, + : gm level suppérieur, - :
     * gm level inférieur
     * @param level
     * @return
     */
    public static Collection<Player> getOnlinePlayers(char c, byte level) {
        Collection<Player> players = new ArrayList<>();

        for (Player P : _online.values()) {
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
    public static Player getOnlinePlayer(String name) {
        return _online.get(name.toLowerCase());
    }

    /**
     * Sauvegarde le monde
     */
    public static void save() {
        Database.setAutocommit(true);
        Shell.println("Sauvegarde des personnages...", GraphicRenditionEnum.YELLOW);
        for (Player P : _online.values()) {
            DAOFactory.character().update(P.getCharacter());
        }
        Shell.println("Sauvegarde terminé !", GraphicRenditionEnum.GREEN);
        Database.setAutocommit(false);
    }

    /**
     * Déconnecte TOUTE les personnes connectés (avant de fermer le serveur)
     */
    public static void kickAll() {
        try {
            for (Player P : _online.values()) {
                if (P.getSession() != null) {
                    P.getSession().close(true);
                } else {
                    P.logout();
                }
            }
        } catch (Exception e) {
        }
    }
}
