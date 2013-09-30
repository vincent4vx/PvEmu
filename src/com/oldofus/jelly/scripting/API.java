package com.oldofus.jelly.scripting;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import com.oldofus.jelly.Shell;

public class API {
    private static JsEngine engine;
    
    public static void initialise(){
        long t = System.currentTimeMillis();
        Shell.print("Chargement des scripts : ", Shell.GraphicRenditionEnum.YELLOW);
        engine = new JsEngine();
        execute("loader");
        Shell.println("Ok en " + (System.currentTimeMillis() - t) + "ms", Shell.GraphicRenditionEnum.GREEN);
    }
    
    public static void execute(String script_name){
        try {
            engine.loadScript(script_name);
        } catch (Exception ex) {
            Logger.getLogger(API.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static JsEngine getEngine(){
        return engine;
    }
}
