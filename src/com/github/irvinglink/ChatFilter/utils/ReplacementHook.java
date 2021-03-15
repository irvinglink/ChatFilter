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

            case "player":
                if (player != null) return player.getName();

            case "target":
                if (target != null) return target.getName();

            case "weight":
                if (player != null) return String.valueOf(plugin.getFilteredPlayers().get(player.getUniqueId()));

            default:
                return null;

        }

    }

}
