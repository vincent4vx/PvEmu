/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.i18n.translation;

import org.pvemu.common.i18n.Translation;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public enum Commands implements Translation{
    UNAVAILABLE_COMMAND("Command `%s` is unavailable"),
    EXECUTION_ERROR("Error during executing `%s`"),
    INVALID_ARGUMENT("Invalid argument %d"),
    DEBUG_STATE("DEBUG Mode %s"),
    ENABLE("enable"),
    DISABLE("disable"),
    DEBUG_USAGE1("enable / disable DEBUG Mode"),
    DEBUG_USAGE2("debug : switch current DEBUG mode"),
    DEBUG_USAGE3("debug [true/false] : enable  disable DEBUG mode"),
    UNDOCUMENTED("This command was not documented"),
    NO_ALIAS_FOUND("No alias available"),
    ALIAS_ALREADY_EXISTS("Alias or command `%s` already exists"),
    ALIAS_CREATED("Alias `%s` created"),
    ALIAS_USAGE1("Handle aliases"),
    ALIAS_USAGE2("alias : list aliases"),
    ALIAS_USAGE3("alias [name] [cmdLine] : create the alias [name] as [cmdLine]"),
    ECHO_USAGE1("Display a text"),
    ECHO_USAGE2("echo {param1 {...}} : display parameters"),
    INVALID_ARGUMENTS_NUMBER("Invalid arguments number"),
    EXEC_USAGE1("Load an execute javascript files"),
    EXEC_USAGE2("exec [file1 {file2...}] : execute file1, file2..."),
    EXIT_USAGE1("Stop immediatly the server without saving"),
    EXIT_USAGE2("exit : stop the server"),
    EXIT_USAGE3("Warning : use this command cautiously"),
    HELP_USAGE1("Get available commands or usage of one command"),
    HELP_USAGE2("help : get available commands list"),
    HELP_USAGE3("help [cmd] : get the usage of [cmd]"),
    AVAILABLE_COMMANDS("Available commands :"),
    NO_COMMANDS_AVAILABLE("No commands available"),
    COMMAND("Command : %s"),
    USAGE("Usage :"),
    ITEM_USAGE1("Add item to player(s)"),
    ITEM_USAGE2("item [itemID]  {{qu = 1 {stats : max|rand = rand} {player1 = %me {player2...}}}} : add item [itemID] to players {player1 {player2...}} with stats {stats}"),
    ITEM_NOT_FOUND("Item %d was not found"),
    ITEM_GENERATED("Item '%s' was generated successfuly for player '%s'"),
    PACKET_SENT("%s : packet sent"),
    TELEPORT_USAGE1("Teleport the player to the destination"),
    TELEPORT_USAGE2("teleport [mapID] {cellID} : teleport player to [mapID] at cell {cellID}"),
    LOCKED_ARG_LIST("Cannot add an argument to a locked argument list"),
    UNAUTHORIZED_EMPTY_LIST("empty list is not authorized here"),
    REQUIRED_ARGUMENT("it was required"),
    INVALID_NUMBER("not a valid number"),
    INVALID_INT_LIST("invalid integer list"),
    PARSER_ERROR("[Parser error] %s at column %d near : '%s'"),
    UNEXPECTED_CHAR("unexpected character %s here"),
    CAN_ACCESS_VAR("Only moderators can access to vars"),
    VAR_NOT_FOUND("Variable '%s' not exists"),
    ;
    
    final private String tr;

    private Commands(String tr) {
        this.tr = tr;
    }

    @Override
    public String defaultTranslation() {
        return tr;
    }
}
