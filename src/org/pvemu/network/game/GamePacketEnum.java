package org.pvemu.network.game;

import org.pvemu.game.World;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.Player;
import java.util.Collection;
import org.pvemu.jelly.Jelly;
import org.apache.mina.core.session.IoSession;

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
     * confirmation de game action [id de l'action] ; [type] (;[args]...)
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
    PONG("pong");
    private String packet;
    private Object param;

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
        if (!Jelly.running) {
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
        sendToPlayerList(World.instance().getOnlinePlayers(), param);
    }

    /**
     * Envoit le packet aux joueurs sléectionné
     *
     * @param players
     */
    public void sendToPlayerList(Collection<Player> players) {
        sendToPlayerList(players, param);
    }

    /**
     * Envoit le packet aux joueurs sélectionnés
     *
     * @param players
     * @param param
     */
    public void sendToPlayerList(Collection<Player> players, Object param) {
        if (!Jelly.running) {
            return;
        }
        for (Player P : players) {
            if (P.getSession() == null) {
                P.logout();
                continue;
            }
            if(P.getSession().isClosing()){
                return;
            }
            P.getSession().write(packet + String.valueOf(param));
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
     * Envoit le packet à tout les joueurs de la map
     *
     * @param map
     */
    public void sendToMap(GameMap map, Object param) {
        sendToPlayerList(map.getPlayers().values(), param);
    }
}
