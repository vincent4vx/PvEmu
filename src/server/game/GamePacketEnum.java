package server.game;

import org.apache.mina.core.session.IoSession;

public enum GamePacketEnum {
    /**
     * Packet envoyé à la connexion
     */
    HELLO_GAME("HG"),
    //Accounts packets
    /**
     * Liste des personnages
     */
    CHARCTERS_LIST("ALK31536000000|"),
    /**
     * Génération du pseudo (création personnage)
     */
    CHARACTER_GENERATOR_NAME("APK"),
    /**
     * Nom du personnage existe déjà
     */
    NAME_ALEREADY_EXISTS("AAEa"),
    /**
     * Compte plein
     */
    CREATE_CHARACTER_FULL("AAEf"),
    /**
     * Erreur lors de la création du perso
     */
    CREATE_CHARACTER_ERROR("AAE"),
    /**
     * Personnage crée avec succès
     */
    CREATE_CHARACTER_OK("AAK"),
    /**
     * 
     */
    SELECT_CHARACTER_ERROR("ASE"),
    /**
     * sélection du personnage Ok
     */
    SELECT_CHARACTER_OK("ASK|"),
    /**
     * Jeu Ok (param = name)
     */
    GAME_CREATE_OK("GCK|1|"),
    /**
     * Packet de stats
     */
    STATS_PACKET("As"),
    /**
     * information sur la map courrante
     * id | date | key
     */
    MAP_DATA("GDM|"),
    /**
     * Nombre de combats sur la carte
     */
    MAP_FIGHT_COUNT("fC", "0"),
    /**
     * Ajoute un joueur
     */
    MAP_ADD_PLAYER("GM|+"),
    /**
     * Supprime un élément de la map
     */
    MAP_REMOVE("GM|-"),
    /**
     * dès que la map est chargé
     */
    MAP_LOADED("GDK"),
    /**
     * Message d'informations prédéfinie
     */
    INFORMATION_MESSAGE("Im0"),
    /**
     * Messages d'erreur prédéfinies
     */
    ERROR_INFORMATION_MESSAGE("Im1"),
    /**
     * Messages d'informations PvP
     */
    PVP_INFORMATION_MESSAGE("Im2"),
    /**
     * date actuelle
     */
    BASIC_DATE("BD"),
    /**
     * heure actuelle
     */
    BASIC_TIME("BT"),
    /**
     * confirmation de game action
     * [id de l'action] ; [type] (;[args]...)
     */
    GAME_ACTION("GA"),
    /**
     * Annule la dernière game action
     */
    GAME_ACTION_ERROR("GA;0"),
    /**
     * Packet de chat
     */
    CHAT_MESSAGE_OK("cMK"),
    /**
     * Ajoute un canal de chat
     */
    CHAT_CHANEL_ADD("cC+"),
    /**
     * Supprime un canal de chat
     */
    CHAT_CHANEL_REMOVE("cC-"),
    /**
     * écrit dans la console (150 caractères max)
     */
    BASIC_CONSOLE_WRITE("BAT2")
    ;
    
    private String packet;
    private String param;
    
    GamePacketEnum(String packet){
        this.packet = packet;
        this.param = "";
    }
    
    GamePacketEnum(String packet, String param){
        this.packet = packet;
        this.param = param;
    }
    
    /**
     * Envoit le packet avec des parametres
     * @param session
     * @param param 
     */
    public void send(IoSession session, String param){
        session.write(packet + param);
    }
    
    /**
     * Envoit le packet avec les paramatres par défaut
     * @param session 
     */
    public void send(IoSession session){
        session.write(packet + param);
    }
}
