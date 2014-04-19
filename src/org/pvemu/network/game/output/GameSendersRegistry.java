/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameSendersRegistry {
    
    private static InformativeMessageSender informativeMessage = null;
    private static ObjectSender object = null;
    private static AccountSender account = null;
    private static MapSender map = null;
    private static PlayerSender player = null;
    private static ExchangeSender exchange = null;
    private static MessageSender message = null;
    private static BasicSender basic = null;
    private static DialogSender dialog = null;
    private static GameActionSender gameAction = null;
    private static DefianceSender defiance = null;
    private static FightSender fight = null;

    /**
     * Get the value of fight
     *
     * @return the value of fight
     */
    public static FightSender getFight() {
        if(fight == null)
            fight = new FightSender();
        return fight;
    }

    /**
     * Set the value of fight
     *
     * @param fight new value of fight
     */
    public static void setFight(FightSender fight) {
        GameSendersRegistry.fight = fight;
    }


    /**
     * Get the value of defiance
     *
     * @return the value of defiance
     */
    public static DefianceSender getDefiance() {
        if(defiance == null)
            defiance = new DefianceSender();
        return defiance;
    }

    /**
     * Set the value of defiance
     *
     * @param defiance new value of defiance
     */
    public static void setDefiance(DefianceSender defiance) {
        GameSendersRegistry.defiance = defiance;
    }


    /**
     * Get the value of gameAction
     *
     * @return the value of gameAction
     */
    public static GameActionSender getGameAction() {
        if(gameAction == null)
            gameAction = new GameActionSender();
        return gameAction;
    }

    /**
     * Set the value of gameAction
     *
     * @param gameAction new value of gameAction
     */
    public static void setGameAction(GameActionSender gameAction) {
        GameSendersRegistry.gameAction = gameAction;
    }


    /**
     * Get the value of dialog
     *
     * @return the value of dialog
     */
    public static DialogSender getDialog() {
        if(dialog == null)
            dialog = new DialogSender();
        return dialog;
    }

    /**
     * Set the value of dialog
     *
     * @param dialog new value of dialog
     */
    public static void setDialog(DialogSender dialog) {
        GameSendersRegistry.dialog = dialog;
    }


    /**
     * Get the value of basic
     *
     * @return the value of basic
     */
    public static BasicSender getBasic() {
        if(basic == null)
            basic = new BasicSender();
        return basic;
    }

    /**
     * Set the value of basic
     *
     * @param basic new value of basic
     */
    public static void setBasic(BasicSender basic) {
        GameSendersRegistry.basic = basic;
    }


    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public static MessageSender getMessage() {
        if(message == null)
            message = new MessageSender();
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public static void setMessage(MessageSender message) {
        GameSendersRegistry.message = message;
    }


    /**
     * Get the value of exchange
     *
     * @return the value of exchange
     */
    public static ExchangeSender getExchange() {
        if(exchange == null)
            exchange = new ExchangeSender();
        return exchange;
    }

    /**
     * Set the value of exchange
     *
     * @param exchange new value of exchange
     */
    public static void setExchange(ExchangeSender exchange) {
        GameSendersRegistry.exchange = exchange;
    }


    /**
     * Get the value of player
     *
     * @return the value of player
     */
    public static PlayerSender getPlayer() {
        if(player == null)
            player = new PlayerSender();
        return player;
    }

    /**
     * Set the value of player
     *
     * @param player new value of player
     */
    public static void setPlayer(PlayerSender player) {
        GameSendersRegistry.player = player;
    }


    /**
     * Get the value of map
     *
     * @return the value of map
     */
    public static MapSender getMap() {
        if(map == null)
            map = new MapSender();
        return map;
    }

    /**
     * Set the value of map
     *
     * @param map new value of map
     */
    public static void setMap(MapSender map) {
        GameSendersRegistry.map = map;
    }


    /**
     * Get the value of account
     *
     * @return the value of account
     */
    public static AccountSender getAccount() {
        if(account == null)
            account = new AccountSender();
        return account;
    }

    /**
     * Set the value of account
     *
     * @param account new value of account
     */
    public static void setAccount(AccountSender account) {
        GameSendersRegistry.account = account;
    }


    /**
     * Get the value of object
     *
     * @return the value of object
     */
    public static ObjectSender getObject() {
        if(object == null)
            object = new ObjectSender();
        return object;
    }

    /**
     * Set the value of object
     *
     * @param object new value of object
     */
    public static void setObject(ObjectSender object) {
        GameSendersRegistry.object = object;
    }


    /**
     * Get the value of informativeMessage
     *
     * @return the value of informativeMessage
     */
    public static InformativeMessageSender getInformativeMessage() {
        if(informativeMessage == null)
            informativeMessage = new InformativeMessageSender();
        return informativeMessage;
    }

    /**
     * Set the value of informativeMessage
     *
     * @param informativeMessage new value of informativeMessage
     */
    public static void setInformativeMessage(InformativeMessageSender informativeMessage) {
        GameSendersRegistry.informativeMessage = informativeMessage;
    }

}
