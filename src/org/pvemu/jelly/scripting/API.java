package org.pvemu.jelly.scripting;

import java.io.File;
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
    
    static public void execDir(String dirname){
        File dir = new File(JsEngine.PATH + dirname);
        
        if(!dir.isDirectory()){
            Loggin.warning("Répertoire '%s' inexistant !", dirname);
        }
        
        for(File file : dir.listFiles()){
            try {
                engine.loadScript(file);
            } catch (Exception ex) {
                Loggin.error("Impossible de charger le fichier de script " + file.getName(), ex);
            }
        }
    }
    
    public static JsEngine getEngine(){
        return engine;
    }
}
