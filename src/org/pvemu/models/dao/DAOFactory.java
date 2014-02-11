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

    public static AccountDAO account() {
        if (account == null) {
            account = new AccountDAO();
        }
        return account;
    }

    public static CharacterDAO character() {
        if (player == null) {
            player = new CharacterDAO();
        }
        return player;
    }

    public static MapDAO map() {
        if (map == null) {
            map = new MapDAO();
        }
        return map;
    }

    public static TriggerDAO trigger() {
        if (trigger == null) {
            trigger = new TriggerDAO();
        }
        return trigger;
    }
    
    public static InventoryDAO inventory(){
        if(inventory == null){
            inventory = new InventoryDAO();
        }
        return inventory;
    }
    
    public static ItemTemplateDAO item(){
        if(item == null){
            item = new ItemTemplateDAO();
        }
        return item;
    }
    
    public static NpcTemplateDAO npcTemplate(){
        if(npcTemplate == null){
            npcTemplate = new NpcTemplateDAO();
        }
        return npcTemplate;
    }
    
    public static MapNpcsDAO mapNpcs(){
        if(mapNpcs == null){
            mapNpcs = new MapNpcsDAO();
        }
        return mapNpcs;
    }
    
    public static NpcQuestionDAO question(){
        if(question == null){
            question = new NpcQuestionDAO();
        }
        return question;
    }
    
    public static NpcResponseActionDAO responseAction(){
        if(responseAction == null){
            responseAction = new NpcResponseActionDAO();
        }
        return responseAction;
    }
}
