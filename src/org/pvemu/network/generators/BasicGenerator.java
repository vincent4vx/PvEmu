/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.pvemu.jelly.Constants;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class BasicGenerator {
    public String generateDate(){
        Date date = new Date(System.currentTimeMillis() + Constants.TIME_CORRECTION);
        DateFormat df = new SimpleDateFormat("Y|MM|dd|");
        return df.format(date);
    }
    
    public long generateTime(){
        return System.currentTimeMillis() + Constants.TIME_CORRECTION;
    }
}
