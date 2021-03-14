package com.github.irvinglink.ChatFilter.commands;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.commands.builders.CommandBuilder;
import com.github.irvinglink.ChatFilter.commands.builders.SubCommand;
import com.github.irvinglink.ChatFilter.commands.subCommands.ReloadSubCommand;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatFilterCommand extends CommandBuilder {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = this.plugin.getChat();

    private final List<SubCommand> subCommands = Collections.synchronizedList(new ArrayList<>());

    public ChatFilterCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);
        subCommands.add(new ReloadSubCommand());

    }

    @Override
    protected void execute(CommandSender sender, String[] args) {

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {

            sender.sendMessage(" ");

            for (int i = subCommands.size() - 1; i >= 0; i--) {

                SubCommand subCommand = subCommands.get(i);

                StringBuilder x = new StringBuilder();
                x.append("&e");
                x.append(subCommand.getSyntax());
                x.append(" &7");
                x.append(subCommand.getDescription());

                sender.sendMessage(chat.str(x.toString()));

            }

            sender.sendMessage(" ");

            return;
        }

        for (int i = 0; i < subCommands.size(); i++) {
            SubCommand subCommand = subCommands.get(i);

            if (args[0].equalsIgnoreCase(subCommand.getName().toLowerCase())) {
                subCommand.perform(sender, args);
                return;
            }

        }

        sender.sendMessage(chat.replace(plugin.getLang().getString("Unknown_Command"), true));

    }

}
