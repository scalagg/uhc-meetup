package gg.scala.meetup.menu;

import gg.scala.meetup.Meetup;
import gg.scala.meetup.player.GamePlayer;
import net.evilblock.cubed.menu.Button;
import net.evilblock.cubed.menu.Menu;
import net.evilblock.cubed.util.CC;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GrowlyX
 * @since 6/6/2021
 */

public class LoadoutEditorMenu extends Menu {

    private static final ItemStack RED_GLASS = new ItemBuilder(Material.STAINED_GLASS_PANE)
            .data((short) 14)
            .name(ChatColor.RED + "Do not touch!")
            .addToLore(
                    "&7You shouldn't be able",
                    "&7to add items to your",
                    "&7own inventory while",
                    "&7editing loadouts!"
            )
            .build();

    @Override
    public int size(@NotNull Map<Integer, ? extends Button> buttons) {
        return 36;
    }

    @NotNull
    @Override
    public String getTitle(@NotNull Player player) {
        return "Editing Loadout";
    }

    @NotNull
    @Override
    public Map<Integer, Button> getButtons(@NotNull Player player) {
        final Map<Integer, Button> buttonMap = new HashMap<>();
        final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(player);

        gamePlayer.getLoadout().getInventoryLocationMap()
                .forEach((integer, itemStack) -> buttonMap.put(
                        integer >= 0 && integer <= 8 ? integer + 27 : integer - 9,
                        new ItemBuilder(itemStack).toButton()
                ));

        return buttonMap;
    }

    @Override
    public void onOpen(Player player) {
        player.getInventory().clear();

        while (player.getInventory().firstEmpty() != -1) {
            final int firstEmpty = player.getInventory().firstEmpty();

            player.getInventory().setItem(firstEmpty, LoadoutEditorMenu.RED_GLASS);
        }

        player.updateInventory();
        player.sendMessage(CC.GREEN + "You're now editing your loadout.");
    }

    @Override
    public void onClose(@NotNull Player player, boolean manualClose) {
        final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(player);

        for (int i = 0; i <= 35; i++) {
            gamePlayer.getLoadout().getInventoryLocationMap()
                    .put(i >= 27 ? i - 27 : i + 9, player.getOpenInventory().getItem(i));
        }

        player.sendMessage(CC.SEC + "You've modified your loadout!");
        player.sendMessage(CC.I_GRAY + "If you need to reset your loadout try " + CC.I_YELLOW + "/resetloadout");

        player.getInventory().clear();
    }
}
