package com.github.irvinglink.ChatFilter.events;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InvalidWordEvent extends Event {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private static final HandlerList handlerList = new HandlerList();

    private final AsyncPlayerChatEvent event;
    private final Player player;
    private final int weight;

    public InvalidWordEvent(AsyncPlayerChatEvent event) {
        this.event = event;
        this.player = event.getPlayer();
        this.weight = plugin.getFilteredPlayers().get(player.getUniqueId());
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Player getPlayer() {
        return this.player;
    }

    public AsyncPlayerChatEvent getChatEvent() {
        return this.event;
    }

    public int getWeight() {
        return weight;
    }

    public String getMessage() {
        return event.getMessage();
    }
}
