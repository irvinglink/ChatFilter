package com.github.irvinglink.ChatFilter.models;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class ThresholdAction {

    private final boolean clientMessage, cancelEvent;

    private final List<String> executions;

    public ThresholdAction(boolean clientMessage, boolean cancelEvent, List<String> executions) {
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
}
