/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.models.NpcQuestion;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DialogGenerator {
    public String generateQuestion(NpcQuestion question){
        return question.id + "|" + question.responses + ";4840";
    }
}
