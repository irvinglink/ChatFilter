package com.github.irvinglink.ChatFilter.models;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;
import com.github.irvinglink.ChatFilter.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ThresholdAction {

    private final ChatFilterPlugin plugin = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);
    private final Chat chat = plugin.getChat();

    private final int weight;
    private final boolean clientMessage, cancelEvent;

    private final List<String> executions;

    public ThresholdAction(int weight, boolean clientMessage, boolean cancelEvent, List<String> executions) {
        this.weight = weight;

        this.clientMessage = clientMessage;
        this.cancelEvent = cancelEvent;

        this.executions = executions;
    }

    public boolean isClientMessage() {
        return clientMessage;
    }

    public boolean isCancelEvent() {
        return cancelEvent;
    }

    public List<String> getExecutions() {
        return executions;
    }

    public void execute(Player player) {

        for (int i = executions.size() - 1; i >= 0; i--) {

            String reward = executions.get(i);

            String[] rewardArgs = reward.split(" ", 2);

            switch (rewardArgs[0].toLowerCase()) {

                case "[message]":
                    player.sendMessage(chat.replace(player, rewardArgs[1], true));
                    break;

                case "[console]":
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), chat.replace(player, rewardArgs[1], true));
                        }
                    }.runTask(plugin);

            break;

            case "[player]":
                if (player.isOnline()) {

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            Objects.requireNonNull(player.getPlayer()).performCommand(chat.replace(player, rewardArgs[1], true));
                        }

                    }.runTask(plugin);
                }

                break;

            default:
                break;

        }

    }

}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThresholdAction that = (ThresholdAction) o;
        return clientMessage == that.clientMessage &&
                cancelEvent == that.cancelEvent &&
                Objects.equals(executions, that.executions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientMessage, cancelEvent, executions);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ThresholdAction{")
                .append("clientMessage=").append(clientMessage).append(", cancelEvent=")
                .append(cancelEvent).append(", executions=").append(executions).append("}").toString();
    }

    public int getWeight() {
        return weight;
    }

    public static Comparator<ThresholdAction> WeightComparator = (action1, action2) -> action2.getWeight() - action1.getWeight();

}
