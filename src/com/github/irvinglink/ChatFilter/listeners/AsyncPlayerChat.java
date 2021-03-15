package com.github.irvinglink.ChatFilter.listeners;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class AsyncPlayerChat implements Listener {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled() || event.getPlayer() == null || event.getMessage() == null) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (player.hasPermission(plugin.getIgnoredPermission())) return;

        boolean isValid = plugin.getChatFilterHandler().checkMessage(event);

        if (!isValid) {
            player.sendMessage("Contains in valid words"); // DEBUG
            event.setCancelled(true);
        }

    }
}
