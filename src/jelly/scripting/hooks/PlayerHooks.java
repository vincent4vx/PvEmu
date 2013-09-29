package jelly.scripting.hooks;

import game.objects.Player;

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
        hook.call(null);
    }
    
    /**
     * Appel la callback de génération du packet GM si elle existe
     * @param p Le personnage courant
     * @return le packet si la callback exsite, null sinon
     */
    public static String callGMHook(game.objects.Player p){
        if(_GMHook == null){
            return null;
        }
        
        return _GMHook.call(p);
    }
}
