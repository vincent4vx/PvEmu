package models.dao;

public class DAOFactory {

    private static AccountDAO account = null;
    private static CharacterDAO player = null;
    private static MapDAO map = null;
    private static TriggerDAO trigger = null;
    private static InventoryDAO inventory = null;
    private static ItemTemplateDAO item = null;

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
}
