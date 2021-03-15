package com.github.irvinglink.ChatFilter.loaders;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;

public class WhitelistIPLoader implements ILoader {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    @Override
    public void load() {

        if (!plugin.getConfig().contains("whitelisted-ips")) return;

        plugin.getConfig().getStringList("whitelisted-ips").forEach(x -> plugin.getWhitelistedIps().add(x));

    }

    @Override
    public void update() {

        if (!plugin.getWhitelistedIps().isEmpty()) plugin.getWhitelistedIps().clear();
        load();

    }
}
