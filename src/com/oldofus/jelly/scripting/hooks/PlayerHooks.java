package com.oldofus.jelly.scripting.hooks;

import com.oldofus.game.objects.Player;

/**
 * Gère les callback pour les packets des joueurs
 * @author vincent
 * @since 0.11
 */
public class PlayerHooks {
    /**
     * Callback pour packet GM
     */
    public interface GMHook{
        public String call(Player p);
    }
    
    private static GMHook _GMHook = null;
    
    /**
     * Enregistre une callback pour le packet GM des players
     * @param hook La callback générant le packet. Doit prendre pour paramettre le joueur
     */
    public static void registerGMHook(GMHook hook){
        _GMHook = hook;
    }
    
    /**
     * Appel la callback de génération du packet GM si elle existe
     * @param p Le personnage courant
     * @return le packet si la callback exsite, null sinon
     */
    public static String callGMHook(com.oldofus.game.objects.Player p){
        if(_GMHook == null){
            return null;
        }
        
        return _GMHook.call(p);
    }
    
    public interface AsHook{
        public String call(Player p);
    }
    
    private static AsHook _AsHook = null;
    
    /**
     * Packet As (stats)
     * @param hook callback générant le packet, prend comme parametre le joueur
     */
    public static void registerAsHook(AsHook hook){
        _AsHook = hook;
    }
    
    /**
     * Appel du hook si possible
     * @param p le joueur cible
     * @return packet généré, ou null si pas de callback
     */
    public static String callAsHook(Player p){
        if(_AsHook == null){
            return null;
        }
        
        return _AsHook.call(p);
    }
}
