package com.github.irvinglink.ChatFilter.commands.subCommands;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.commands.builders.SubCommand;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class AddIpSubCommand implements SubCommand {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = plugin.getChat();

    @Override
    public String getName() {
        return "addIP";
    }

    @Override
    public String getDescription() {
        return "Add player ip to whitelisted-ips.";
    }

    @Override
    public String getSyntax() {
        return "/chatfilter addIP <player_name>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (args.length == 2) {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

            if (!target.isOnline()) {
                sender.sendMessage(chat.replace(null, target, plugin.getLang().getString("Player_No_Online"), true));
                return;
            }

            String target_ip = target.getPlayer().getAddress().toString();

            if (plugin.getWhitelistedIps().contains(target_ip)) {
                sender.sendMessage(chat.replace(null, target, plugin.getLang().getString("IP_Already_Listed"), true));
                return;
            }

            plugin.getWhitelistedIps().add(target_ip);

            plugin.getConfig().set("whitelisted-ips", plugin.getWhitelistedIps());
            plugin.saveConfig();

            sender.sendMessage(chat.replace(null, target, plugin.getLang().getString("Added_IP"), true));

        } else
            sender.sendMessage(chat.replace(null, getSyntax(), plugin.getLang().getString("Command_Syntax"), true));

    }

}
