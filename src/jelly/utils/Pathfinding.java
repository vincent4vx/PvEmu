package jelly.utils;

import game.objects.GameMap;
import java.util.concurrent.atomic.AtomicReference;
import jelly.Loggin;

public class Pathfinding {

    public static int isValidPath(GameMap map, short cellID, AtomicReference<String> pathRef) {
        //_nSteps = 0;
        AtomicReference<Integer> rSteps = new AtomicReference<>(0);
        short newPos = cellID;
        //int Steps = 0;
        String path = pathRef.get();
        StringBuilder newPath = new StringBuilder();
        boolean stopBefore = false;
        
        for (int i = 0; i < path.length(); i += 3) {
            String SmallPath = path.substring(i, i + 3);
            char dir = SmallPath.charAt(0);
            short dirCaseID = cellCode_To_ID(SmallPath.substring(1));
            
            if(i + 3 == path.length()){ //on se trouve à une case de l'arrivé
                if(map.getCellById(dirCaseID).getObj() != null){ //on y trouve un objet intéractif
                    Loggin.debug("IO %d trouvé", map.getCellById(dirCaseID).getObj().getID());
                    stopBefore = true;              
                }
            }

            String[] aPathInfos = ValidSinglePath(newPos, SmallPath, map, rSteps, stopBefore).split(":");
            if (aPathInfos[0].equalsIgnoreCase("stop")) {
                newPos = Short.parseShort(aPathInfos[1]);
                newPath.append(dir).append(cellID_To_Code(newPos));
                pathRef.set(newPath.toString());
                return rSteps.get();
            } else if (aPathInfos[0].equalsIgnoreCase("ok")) {
                newPos = dirCaseID;
            } else {
                pathRef.set(newPath.toString());
                return -1000;
            }
            newPath.append(dir).append(cellID_To_Code(newPos));
        }
        pathRef.set(newPath.toString());
        return rSteps.get();
    }

    public static String ValidSinglePath(short CurrentPos, String Path, GameMap map, AtomicReference<Integer> rSteps, boolean stopBefore) {
        int steps = 0;
        char dir = Path.charAt(0);
        short dirCaseID = cellCode_To_ID(Path.substring(1));
        short lastPos = CurrentPos;
        for (steps = 1; steps <= 64; steps++) {

            if (GetCellIDFromDirrection(lastPos, dir, map, false) == dirCaseID) {
                if (!stopBefore && map.getCellById(dirCaseID).isWalkable()) {
                    rSteps.set(rSteps.get() + steps);
                    return "ok:";
                } else {
                    steps--;
                    rSteps.set(rSteps.get() + steps);
                    return ("stop:" + lastPos);
                }
            } else {
                lastPos = GetCellIDFromDirrection(lastPos, dir, map, false);
            }
        }
        return "no:";
    }

    public static short GetCellIDFromDirrection(short CaseID, char Direction, GameMap map, boolean inFight) {
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
        
        if(d == 14 || d == 15){
            return true;
        }
        return false;
    }
}
