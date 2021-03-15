package com.github.irvinglink.ChatFilter.listeners;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.events.InvalidWordEvent;
import com.github.irvinglink.ChatFilter.models.ThresholdAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class InvalidWord implements Listener {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    @EventHandler
    public void onInvalidWord(InvalidWordEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        int weight = plugin.getFilteredPlayers().get(uuid);

        for (ThresholdAction thresholdAction : plugin.getThresholdActions()) {

            player.sendMessage(weight + " | " + thresholdAction.getWeight());

            if (weight >= thresholdAction.getWeight()) {

                if (thresholdAction.isClientMessage() && thresholdAction.isCancelEvent()) player.sendMessage(event.getChatEvent().getFormat());  // ONLY PLAYER CLIENT MESSAGE

                thresholdAction.execute(player); // Executions

                event.getChatEvent().setCancelled(thresholdAction.isCancelEvent()); // Cancel Message
                break;
            }

        }

    }

}
