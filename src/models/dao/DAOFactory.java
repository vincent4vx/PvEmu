package models.dao;

public class DAOFactory {
    private static AccountDAO account = null;
    private static CharacterDAO player = null;
    private static MapDAO map = null;
    private static TriggerDAO trigger = null;
    
    public static AccountDAO account(){
        if(account == null){
            account = new AccountDAO();
        }
        return account;
    }
    
    public static CharacterDAO character(){
        if(player == null){
            player = new CharacterDAO();
        }
        return player;
    }
    
    public static MapDAO map(){
        if(map == null){
            map = new MapDAO();
        }
        return map;
    }
    
    public static TriggerDAO trigger(){
        if(trigger == null){
            trigger = new TriggerDAO();
        }
        return trigger;
    }
}
