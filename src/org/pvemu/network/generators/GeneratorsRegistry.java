/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GeneratorsRegistry {
    private static PlayerGenerator player = null;
    private static InformativeMessageGenerator informativeMessage = null;
    private static ObjectGenerator object = null;
    private static AccountGenerator account = null;
    private static MapGenerator map = null;
    private static CharacterGenerator character = null;
    private static ExchangeGenerator exchange = null;
    private static MessageGenerator message = null;
    private static BasicGenerator basic = null;
    private static DialogGenerator dialog = null;
    private static GameActionGenerator gameAction = null;
    private static MonsterGenerator monster = null;
    private static FightGenerator fight = null;
    private static EffectGenerator effect = null;
    private static SpellGenerator spell = null;

    /**
     * Get the value of spell
     *
     * @return the value of spell
     */
    public static SpellGenerator getSpell() {
        if(spell == null)
            spell = new SpellGenerator();
        return spell;
    }

    /**
     * Set the value of spell
     *
     * @param spell new value of spell
     */
    public static void setSpell(SpellGenerator spell) {
        GeneratorsRegistry.spell = spell;
    }


    /**
     * Get the value of effect
     *
     * @return the value of effect
     */
    public static EffectGenerator getEffect() {
        if(effect == null)
            effect = new EffectGenerator();
        return effect;
    }

    /**
     * Set the value of effect
     *
     * @param effect new value of effect
     */
    public static void setEffect(EffectGenerator effect) {
        GeneratorsRegistry.effect = effect;
    }


    /**
     * Get the value of fight
     *
     * @return the value of fight
     */
    public static FightGenerator getFight() {
        if(fight == null)
            fight = new FightGenerator();
        return fight;
    }

    /**
     * Set the value of fight
     *
     * @param fight new value of fight
     */
    public static void setFight(FightGenerator fight) {
        GeneratorsRegistry.fight = fight;
    }


    /**
     * Get the value of monster
     *
     * @return the value of monster
     */
    public static MonsterGenerator getMonster() {
        if(monster == null)
            monster = new MonsterGenerator();
        return monster;
    }

    /**
     * Set the value of monster
     *
     * @param monster new value of monster
     */
    public static void setMonster(MonsterGenerator monster) {
        GeneratorsRegistry.monster = monster;
    }


    /**
     * Get the value of gameAction
     *
     * @return the value of gameAction
     */
    public static GameActionGenerator getGameAction() {
        if(gameAction == null)
            gameAction = new GameActionGenerator();
        return gameAction;
    }

    /**
     * Set the value of gameAction
     *
     * @param gameAction new value of gameAction
     */
    public static void setGameAction(GameActionGenerator gameAction) {
        GeneratorsRegistry.gameAction = gameAction;
    }


    /**
     * Get the value of dialog
     *
     * @return the value of dialog
     */
    public static DialogGenerator getDialog() {
        if(dialog == null)
            dialog = new DialogGenerator();
        return dialog;
    }

    /**
     * Set the value of dialog
     *
     * @param dialog new value of dialog
     */
    public static void setDialog(DialogGenerator dialog) {
        GeneratorsRegistry.dialog = dialog;
    }


    /**
     * Get the value of basic
     *
     * @return the value of basic
     */
    public static BasicGenerator getBasic() {
        if(basic == null)
            return basic = new BasicGenerator();
        return basic;
    }

    /**
     * Set the value of basic
     *
     * @param basic new value of basic
     */
    public static void setBasic(BasicGenerator basic) {
        GeneratorsRegistry.basic = basic;
    }


    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public static MessageGenerator getMessage() {
        if(message == null)
            message = new MessageGenerator();
        return message;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public static void setMessage(MessageGenerator message) {
        GeneratorsRegistry.message = message;
    }


    /**
     * Get the value of exchange
     *
     * @return the value of exchange
     */
    public static ExchangeGenerator getExchange() {
        if(exchange == null)
            exchange = new ExchangeGenerator();
        return exchange;
    }

    /**
     * Set the value of exchange
     *
     * @param exchange new value of exchange
     */
    public static void setExchange(ExchangeGenerator exchange) {
        GeneratorsRegistry.exchange = exchange;
    }


    /**
     * Get the value of character
     *
     * @return the value of character
     */
    public static CharacterGenerator getCharacter() {
        if(character == null)
            character = new CharacterGenerator();
        return character;
    }

    /**
     * Set the value of character
     *
     * @param character new value of character
     */
    public static void setCharacter(CharacterGenerator character) {
        GeneratorsRegistry.character = character;
    }


    /**
     * Get the value of map
     *
     * @return the value of map
     */
    public static MapGenerator getMap() {
        if(map == null)
            map = new MapGenerator();
        return map;
    }

    /**
     * Set the value of map
     *
     * @param map new value of map
     */
    public static void setMap(MapGenerator map) {
        GeneratorsRegistry.map = map;
    }


    /**
     * Get the value of account
     *
     * @return the value of account
     */
    public static AccountGenerator getAccount() {
        if(account == null)
            account = new AccountGenerator();
        return account;
    }

    /**
     * Set the value of account
     *
     * @param account new value of account
     */
    public static void setAccount(AccountGenerator account) {
        GeneratorsRegistry.account = account;
    }


    /**
     * Get the value of object
     *
     * @return the value of object
     */
    public static ObjectGenerator getObject() {
        if(object == null)
            object = new ObjectGenerator();
        return object;
    }

    /**
     * Set the value of object
     *
     * @param object new value of object
     */
    public static void setObject(ObjectGenerator object) {
        GeneratorsRegistry.object = object;
    }


    /**
     * Get the value of informativeMessage
     *
     * @return the value of informativeMessage
     */
    public static InformativeMessageGenerator getInformativeMessage() {
        if(informativeMessage == null)
            informativeMessage = new InformativeMessageGenerator();
        return informativeMessage;
    }

    /**
     * Set the value of informativeMessage
     *
     * @param informativeMessage new value of informativeMessage
     */
    public static void setInformativeMessage(InformativeMessageGenerator informativeMessage) {
        GeneratorsRegistry.informativeMessage = informativeMessage;
    }

    
    public static PlayerGenerator getPlayer(){
        if(player == null)
            player = new PlayerGenerator();
        
        return player;
    }
    
    public static void setPlayer(PlayerGenerator newPlayer){
        player = newPlayer;
    }
    
    
}
