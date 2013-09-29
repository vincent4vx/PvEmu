package com.oldofus.jelly.scripting;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import com.oldofus.jelly.Shell;

public class JsEngine {
    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine;
    private final static String PATH = "scripts/";
    private final static String EXT = ".js";
    
    public JsEngine(){
        engine = manager.getEngineByName("JavaScript");
        ScriptContext context = new SimpleScriptContext();
        int scope = ScriptContext.ENGINE_SCOPE;
        
        context.setAttribute("API", API.getIntance(), scope);
        
        engine.setContext(context);
        try {
            engine.eval(
                    "var oldofus = Packages.com.oldofus;"
                    + "var jelly = oldofus.jelly;"
                    + "importPackage(jelly);"
                    + "importPackage(java.lang);"
            );
        } catch (ScriptException ex) {
            Logger.getLogger(JsEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadScript(String name) throws ScriptException{
        File script = new File(PATH + name + EXT);
        
        
        if(!script.isFile()){
            Shell.println("Impossible de lire le script");
            return;
        }
        try {
            engine.eval(new FileReader(script));
        } catch (FileNotFoundException ex) {
        }
    }
    
    public ScriptEngine getEngine(){
        return engine;
    }
    
    public Invocable getInvocator(){
        return (Invocable)engine;
    }
}
