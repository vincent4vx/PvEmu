package org.pvemu.jelly.utils;

import org.pvemu.game.objects.map.GameMap;
import java.util.concurrent.atomic.AtomicReference;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.jelly.Loggin;

public class Pathfinding {
    
    static public int validatePath(GameMap map, short startCell, AtomicReference<String> path){
        int steps = 0;
        short currentCell = startCell;
        short endCell = cellCode_To_ID(path.get().substring(path.get().length() - 2, path.get().length()));
        
        for(int i = 0; i < path.get().length(); i += 3){
            String step = path.get().substring(i, i+3);
            short cellID = cellCode_To_ID(step.substring(1));
            char dir = step.charAt(0);
            boolean stop = false;
            
            int lastSteps = steps;
            for(;;){
                short lastCell = currentCell;
                currentCell = getCellIDFromDirrection(currentCell, dir, map, false);
                MapCell cell = map.getCellById(currentCell);
                
                if(!cell.isWalkable()){
                    stop = true;
                }
                   
                if(endCell == currentCell && cell.getObj() != null){ //Use the IO
                    Loggin.debug("IO %d trouvé", cell.getObj().getID());
                    stop = true;
                }
                
                if(!stop && currentCell == cellID){ //end of the step
                    //steps += j;
                    break;
                }
                
                if(stop){ //blocked
                    //--j;
                    //steps += j;
                    --steps;
                    stop = true;
                    currentCell = lastCell;
                    break;
                }
                
                if(steps > lastSteps + 64)
                    return -1000;
                
                ++steps;
            }
            
                
            if(stop){
                path.set(
                        new StringBuilder().append(path.get().substring(0, i))
                                .append(dir).append(cellID_To_Code(currentCell))
                                .toString()
                );
                break;
            }
        }
        
        return steps;
    }

    public static short getCellIDFromDirrection(short CaseID, char Direction, GameMap map, boolean inFight) {
        switch (Direction) {
            case 'a':
                return (short) (inFight ? -1 : CaseID + 1);
            case 'b':
                return (short) (CaseID + map.getWidth());
            case 'c':
                return (short) (inFight ? -1 : CaseID + (map.getWidth() * 2 - 1));
            case 'd':
                return (short) (CaseID + (map.getWidth() - 1));
            case 'e':
                return (short) (inFight ? -1 : CaseID - 1);
            case 'f':
                return (short) (CaseID - map.getWidth());
            case 'g':
                return (short) (inFight ? -1 : CaseID - (map.getWidth() * 2 - 1));
            case 'h':
                return (short) (CaseID - map.getWidth() + 1);
        }
        return -1;
    }
    
    public static short cellCode_To_ID(String cellCode) {
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        char char1 = cellCode.charAt(0), char2 = cellCode.charAt(1);
        short code1 = 0, code2 = 0, a = 0;
        while (a < HASH.length) {
            if (HASH[a] == char1) {
                code1 = (short) (a * 64);
            }
            if (HASH[a] == char2) {
                code2 = a;
            }
            a++;
        }
        return (short) (code1 + code2);
    }

    public static String cellID_To_Code(short cellID) {
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};

        int char1 = cellID / 64, char2 = cellID % 64;
        return HASH[char1] + "" + HASH[char2];
    }
    
    /**
     * Calcule si deux cellules sont adjacentes
     * @param cell1
     * @param cell2
     * @return 
     */
    public static boolean isAdjacentCells(short cell1, short cell2){
        if(cell1 == cell2){
            return false;
        }
        
        short d = (short) Math.abs(cell1-cell2);
        return d == 14 || d == 15;
    }
}
