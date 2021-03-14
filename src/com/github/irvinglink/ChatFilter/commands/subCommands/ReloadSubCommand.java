package com.github.irvinglink.ChatFilter.commands.subCommands;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.commands.builders.SubCommand;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements SubCommand {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = plugin.getChat();

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload configuration files.";
    }

    @Override
    public String getSyntax() {
        return "/chatfilter reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (args.length == 1) {

            plugin.reloadConfig();

            plugin.getWordCategoryLoader().update();

            sender.sendMessage(chat.replace(null, getSyntax(), plugin.getLang().getString("Reloaded_Configuration_Files"), true));

        } else
            sender.sendMessage(chat.replace(null, getSyntax(), plugin.getLang().getString("Command_Syntax"), true));

    }
}
