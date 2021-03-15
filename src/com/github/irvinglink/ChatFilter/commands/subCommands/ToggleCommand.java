package com.github.irvinglink.ChatFilter.commands.subCommands;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.commands.builders.SubCommand;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ToggleCommand implements SubCommand {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = plugin.getChat();

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getDescription() {
        return "Toggle log messages";
    }

    @Override
    public String getSyntax() {
        return "/chatfilter toggle";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {


        if (args.length == 1) {

            if (!(sender instanceof Player))
                sender.sendMessage(chat.replace(plugin.getLang().getString("No_Player_Command"), true));

            assert sender instanceof Player;

            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (!plugin.getTogglePlayers().contains(uuid)) { // TOGGLE ON

                plugin.getTogglePlayers().add(uuid);
                sender.sendMessage(chat.replace(plugin.getLang().getString("Toggle_Filter_Enabled"), true));

            } else { // TOGGLE OFF

                plugin.getTogglePlayers().remove(uuid);
                sender.sendMessage(chat.replace(plugin.getLang().getString("Toggle_Filter_Disabled"), true));

            }


        } else
            sender.sendMessage(chat.replace(null, getSyntax(), plugin.getLang().getString("Command_Syntax"), true));

    }
}
