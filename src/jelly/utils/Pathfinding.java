package jelly.utils;

import game.objects.GameMap;
import java.util.concurrent.atomic.AtomicReference;

public class Pathfinding {

    public static int isValidPath(GameMap map, int cellID, AtomicReference<String> pathRef) {
        int newPos = cellID;
        int lastPos = cellID;
        String path = pathRef.get();
        StringBuilder newPath = new StringBuilder();
        AtomicReference<Integer> rSteps = new AtomicReference<>();
        rSteps.set(0);

        for (int i = 0; i < path.length(); i += 3) {
            String SmallPath = path.substring(i, i + 3);
            char dir = SmallPath.charAt(0);

            newPos = ValidSinglePath(newPos, SmallPath, map, rSteps);
            if(newPos == -1){
                pathRef.set(newPath.toString());
                return rSteps.get();
            }else if (newPos != lastPos) {
                newPath.append(dir).append(cellID_To_Code(newPos));
                pathRef.set(newPath.toString());
            } else {
                pathRef.set(newPath.toString());
                return -1000;
            }
            newPath.append(dir).append(cellID_To_Code(newPos));
        }

        pathRef.set(newPath.toString());
        return rSteps.get();
    }

    public static int ValidSinglePath(int CurrentPos, String Path, GameMap map, AtomicReference<Integer> rSteps) {
        char dir = Path.charAt(0);
        int dirCellID = cellCode_To_ID(Path.substring(1));
        int lastPos = CurrentPos;
        int steps;
        for (steps = 1; steps <= 64; steps++) {
            if (GetCellIDFromDirrection(lastPos, dir, map, false) == dirCellID) {
                if (map.getCellById(dirCellID).isWalkable()) {
                    rSteps.set(rSteps.get() + steps);
                    return dirCellID;
                } else {
                    rSteps.set(rSteps.get() + steps);
                    return lastPos;
                }
            } else {
                lastPos = GetCellIDFromDirrection(lastPos, dir, map, false);
            }
        }
        return -1;
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
