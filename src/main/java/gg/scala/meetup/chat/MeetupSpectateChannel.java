package gg.scala.meetup.chat;

import gg.scala.lemon.player.channel.Channel;
import gg.scala.lemon.player.rank.Rank;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author GrowlyX
 * @since 6/23/2021
 */

public class MeetupSpectateChannel implements Channel {

    @Nullable
    @Override
    public String getPermission() {
        return null;
    }

    @Nullable
    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public boolean shouldCheckForPrefix() {
        return false;
    }

    @NotNull
    @Override
    public String colorIfHasPermission(@NotNull Player player, @NotNull String message) {
        return null;
    }

    @NotNull
    @Override
    public String dePrefixed(@NotNull String message) {
        return null;
    }

    @NotNull
    @Override
    public String getFormatted(@NotNull String message, @NotNull String sender, @NotNull Rank rank, @NotNull Player receiver) {
        return null;
    }

    @NotNull
    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean hasPermission(@NotNull Player t) {
        return t.hasMetadata("spectator");
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public boolean isPrefixed(@NotNull String message) {
        return false;
    }
}
