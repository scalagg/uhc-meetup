package gg.scala.meetup.menu;

import gg.scala.meetup.Meetup;
import gg.scala.meetup.player.GamePlayer;
import io.papermc.lib.PaperLib;
import net.evilblock.cubed.menu.Button;
import net.evilblock.cubed.menu.pagination.PaginatedMenu;
import net.evilblock.cubed.util.CC;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author puugz
 * @since 01/06/2021 20:48
 */
public class SpectateMenu extends PaginatedMenu {

    {
        setAutoUpdate(true);
    }

    @NotNull
    @Override
    public String getPrePaginatedTitle(@NotNull Player player) {
        return "Remaining Players";
    }

    @NotNull
    @Override
    public Map<Integer, Button> getAllPagesButtons(@NotNull Player player) {
        final Map<Integer, Button> buttons = new HashMap<>();

        for (GamePlayer gamePlayer : Meetup.getInstance().getGameHandler().getRemaining()) {
            buttons.put(buttons.size(), new ItemBuilder(Material.SKULL_ITEM)
                    .data((short) 3)
                    .name(gamePlayer.getPlayer().getDisplayName())
                    .addToLore(
                            CC.GRAY + "Health: " + CC.WHITE + this.fetchFancyPercentage(player.getHealth(), 20),
                            CC.GRAY + "Hunger: " + CC.WHITE + this.fetchFancyPercentage(player.getFoodLevel(), 20),
                            "",
                            CC.YELLOW + "Click to teleport."
                    )
                    .owner(gamePlayer.getName())
                    .toButton((clicker, clickType) -> {
                        final Player target = gamePlayer.getPlayer();

                        if (target != null) {
                            PaperLib.teleportAsync(player, target.getLocation()).whenComplete((aBoolean, throwable) -> {
                                player.sendMessage(CC.SEC + "You've teleported to " + target.getDisplayName() + CC.SEC + ".");
                            });
                        }
                    })
            );
        }

        return buttons;
    }

    public String fetchFancyPercentage(double amount, int total) {
        final double percentage = (amount * 100.0f) / total;

        return (percentage > 80 ? CC.GREEN + percentage :
                percentage > 60 ? CC.YELLOW + percentage :
                        percentage > 40 ? CC.RED + percentage :
                                CC.D_RED + percentage) + "%";
    }
}
