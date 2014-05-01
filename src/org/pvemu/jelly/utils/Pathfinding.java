package org.pvemu.jelly.utils;

import com.sun.jmx.remote.internal.ArrayQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.pvemu.game.objects.map.GameMap;
import java.util.concurrent.atomic.AtomicReference;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;

public class Pathfinding {
    
    static public short validatePath(GameMap map, short startCell, AtomicReference<String> path, boolean inFight){
        short steps = 0;
        short currentCell = startCell;
        short endCell = cellCode_To_ID(path.get().substring(path.get().length() - 2, path.get().length()));
        
        for(int i = 0; i < path.get().length(); i += 3){
            String step = path.get().substring(i, i+3);
            short cellID = cellCode_To_ID(step.substring(1));
            char dir = step.charAt(0);
            boolean stop = false;
            
            int lastSteps = steps;
            for(;;){
                ++steps;
                
                short lastCell = currentCell;
                currentCell = getCellIDFromDirrection(currentCell, dir, map, inFight);
                MapCell cell = map.getCellById(currentCell);
                
                if(cell == null || !cell.isWalkable()){
                    Loggin.debug("Case %d non marchable", currentCell);
                    stop = true;
                }else if(endCell == currentCell && cell.getObj() != null){ //Use the IO
                    Loggin.debug("IO %d trouvé", cell.getObj().getObjID());
                    stop = true;
                }
                
                if(!stop && currentCell == cellID){ //end of the step
                    break;
                }
                
                if(stop){ //blocked
                    --steps;
                    stop = true;
                    currentCell = lastCell;
                    break;
                }
                
                if(steps > lastSteps + 64)
                    return -1000;
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
    
    static private class AStar{
        private class Node{
            final int startDist;
            final int destDist;
            final int totalDist;
            final short parent;

            public Node(int startDist, int destDist, int totalDist, short parent) {
                this.startDist = startDist;
                this.destDist = destDist;
                this.totalDist = totalDist;
                this.parent = parent;
            }

            public int getStartDist() {
                return startDist;
            }

            public int getDestDist() {
                return destDist;
            }

            public int getTotalDist() {
                return totalDist;
            }

            public short getParent() {
                return parent;
            }
            
        }
        
        final private Fight fight;
        final private short start;
        final private short dest;
        final private Map<Short, Node> openList = new HashMap<>();
        final private Map<Short, Node> closeList = new HashMap<>();

        public AStar(Fight fight, short start, short dest) {
            this.fight = fight;
            this.start = start;
            this.dest = dest;
        }
        
        private void addAdjacentCells(short cell){
            for(short c : MapUtils.getAdjencentCells(fight.getFightMap().getMap(), cell)){
                if(!fight.getFightMap().getMap().getCellById(c).isWalkable())
                    continue;
                
                if(c != dest && fight.getFightMap().getFighter(c) != null)
                    continue;
                
                if(!closeList.containsKey(c)){
                    int startDist = closeList.get(cell).getStartDist() + 1;
                    int destDist = MapUtils.getDistanceBetween(fight.getFightMap().getMap(), c, dest);
                    int totalDist = startDist + destDist;
                    Node node = new Node(startDist, destDist, totalDist, cell);
                    
                    if(openList.containsKey(c)){
                        if(node.getTotalDist() < openList.get(c).getTotalDist()){
                            openList.put(c, node);
                        }
                    }else{
                        openList.put(c, node);
                    }
                }
            }
        }
        
        private short getBestNode(){
            Iterator<Entry<Short, Node>> it = openList.entrySet().iterator();
            Entry<Short, Node> best = it.next();
            
            for(Entry<Short, Node> entry = best; it.hasNext(); entry = it.next()){
                if(entry.getValue().getTotalDist() < best.getValue().getTotalDist())
                    best = entry;
            }
            
            return best.getKey();
        }
        
        private void addToCloseList(short cell){
            Node node = openList.remove(cell);
            closeList.put(cell, node);
            
            if(node == null){
                Loggin.warning("Cannot found cell %d in openList", cell);
            }
        }
        
        private LinkedList<Short> getPath(){
            LinkedList<Short> path = new LinkedList<>();
            
            short last = dest;
            
            do{
                Node node = closeList.get(last);
                path.addFirst(node.getParent());
                last = node.getParent();
            }while(last != start);
            
            return path;
        }
        
        public Collection<Short> computePath(){
            short current = start;
            openList.put(start, new Node(0, 0, 0, current));
            
            do{
                Loggin.debug("current cell : %d", current);
                addToCloseList(current);
                addAdjacentCells(current);
                current = getBestNode();
            }while(current != dest && !openList.isEmpty());
            
            addToCloseList(current);
            
            if(current == dest){ //path found
                return getPath();
            }
            
            return null;
        }
    }
    
    static public Collection<Short> findPath(Fight fight, short start, short dest){
        return new AStar(fight, start, dest).computePath();
    }
}
