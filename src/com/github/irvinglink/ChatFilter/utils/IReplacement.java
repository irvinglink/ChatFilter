package com.github.irvinglink.ChatFilter.utils;

import org.bukkit.OfflinePlayer;

public interface IReplacement {

    String replace(OfflinePlayer player, OfflinePlayer target, String str, String text);

}
