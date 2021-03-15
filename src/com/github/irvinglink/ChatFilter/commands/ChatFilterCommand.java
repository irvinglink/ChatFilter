package com.github.irvinglink.ChatFilter.commands;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.commands.builders.CommandBuilder;
import com.github.irvinglink.ChatFilter.commands.builders.SubCommand;
import com.github.irvinglink.ChatFilter.commands.subCommands.AddIpSubCommand;
import com.github.irvinglink.ChatFilter.commands.subCommands.ReloadSubCommand;
import com.github.irvinglink.ChatFilter.commands.subCommands.ToggleCommand;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChatFilterCommand extends CommandBuilder implements TabCompleter {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = this.plugin.getChat();

    private final List<SubCommand> subCommands = Collections.synchronizedList(new ArrayList<>());

    public ChatFilterCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);
        subCommands.add(new ReloadSubCommand());
        subCommands.add(new AddIpSubCommand());
        subCommands.add(new ToggleCommand());

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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return null;

        List<String> output = new ArrayList<>();

        if (args.length == 1) {

            List<String> subCommandStrList = subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());
            subCommandStrList.add("help");

            if (!(args[0].isEmpty())) {

                subCommandStrList.forEach(x -> {
                    if (x.toLowerCase().startsWith((args[0].toLowerCase()))) output.add(x);
                });


            } else return subCommandStrList;


        }

        return output;
    }
}
