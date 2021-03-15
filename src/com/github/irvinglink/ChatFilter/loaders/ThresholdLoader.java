package com.github.irvinglink.ChatFilter.loaders;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.models.ThresholdAction;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ThresholdLoader implements ILoader {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    @Override
    public void load() {

        FileConfiguration config = plugin.getConfig();

        String path = "threshold-action";

        if (!config.contains(path) || !config.isConfigurationSection(path)) return;

        for (String weightThreshold : config.getConfigurationSection(path).getKeys(false)) {

            try {

                int weight = Integer.parseInt(weightThreshold);
                boolean clientMessage = config.getBoolean(path + "." + weight + ".action.show-only-client");
                boolean cancelEvent = config.getBoolean(path + "." + weight + ".action.cancel-msg-event");

                List<String> executions = config.getStringList(path + "." + weight + ".executions");

                plugin.getThresholdActions().add(new ThresholdAction(weight, clientMessage, cancelEvent, executions));

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }

        plugin.getThresholdActions().sort(ThresholdAction.WeightComparator);

    }

    @Override
    public void update() {
        if (!plugin.getThresholdActions().isEmpty()) plugin.getThresholdActions().clear();
        load();
    }

}
