package com.oldofus.jelly.utils;

import com.oldofus.game.World;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.oldofus.jelly.Jelly;
import com.oldofus.jelly.Loggin;
import com.oldofus.jelly.Shell;
import com.oldofus.jelly.Utils;
import com.oldofus.network.game.GameIoHandler;

public class SystemStats {

    private static ThreadMXBean TMB = ManagementFactory.getThreadMXBean();
    private static long lastNanoTime = 0;
    private static long lastCpuTime = 0;

    /**
     * Retourne l'utilisation de mémoire vive par le JVM
     *
     * @return l'utilisation total de ram par JVM en Mio
     */
    public static long getRamUsage() {
        return Runtime.getRuntime().totalMemory() / (1024 * 1024);
    }

    /**
     * Retourne le nombre de threads actifs
     *
     * @return
     */
    public static int getThreadCount() {
        return TMB.getThreadCount();
    }
    
    /**
     * Retourne l'utilisation du CPU
     * @return Utilisation du CPU en double, de 0 à 1
     */
    public static double getCpuUsage(){
        long time = System.nanoTime();
        double timeDiff = time - lastNanoTime;
        long cpuTime = 0;
        
        for(long id : TMB.getAllThreadIds()){
            cpuTime += TMB.getThreadCpuTime(id);
        }
        
        double cpuDiff = cpuTime - lastCpuTime;
        
        lastNanoTime = time;
        lastCpuTime = cpuTime;
        
        return cpuDiff / timeDiff / Runtime.getRuntime().availableProcessors();
    }

    public static void printStats() {
        Shell.println("Statistiques :", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println("==========================\n", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println("Informations sur les sockets");
        Shell.print("Nombre de packets reçus   : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(String.valueOf(GameIoHandler.RECV), Shell.GraphicRenditionEnum.GREEN);
        Shell.print("Nombre de packets envoyés : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(String.valueOf(GameIoHandler.SENT), Shell.GraphicRenditionEnum.GREEN);
        Shell.print("Nombre de connexions      : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(String.valueOf(GameIoHandler.CON), Shell.GraphicRenditionEnum.CYAN);

        Shell.println("\nInformations sur le système");
        Shell.print("Utilisation de la RAM    : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(getRamUsage() + "Mo", Shell.GraphicRenditionEnum.BLUE);
        Shell.print("Nombre de threads actifs : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(String.valueOf(getThreadCount()), Shell.GraphicRenditionEnum.CYAN);
        Shell.print("Utilisation du CPU       : ", Shell.GraphicRenditionEnum.YELLOW);
        
        double cpu = getCpuUsage() * 100;
        String msg = String.valueOf(cpu).substring(0, 4) + "%";
        Shell.GraphicRenditionEnum color;
        
        if(cpu >= 75){
            color = Shell.GraphicRenditionEnum.RED;
        }else if(cpu >= 50){
            color = Shell.GraphicRenditionEnum.YELLOW;
        }else if(cpu >= 25){
            color = Shell.GraphicRenditionEnum.BLUE;
        }else{
            color = Shell.GraphicRenditionEnum.GREEN;
        }
        
        Shell.println(msg, color);

        Shell.println("\nInformations sur le jeu");
        Shell.print("Nombre de joueurs en ligne : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(String.valueOf(World.getOnlinePlayers().size()), Shell.GraphicRenditionEnum.GREEN);
        Shell.print("Uptime                     : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(Utils.getUptime(), Shell.GraphicRenditionEnum.YELLOW, Shell.GraphicRenditionEnum.BOLD);
        Shell.print("Nombre d'erreurs           : ", Shell.GraphicRenditionEnum.YELLOW);
        Shell.println(String.valueOf(Loggin.ERROR_COUNT), Shell.GraphicRenditionEnum.RED);
    }
    
    public static void displayStatsAtFixedRate(){
        ScheduledExecutorService SES = Executors.newSingleThreadScheduledExecutor();
        
        SES.scheduleAtFixedRate(
                new Runnable(){
                    @Override
                    public void run(){
                        if(Jelly.DEBUG){
                            return;
                        }
                        Shell.clear();
                        printStats();
                    }
                }, 0, 1, TimeUnit.SECONDS
        );
    }
}
