package org.pvemu.network.game;

import org.pvemu.game.World;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.common.PvEmu;
import org.apache.mina.core.session.IoSession;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.filters.Filterable;
import org.pvemu.network.Sessionable;

public enum GamePacketEnum {

    /**
     * Packet envoyé à la connexion
     */
    HELLO_GAME("HG"),
    /**
     * Erreur lors de l'attachement du compte
     */
    ACCOUNT_ATTACH_ERROR("ATE"),
    /**
     * Attachement OK, envois data du perso
     * (dofus < 1.10.0)
     */
    ACCOUNT_ATTACH_OK("ATK"),
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
    SELECT_CHARACTER_OK("ASK"),
    /**
     * Jeu Ok (param = name)
     */
    GAME_CREATE_OK("GCK|1|"),
    /**
     * Packet de stats
     */
    STATS_PACKET("As"),
    /**
     * Restriction du personnage (vitesse, tombe...)
     */
    CHARACTER_RESTRICTION("AR", "6bk"),
    /**
     * Erreur lors de la suppression du personnage
     */
    CHARACTER_DELETE_ERROR("ADE"),
    /**
     * information sur la map courrante id | date | key
     */
    MAP_DATA("GDM|"),
    /**
     * Nombre de combats sur la carte
     */
    MAP_FIGHT_COUNT("fC", "0"),
    /**
     * Ajoute un joueur
     */
    @Deprecated
    MAP_ADD("GM|+"),
    /**
     * modifie les éléments de la map (les GMable)
     */
    MAP_ELEM("GM"),
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
     * confirmation de game action [id de l'action] ; [type] (;[args]...)
     */
    GAME_ACTION("GA"),
    GAME_ACTION_START("GAS"),
    GAME_ACTION_FNISH("GAF"),
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
     * Affiche le smiley aux joueurs
     * [id joueur] | [id smiley]
     */
    CHAT_SMILEY("cS"),
    /**
     * écrit dans la console (150 caractères max)
     * @deprecated 
     */
    BASIC_CONSOLE_WRITE("BAT2"),
    /**
     * Ecris dans la console un message d'erreur
     */
    CONSOLE_WRITE_ERROR("BAT1"),
    /**
     * Ecris dans la console un message de succès
     */
    CONSOLE_WRITE_OK("BAT2"),
    /**
     * Ecris dans la console un message de debug
     */
    CONSOLE_WRITE_DEBUG("BAT0"),
    /**
     * 
     */
    BASIC_CONSOLE_PROMPT("BAP"),
    /**
     * Message serveur (utilisé par console admin)
     */
    SERVER_MESSAGE("cs"),
    /**
     * Nombre de pods utilisés | pods max
     */
    OBJECTS_WEIGHT("Ow"),
    /**
     * Utilisé par onQuantityChange
     */
    OBJECT_QUANTITY("OQ"),
    /**
     * Ajoute un objet à l'inventaire
     */
    OBJECT_ADD_OK("OAKO"),
    OBJECT_ADD_ERROR_LEVEL("OAEL"),
    /**
     * Supprime l'objet de l'inventaire
     */
    OBJECT_REMOVE("OR"),
    /**
     * Déplace l'objet
     * [id] | [position]
     */
    OBJECT_MOVE("OM"),
    /**
     * Packet pour affichage des accessoires (coiffe cape arme bouclier et familier
     */
    OBJECT_ACCESSORIES("Oa"),
    OBJECT_SET("OS"),
    /**
     * Cinématique du début
     */
    TUTORIAL_BEGIN("TB"),
    /**
     * Création du tuto (pour Dofus 1.9)
     */
    TUTORIAL_CREATE("TC"),
    /**
     * Change la direction du personnage
     * id | direction
     */
    EMOTE_DIRECTION("eD"),
    /**
     * Débute le dialogue avec le npc (param = GM id)
     */
    DIALOG_CREATE("DCK"),
    /**
     * Erreur (dialogue introuvable)
     */
    DIALOG_CREATE_ERROR("DCE"),
    /**
     * Question + liste des réponses
     */
    DIALOG_QUESTION("DQ"),
    /**
     * Ferme le dialogue
     */
    DIALOG_LEAVE("DV"),
    /**
     * Erreur lors de l'échange
     * params : 
     *  - O déjà en echange
     *  - T artisan trop loins de l'atelier
     *  - J outils non équipé
     *  - S non abo
     *  - I action impossible
     */
    EXCHANGE_ERROR("ERE", "I"),
    /**
     * Requête d'échange OK
     * param : [lanceur] | [cible] | [type]
     */
    EXCHANGE_REQUEST_OK("ERK"),
    /**
     * Echange accepté, début de l'échange
     */
    EXCHANGE_CREATE_OK("ECK"),
    /**
     * Impossible d'accpeter l'échange
     */
    EXCHANGE_CREATE_ERROR("ECE"),
    /**
     * Stop l'échange en cours
     */
    EXCHANGE_LEAVE("EV"),
    /**
     * Accepte l'échange en cours
     */
    EXCHANGE_ACCEPT("EVa"),
    /**
     * Erreur lors du déplacement de l'objet
     */
    EXCHANGE_MOVE_ERROR("EME"),
    /**
     * Si échange accepté ou non
     * param : [0/1][player]
     */
    EXCHANGE_OK("EK"),
    /**
     * Movement OK
     * param : [type][+/-][itemID]|[qu]
     */
    EXCHANGE_LOCAL_MOVE_OK("EMK"),
    /**
     * Mouvement OK
     * param : [type][+/-][itemID]|[qu]|[templateID]|[stats]
     */
    EXCHANGE_DISTANT_MOVE_OK("EmK"),
    SPELL_LIST("SL"),
    /**
     * On start fight
     */
    FIGHT_JOIN_OK("GJK"),
    ADD_TO_TEAM("Gt"),
    FIGHT_PLACES("GP"),
    FIGHT_CHANGE_PLACE("GIC"),
    FIGHT_EFFECT("GIE"),
    FIGHT_ADD_FLAG("Gc+"),
    FIGHT_REMOVE_FLAG("Gc-"),
    FIGHT_READY("GR"),
    FIGHT_START("GS"),
    FIGHT_TURN_LIST("GTL"),
    FIGHT_TURN_MIDDLE("GTM"),
    FIGHT_TURN_START("GTS"),
    FIGHT_TURN_FNISH("GTF"),
    FIGHT_END("GE"),
    SPELL_UPGRADE_OK("SUK"),
    SPELL_UPGRADE_ERROR("SUE"),
    NEW_LEVEL("AN"),
    PONG("pong"),
    QPONG("qpong");
    final private String packet;
    final private Object param;

    GamePacketEnum(String packet) {
        this.packet = packet;
        this.param = "";
    }

    GamePacketEnum(String packet, Object param) {
        this.packet = packet;
        this.param = param;
    }

    /**
     * Envoit le packet avec des parametres
     *
     * @param session
     * @param param
     */
    public void send(IoSession session, Object param) {
        if (!PvEmu.running) {
            return; //ne rien envoyer une fois off
        }
        if(session == null || session.isClosing()){
            return;
        }
        session.write(packet + String.valueOf(param));
    }

    /**
     * Envoit le packet avec les paramatres par défaut
     *
     * @param session
     */
    public void send(IoSession session) {
        send(session, param);
    }

    /**
     * Envoit le packet à tous les joueurs connectés
     */
    public void sendToAll() {
        sendToAll(param);
    }

    /**
     * Envoit le packet à tous les joueurs connectés
     * @param param packet à envoyer
     */
    public void sendToAll(Object param) {
        sendToList(World.instance().getOnlinePlayers(), param);
    }
    
    public void sendToList(Iterable<? extends Sessionable> list, Object param){
        for(Sessionable sa : list){
            send(sa.getSession(), param);
        }
    }
    
    public <T extends Sessionable & Filterable> void sendToFilteredList(Iterable<? extends T> list, Filter filter, Object param){
        for(T s : list){
            if(s.corresponds(filter)){
                send(s.getSession(), param);
            }
        }
    }

    /**
     * Envoit le packet à tout les joueurs de la map
     *
     * @param map
     */
    public void sendToMap(GameMap map) {
        sendToMap(map, param);
    }

    /**
     * send a packet to all players on map (except players in fight)
     * @param map
     * @param param 
     */
    public void sendToMap(GameMap map, Object param) {
        sendToFilteredList(map.getPlayers().values(), FilterFactory.playerNotInFightFilter(), param);
    }
    
    public void sendToFight(Fight fight, Object param){
        /*for(Fighter fighter : fight.getFighters()){
            if(fighter instanceof Sessionable){
                send(((Sessionable)fighter).getSession(), param);
            }
        }*/
        sendToFighters(fight.getFighters(), param);
    }
    
    public void sendToFighters(Iterable<Fighter> fighters, Object param){
        for(Fighter fighter : fighters){
            if(fighter instanceof Sessionable){
                send(((Sessionable)fighter).getSession(), param);
            }
        }
    }

    public String getPacket() {
        return packet;
    }
}
