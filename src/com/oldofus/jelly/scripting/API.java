package com.oldofus.jelly.scripting;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import com.oldofus.jelly.Shell;

public class API {
    private JsEngine engine;
    private static API instance;
    
    public static void initialise(){
        long t = System.currentTimeMillis();
        Shell.print("Chargement des scripts : ", Shell.GraphicRenditionEnum.YELLOW);
        instance = new API();
        instance.engine = new JsEngine();
        instance.execute("loader");
        Shell.println("Ok en " + (System.currentTimeMillis() - t) + "ms", Shell.GraphicRenditionEnum.GREEN);
    }
    
    public final void execute(String script_name){
        try {
            engine.loadScript(script_name);
        } catch (ScriptException ex) {
            Logger.getLogger(API.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object getObject(String name){
        return engine.getEngine().get(name);
    }
    
    public static API getIntance(){
        return instance;
    }
}
