/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.parser;

import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.utils.Pair;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Parser {
    /**
     * The start of the element to parse
     * @return 
     */
    public char start();
    
    /**
     * Parse the element
     * @param command the command line
     * @param start the position of the start character
     * @param asker 
     * @return the end position of the element at first and the parsed arguments at second
     */
    public Pair<Integer, List<String>> parse(String command, int start, Asker asker) throws ParserError;
}
