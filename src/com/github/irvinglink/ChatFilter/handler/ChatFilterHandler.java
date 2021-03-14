package com.github.irvinglink.ChatFilter.handler;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.model.WordCategory;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class ChatFilterHandler {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = this.plugin.getChat();

    public boolean checkMessage(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String message = event.getMessage();

        final List<WordCategory> wordCategories = plugin.getWordCategories();

        for (WordCategory category : wordCategories) {

            Pattern pattern = category.getPattern();
            


        }

        return true;
    }

}
