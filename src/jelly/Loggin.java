package jelly;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggin {
    private final static Loggin self = new Loggin();
    private Logger realm;
    
    private Loggin(){
        try {
            realm = Logger.getLogger("Realm");
            realm.addHandler(new FileHandler("Logs/realm.log"));
        } catch (Exception e) {}
    }
    
    public static void debug(String msg){
        if(Jelly.DEBUG){
            System.out.println("[debug] " + msg);
        }
    }
    
    public static void realm(String msg){
        realm(msg, Level.INFO, null);
    }
    
    public static void realm(String msg, Level lvl, Exception ex){
        self.realm.log(lvl, msg, ex);
    }
}
