package com.github.irvinglink.ChatFilter.loaders;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.exceptions.CreatingCategoryException;
import com.github.irvinglink.ChatFilter.models.WordCategory;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class WordCategoryLoader implements ILoader {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    @Override
    public void load() {

        FileConfiguration config = plugin.getConfig();

        List<String> lines = config.getStringList("banned-words");

        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);

            int firstIndex = line.indexOf(';');
            int secondIndex = line.indexOf(';', firstIndex + 1);

            int weight = Integer.parseInt(line.substring(0, firstIndex));
            int wordCount = Integer.parseInt(line.substring(firstIndex + 1, secondIndex));

            String words = line.substring(secondIndex + 1);

            List<String> wordList = Arrays.asList(words.split(","));

            plugin.getWordCategories().add(new WordCategory(weight, wordCount, wordList));


        }

    }

    @Override
    public void update() {

        if (!plugin.getWordCategories().isEmpty()) plugin.getWordCategories().clear();
        load();

    }

}
