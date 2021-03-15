package com.github.irvinglink.ChatFilter.handler;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.models.WordCategory;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFilterHandler {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = this.plugin.getChat();

    public boolean checkMessage(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String message = event.getMessage();

        final List<WordCategory> wordCategories = plugin.getWordCategories();

        int count = 0;

        for (WordCategory category : wordCategories) {

            List<String> regexList = category.getRegexList();

            for (int i = 0; i < regexList.size(); i++) {

                Pattern pattern = Pattern.compile(regexList.get(i));
                Matcher matcher = pattern.matcher(message);

                while (matcher.find()) {
                    System.out.println(matcher.group(0));
                    count++;
                }

            }

            if (count >= category.getWeight()) return false;

        }

        return true;
    }

}
