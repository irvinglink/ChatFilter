package com.github.irvinglink.ChatFilter.utils;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import org.bukkit.OfflinePlayer;

public class ReplacementHook implements IReplacement {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    private final Chat chat = this.plugin.getChat();

    public String replace(OfflinePlayer player, OfflinePlayer target, String str, String var) {

        switch (var) {
            case "prefix":
                return this.chat.getPrefix();

            case "str":
            case "command_syntax":
                return str;

            default:
                return null;

        }

    }

}
