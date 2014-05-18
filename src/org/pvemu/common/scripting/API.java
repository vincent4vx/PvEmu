package org.pvemu.common.scripting;

import java.io.File;
import org.pvemu.common.Loggin;
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.common.i18n.translation.Scripting;

public class API {
    private static JsEngine engine;
    
    public static void initialise(){
        long t = System.currentTimeMillis();
        Shell.print(I18n.tr(Commons.LOADING, I18n.tr(Commons.SCRIPTS)), Shell.GraphicRenditionEnum.YELLOW);
        engine = new JsEngine();
        execute("loader");
        Shell.println(I18n.tr(Commons.LOADED_IN, I18n.tr(Commons.SCRIPTS), System.currentTimeMillis() - t), Shell.GraphicRenditionEnum.GREEN);
    }
    
    public static void execute(String script_name){
        try {
            engine.loadScript(script_name);
        } catch (Exception ex) {
            Loggin.error(I18n.tr(Scripting.EXEC_ERROR, script_name), ex);
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
                Loggin.error(I18n.tr(Scripting.CANT_LOAD, file.getName()), ex);
            }
        }
    }
    
    public static JsEngine getEngine(){
        return engine;
    }
}
