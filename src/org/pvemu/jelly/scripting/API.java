package org.pvemu.jelly.scripting;

import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;

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
            Loggin.error("Erreur lors de l'execution du script '" + script_name + "'", ex);
        }
    }
    
    public static JsEngine getEngine(){
        return engine;
    }
}
