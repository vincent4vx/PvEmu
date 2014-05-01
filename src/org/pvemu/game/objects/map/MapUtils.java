package org.pvemu.game.objects.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class MapUtils {

    static public class Coordinates {

        final private int x, y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    /**
     * test if the destination is valid
     *
     * @param mapID
     * @param cellID
     * @return
     */
    public static boolean isValidDest(short mapID, short cellID) {
        GameMap map = MapFactory.getById(mapID);

        if (map == null) { //map inexistante
            return false;
        }

        MapCell cell = map.getCellById(cellID);

        return cell != null && cell.isWalkable();
    }

    /**
     * Get a random valid (i.e. walkable) cell in a map
     *
     * @param map
     * @return
     */
    static public MapCell getRandomValidCell(GameMap map) {
        MapCell cell;
        int size = map.getCells().size();

        do {
            cell = map.getCellById((short) Utils.rand(size));
        } while (!cell.isWalkable());

        return cell;
    }

    static public List<Short> parseCellList(String cellList, GameMap map) {
        List<Short> list = new ArrayList<>(cellList.length() / 2);

        for (int i = 0; i < cellList.length(); i += 2) {
            short cell = Crypt.cellCode_To_ID(cellList.substring(i, i + 2));

            if (map.getCellById(cell) != null && map.getCellById(cell).isWalkable()) {
                list.add(cell);
            }
        }

        return list;
    }

    static public Coordinates getCellCoordinates(GameMap map, short cell) {
        int width = map.getWidth();
        int loc5 = (int) (cell / ((width * 2) - 1));
        int loc6 = cell - loc5 * ((width * 2) - 1);
        int loc7 = loc6 % width;

        int y = loc5 - loc7;
        int x = ((cell - (width - 1) * y) / width);

        return new Coordinates(x, y);
    }

    static public int getDistanceBetween(GameMap map, short cell1, short cell2) {
        Coordinates c1 = getCellCoordinates(map, cell1);
        Coordinates c2 = getCellCoordinates(map, cell2);

        int diffX = Math.abs(c1.getX() - c2.getX());
        int diffY = Math.abs(c1.getY() - c2.getY());

        return (int) Math.ceil(Math.sqrt(diffX * diffX + diffY * diffY));
    }

    static public Collection<Short> getCellsFromArea(GameMap map, short start, short casterCell, String area) {
        byte size = Crypt.getHashIndex(area.charAt(1));
        Collection<Short> cells = new HashSet<>();
        cells.add(start);

        switch (area.charAt(0)) {
            case 'C': {//circle
                Collection<Short> lastCells = new HashSet<>(cells);
                for (byte i = 0; i < size; ++i) {
                    Collection<Short> aroundCells = new HashSet<>(lastCells.size() * 4);
                    for (short cell : lastCells) {
                        aroundCells.addAll(getCellsAround(map, cell));
                    }
                    lastCells.clear();
                    lastCells.addAll(aroundCells);
                    cells.addAll(aroundCells);
                }
            }
            break;
            case 'X': {//cross
                short lastCell = start;
                for (char d : new char[]{'b', 'd', 'f', 'h'}) {
                    for (byte i = 0; i < size; ++i) {
                        short cell = getCellIDFromDirrection(lastCell, d, map, true);

                        if (cell == -1) {
                            break;
                        }

                        lastCell = cell;
                        cells.add(cell);
                    }
                }
            }
            break;
            case 'L':{//vertical line
                char dir = getDirBetweenTwoCase(casterCell, start, map, true);
                short lastCell = start;
                for(byte i = 0; i < size; ++i){
                    short cell = getCellIDFromDirrection(lastCell, dir, map, true);
                    
                    if(cell != -1){
                        lastCell = cell;
                        cells.add(cell);
                    }
                }
            }break;
            case 'H':{//horizontal line
                char dir = getDirBetweenTwoCase(casterCell, start, map, true);
                short cl = getCellIDFromDirrection(start, (char)(dir - 1), map, true);
                short cr = getCellIDFromDirrection(start, (char)(dir + 1), map, true);
                
                if(cl != -1)
                    cells.add(cl);
                
                if(cr != -1)
                    cells.add(cr);
            }break;
        }

        return cells;
    }

    static public Collection<Short> getCellsAround(GameMap map, short cell) {
        Collection<Short> cells = new HashSet<>(4);

        for (char c : new char[]{'b', 'd', 'f', 'h'}) {
            short cur = getCellIDFromDirrection(cell, c, map, true);

            if (cur != -1) {
                cells.add(cur);
            }
        }

        return cells;
    }

    public static char getDirBetweenTwoCase(short cell1ID, short cell2ID, GameMap map, boolean inFight) {
        Collection<Character> dirs = new HashSet<>();
        dirs.add('b');
        dirs.add('d');
        dirs.add('f');
        dirs.add('h');
        if (!inFight) {
            dirs.add('a');
            dirs.add('b');
            dirs.add('c');
            dirs.add('d');
        }
        for (char c : dirs) {
            short cell = cell1ID;
            for (byte i = 0; i <= 64; i++) {
                if (getCellIDFromDirrection(cell, c, map, inFight) == cell2ID) {
                    return c;
                }
                cell = getCellIDFromDirrection(cell, c, map, inFight);
            }
        }
        return 0;
    }
    
    static public Set<Short> getAdjencentCells(GameMap map, short cell){
        Set<Short> cells = new HashSet<>(4);
        
        if(cell > 15)
            cells.add((short)(cell - 15));
        
        if(cell > 14)
            cells.add((short)(cell - 14));
        
        int size = map.getCells().size();
        
        if(cell + 15 < size)
            cells.add((short)(cell + 15));
        
        if(cell + 14 < size)
            cells.add((short)(cell + 14));
        
        return cells;
    }
    
    
    /**
     * Test if cells are adjencents (directly near)
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
}
