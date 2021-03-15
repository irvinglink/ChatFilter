package com.github.irvinglink.ChatFilter.listeners;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.events.InvalidWordEvent;
import com.github.irvinglink.ChatFilter.handlers.CooldownHandler;
import com.github.irvinglink.ChatFilter.models.ThresholdAction;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class InvalidWord implements Listener {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = plugin.getChat();
    private final CooldownHandler cooldownHandler = plugin.getCooldownHandler();

    @EventHandler
    public void onInvalidWord(InvalidWordEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        int weight = plugin.getFilteredPlayers().get(uuid);

        for (ThresholdAction thresholdAction : plugin.getThresholdActions()) {

            if (weight >= thresholdAction.getWeight()) {

                cooldownHandler.add(uuid, plugin.getConfig().getLong("clear-weight-cooldown"));

                if (thresholdAction.isClientMessage() || thresholdAction.isCancelEvent()) {  // CANCEL CHAT EVENT
                    event.getChatEvent().setCancelled(true);

                    if (thresholdAction.isClientMessage()) // ONLY PLAYER CLIENT MESSAGE
                        player.sendMessage(chat.replace(player, event.getMessage(), plugin.getConfig().getString("messages.show-only-client-player"), true));

                    else
                        plugin.getTogglePlayers().forEach(x -> { // LOG MESSAGE
                            OfflinePlayer toggledPlayer = Bukkit.getOfflinePlayer(x);

                            if (toggledPlayer.isOnline())
                                toggledPlayer.getPlayer().sendMessage(chat.replace(player, event.getMessage(), plugin.getConfig().getString("messages.show-only-client-log"), true));

                        });
                }

                thresholdAction.execute(player); // THRESHOLD EXECUTIONS

                break;
            }

        }

    }
}
