package com.github.irvinglink.ChatFilter.commands.builders;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class CommandBuilder implements CommandExecutor {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = this.plugin.getChat();

    private final String cmdName;
    private final String permission;

    private final boolean console;

    public CommandBuilder(String cmdName, String permission, boolean console) {
        this.cmdName = cmdName;
        this.permission = permission;
        this.console = console;

        Objects.requireNonNull(this.plugin.getCommand(cmdName)).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!cmd.getLabel().equalsIgnoreCase(this.cmdName)) return true;

        if (!sender.hasPermission(this.permission)) {
            sender.sendMessage(this.chat.replace(this.plugin.getLang().getString("No_Permission"), true));
            return true;
        }

        StringBuilder x = new StringBuilder();

        if (!this.console && !(sender instanceof Player)) {
            x.append("&aOnly players can use this command");
            sender.sendMessage(this.chat.str(x.toString()));
            return true;
        }

        execute(sender, args);
        return true;

    }

    protected abstract void execute(CommandSender sender, String[] args);
}
