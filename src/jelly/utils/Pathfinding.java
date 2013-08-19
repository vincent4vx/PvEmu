package jelly.utils;

import game.objects.GameMap;
import java.util.concurrent.atomic.AtomicReference;

public class Pathfinding {

    public static int isValidPath(GameMap map, int cellID, AtomicReference<String> pathRef) {
        //_nSteps = 0;
        AtomicReference<Integer> rSteps = new AtomicReference<>(0);
        int newPos = cellID;
        //int Steps = 0;
        String path = pathRef.get();
        String newPath = "";
        for (int i = 0; i < path.length(); i += 3) {
            String SmallPath = path.substring(i, i + 3);
            char dir = SmallPath.charAt(0);
            int dirCaseID = cellCode_To_ID(SmallPath.substring(1));
            //_nSteps = 0;

            String[] aPathInfos = ValidSinglePath(newPos, SmallPath, map, rSteps).split(":");
            if (aPathInfos[0].equalsIgnoreCase("stop")) {
                newPos = Integer.parseInt(aPathInfos[1]);
                //Steps += _nSteps;
                newPath += dir + cellID_To_Code(newPos);
                pathRef.set(newPath);
                return rSteps.get();
            } else if (aPathInfos[0].equalsIgnoreCase("ok")) {
                newPos = dirCaseID;
                //Steps += _nSteps;
            } else {
                pathRef.set(newPath);
                return -1000;
            }
            newPath += dir + cellID_To_Code(newPos);
        }
        pathRef.set(newPath);
        return rSteps.get();
    }

    public static String ValidSinglePath(int CurrentPos, String Path, GameMap map, AtomicReference<Integer> rSteps) {
        int steps = 0;
        char dir = Path.charAt(0);
        int dirCaseID = cellCode_To_ID(Path.substring(1));
        int lastPos = CurrentPos;
        for (steps = 1; steps <= 64; steps++) {

            if (GetCellIDFromDirrection(lastPos, dir, map, false) == dirCaseID) {
                /*if (map.getCellById(lastPos).isTrigger()) {
                 return "stop:" + lastPos;
                 }*/

                if (map.getCellById(dirCaseID).isWalkable()) {
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

            /*if (fight != null && fight.isOccuped(lastPos)) {
             return "no:";
             }*/
            /*if (fight != null) {
             if (getEnemyFighterArround(lastPos, map, fight) != null)//Si ennemie proche
             {
             return "stop:" + lastPos;
             }
             for (Piege p : fight.get_traps()) {
             int dist = getDistanceBetween(map, p.get_cell().getID(), lastPos);
             if (dist <= p.get_size()) {
             //on arrete le déplacement sur la 1ere case du piege
             return "stop:" + lastPos;
             }
             }
             }*/

        }
        return "no:";
    }

    public static int GetCellIDFromDirrection(int CaseID, char Direction, GameMap map, boolean inFight) {
        switch (Direction) {
            case 'a':
                return inFight ? -1 : CaseID + 1;
            case 'b':
                return CaseID + map.getWidth();
            case 'c':
                return inFight ? -1 : CaseID + (map.getWidth() * 2 - 1);
            case 'd':
                return CaseID + (map.getWidth() - 1);
            case 'e':
                return inFight ? -1 : CaseID - 1;
            case 'f':
                return CaseID - map.getWidth();
            case 'g':
                return inFight ? -1 : CaseID - (map.getWidth() * 2 - 1);
            case 'h':
                return CaseID - map.getWidth() + 1;
        }
        return -1;
    }

    public static int cellCode_To_ID(String cellCode) {
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        char char1 = cellCode.charAt(0), char2 = cellCode.charAt(1);
        int code1 = 0, code2 = 0, a = 0;
        while (a < HASH.length) {
            if (HASH[a] == char1) {
                code1 = a * 64;
            }
            if (HASH[a] == char2) {
                code2 = a;
            }
            a++;
        }
        return (code1 + code2);
    }

    public static String cellID_To_Code(int cellID) {
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};

        int char1 = cellID / 64, char2 = cellID % 64;
        return HASH[char1] + "" + HASH[char2];
    }
}
