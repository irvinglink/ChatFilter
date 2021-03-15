package com.github.irvinglink.ChatFilter.handlers;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CooldownHandler {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    private UUID uuid;
    private Long seconds;

    private static final ConcurrentHashMap<UUID, Long> cooldownMap = new ConcurrentHashMap<>();

    public void add(UUID uuid, Long seconds) {

        this.uuid = uuid;
        this.seconds = seconds;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (offlinePlayer.isOnline()) {

            Player player = offlinePlayer.getPlayer();
            assert player != null;

            cooldownMap.put(uuid, System.currentTimeMillis());


        }

    }

    public void remove(UUID uuid) {
        cooldownMap.remove(uuid);
    }

    public boolean isInCooldown(UUID uuid) {

        if (!cooldownMap.containsKey(uuid)) return false;

        if (getTimeLeft(uuid) <= 0) {
            cooldownMap.remove(uuid);
            return false;
        }

        return true;
    }

    public Long getTimeLeft(UUID uuid) {
        long timeLeft = (((cooldownMap.get(uuid) / 1000) + this.seconds) - System.currentTimeMillis() / 1000);
        return (timeLeft < 0) ? 0 : timeLeft;
    }

    public String getTimeString(long seconds) {

        if (seconds >= 3600) {

            long time = TimeUnit.SECONDS.toHours(seconds);

            if (time == 1) return time + " " + plugin.getLang().getString("Time.Hour");

            return time + " " + plugin.getLang().getString("Time.Hours");

        }

        if (seconds >= 60) {

            long time = TimeUnit.SECONDS.toMinutes(seconds);

            if (time == 1) return time + " " + plugin.getLang().getString("Time.Minute");

            return time + " " + plugin.getLang().getString("Time.Minutes");

        }

        if (seconds == 1) return seconds + " " + plugin.getLang().getString("Time.Second");

        return seconds + " " + plugin.getLang().getString("Time.Seconds");

    }

    public UUID getUuid() {
        return uuid;
    }
}
