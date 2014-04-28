package org.pvemu.models.dao;


public class DAOFactory {

    private static AccountDAO account = null;
    private static CharacterDAO player = null;
    private static MapDAO map = null;
    private static TriggerDAO trigger = null;
    private static InventoryDAO inventory = null;
    private static ItemTemplateDAO item = null;
    private static NpcTemplateDAO npcTemplate = null;
    private static MapNpcsDAO mapNpcs = null;
    private static NpcQuestionDAO question = null;
    private static NpcResponseActionDAO responseAction = null;
    private static MonsterDAO monster = null;
    private static SpellDAO spell = null;
    private static LearnedSpellDAO learnedSpell = null;
    private static ExperienceDAO experience = null;
    
    public static void init(){
        account = new AccountDAO();
        player = new CharacterDAO();
        map = new MapDAO();
        trigger = new TriggerDAO();
        inventory = new InventoryDAO();
        item = new ItemTemplateDAO();
        npcTemplate = new NpcTemplateDAO();
        mapNpcs = new MapNpcsDAO();
        question = new NpcQuestionDAO();
        responseAction = new NpcResponseActionDAO();
        monster = new MonsterDAO();
        spell = new SpellDAO();
        learnedSpell = new LearnedSpellDAO();
        experience = new ExperienceDAO();
    }

    /**
     * Get the value of experience
     *
     * @return the value of experience
     */
    public static ExperienceDAO experience() {
        return experience;
    }


    public static LearnedSpellDAO learnedSpell() {
        return learnedSpell;
    }


    /**
     * Get the value of spell
     *
     * @return the value of spell
     */
    public static SpellDAO spell() {
        return spell;
    }


    /**
     * Get the value of monster
     *
     * @return the value of monster
     */
    public static MonsterDAO monster() {
        return monster;
    }


    public static AccountDAO account() {
        return account;
    }

    public static CharacterDAO character() {
        return player;
    }

    public static MapDAO map() {
        return map;
    }

    public static TriggerDAO trigger() {
        return trigger;
    }
    
    public static InventoryDAO inventory(){
        return inventory;
    }
    
    public static ItemTemplateDAO item(){
        return item;
    }
    
    public static NpcTemplateDAO npcTemplate(){
        return npcTemplate;
    }
    
    public static MapNpcsDAO mapNpcs(){
        return mapNpcs;
    }
    
    public static NpcQuestionDAO question(){
        return question;
    }
    
    public static NpcResponseActionDAO responseAction(){
        return responseAction;
    }
}
