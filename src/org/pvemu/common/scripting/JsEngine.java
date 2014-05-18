package org.pvemu.common.scripting;
import org.pvemu.common.Loggin;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

public final class JsEngine {
    //private Context context;
    private Scriptable scope;
    /**
     * Le path (relatif ou absolu) vers les scripts
     */
    final static String PATH = "scripts/";
    /**
     * Extension du javascript
     */
    private final static String EXT = ".js";
    
    /**
     * Version java à utiliser (version mini = 1.7)
     */
    private final static int JAVA_VERSION = Context.VERSION_1_7;
    /**
     * Optimisation de rhino. Un nombre trop élever peut causer
     * des problèmes et ralenti la compilation.
     * Une nombre trop faible n'optimise pas le code
     * 
     * @values -1 : toujours réinterpréter le code (pas de compilation)<br/>0 : pas d'optimisation, mais code compilé<br/>1 - 9 : optimisation du code
     */
    private final static int OPTIMIZATION_LEVEL = 3;
    /**
     * Compiler les scripts pour de meilleurs performances ? (affecte le temps de compilation)
     */
    private final static boolean COMPILE_SCRIPTS = true;
    
    private HashMap<String, Script> cache = new HashMap<>();
    
    public JsEngine(){
        Context context = Context.enter();
        try{
            context.setLanguageVersion(JAVA_VERSION);
            context.setOptimizationLevel(OPTIMIZATION_LEVEL);
            context.getWrapFactory().setJavaPrimitiveWrap(false);

            //scope = context.initStandardObjects();
            scope = new ImporterTopLevel(context);
        }finally{
            Context.exit();
        }
        
        eval(
                "var pvemu = Packages.org.pvemu;"
                + "var common = pvemu.common;"
                + "var game = pvemu.game;"
                + "var network = pvemu.network;"
                + "importPackage(common);"
                + "importPackage(java.lang);"
                + "importClass(common.scripting.API);"
                + "var commands = pvemu.commands;"
                + "importPackage(commands);"
                + "var actions = pvemu.actions;"
                + "importPackage(actions);"
                + "importPackage(common.utils);"
        );
    }
    
    /**
     * Charge un fichier de script js
     * @param name le nom du script (ne pas mettre l'extension !)
     * @throws Exception en cas d'erreur sur le script ou sur le fichier
     */
    public void loadScript(String name) throws Exception{
        loadScript(new File(PATH + name + EXT));
    }
    
        /**
     * Charge un fichier de script js
     * @param file fichier de script
     * @throws Exception en cas d'erreur sur le script ou sur le fichier
     */
    public void loadScript(File file) throws Exception{
        try{
            Context context = Context.enter();
            if(!COMPILE_SCRIPTS){
                context.evaluateReader(scope, new FileReader(file), file.getName(), 1, null);
                return;
            }

            if(!cache.containsKey(file.getName())){
                compileScript(file, context);
            }
            cache.get(file.getName()).exec(context, scope);
        }finally{
            Context.exit();
        }
    }
    
    /**
     * Compile le fichier de script
     * @param scriptName nom du script à compiler
     */
    private void compileScript(File file, Context context){        
        try{
            cache.put(file.getName(), context.compileReader(new FileReader(file), file.getName(), 1, null));
        }catch(Exception e){
            Loggin.error("Impossible de compiler le script '" + file.getName() + "'", e);
            return;
        }
        
        Loggin.debug("Compilation du script '%s'", file.getName());
    }
    
    /**
     * Exécute un code JS à la volé
     * @param code le code à exécuter
     */
    public void eval(String code){
        try{
            Context context = Context.enter();
            context.evaluateString(scope, code, "<internal>", 1, null);
        }finally{
            Context.exit();
        }
    }
}
