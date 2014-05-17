package org.pvemu.jelly.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.jelly.Loggin;

public class Pathfinding {
    
    static public Collection<Short> parsePath(GameMap map, short startCell, String strPath, boolean inFight){
        List<Short> path = new ArrayList<>();
        
        short lastCell = startCell;
        char lastDir;
        for(int i = 0; i < strPath.length(); i += 3){
            String strStep = strPath.substring(i, i+3);
            short stepCell = Crypt.cellCode_To_ID(strStep.substring(1));
            lastDir = strStep.charAt(0);
            List<Short> step = new ArrayList<>(12);
            
            int s = map.getWidth() * 2 + 1;
            
            while(lastCell != stepCell 
                    && map.getCellById(lastCell) != null 
                    && map.getCellById(lastCell).isWalkable()
                    && s-- > 0
            ){
                
                lastCell = MapUtils.getCellIDFromDirrection(lastCell, lastDir, map, inFight);
                step.add(lastCell);
            }
            
            if(s == 0){
                Loggin.debug("Cannot found path !");
                break;
            }
            
            lastCell = stepCell;
            path.addAll(step);
        }
        
        return path;
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
            if(openList.isEmpty())
                return -1;
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
        
        private LinkedList<Short> getPath(boolean addStart, boolean addDest){
            LinkedList<Short> path = new LinkedList<>();
            
            short last = addDest ? dest : closeList.get(dest).getParent();
            
            do{
                path.addFirst(last);
                Node node = closeList.get(last);
                last = node.getParent();
            }while(last != start);
            
            if(addStart)
                path.addFirst(start);
            
            return path;
        }
        
        public Collection<Short> computePath(boolean addStart, boolean addDest){
            short current = start;
            openList.put(start, new Node(0, 0, 0, current));
            
            do{
                addToCloseList(current);
                addAdjacentCells(current);
                current = getBestNode();
                
                if(current == -1) //there is no nodes
                    return null;
            }while(current != dest && !openList.isEmpty());
            
            addToCloseList(current);
            
            if(current == dest){ //path found
                return getPath(addStart, addDest);
            }
            
            return null;
        }
    }
    
    static public Collection<Short> findPath(Fight fight, short start, short dest, boolean addStart, boolean addDest){
        return new AStar(fight, start, dest).computePath(addStart, addDest);
    }
    
    static public Collection<Short> findPath(Fight fight, short start, short dest, boolean addStart, boolean addDest, int limit){
        Collection<Short> path = findPath(fight, start, dest, addStart, addDest);
        
        if(path == null)
            return null;
        
        List<Short> newPath = new ArrayList<>(limit);
        Iterator<Short> it = path.iterator();
        
        while(it.hasNext() && limit-- > 0){
            newPath.add(it.next());
        }
        
        return newPath;
    }
}