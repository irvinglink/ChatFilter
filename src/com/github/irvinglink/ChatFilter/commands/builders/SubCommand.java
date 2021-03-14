package com.github.irvinglink.ChatFilter.commands.builders;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String getName();

    String getDescription();

    String getSyntax();

    void perform(CommandSender player, String args[]);

}
