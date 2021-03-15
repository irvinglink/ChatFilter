package com.github.irvinglink.ChatFilter.listeners;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.events.InvalidWordEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.net.InetSocketAddress;
import java.util.UUID;

public class AsyncPlayerChat implements Listener {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled() || event.getPlayer() == null || event.getMessage() == null) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        InetSocketAddress ip = player.getAddress();

        if (player.hasPermission(plugin.getIgnoredPermission()) || plugin.getWhitelistedIps().contains(ip.toString())) return;

        boolean isValid = plugin.getChatFilterHandler().checkMessage(event);

        if (!isValid) Bukkit.getPluginManager().callEvent(new InvalidWordEvent(event));

    }
}
