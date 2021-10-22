package gg.scala.meetup.handler;

import gg.scala.meetup.commons.PlayerCache;
import gg.scala.meetup.player.GamePlayer;
import net.evilblock.cubed.util.CC;
import gg.scala.meetup.util.MeetupUtil;
import lombok.Getter;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 19/06/2021 22:31
 */
@Getter
public class PlayerHandler extends PlayerCache<GamePlayer> {

	private final ItemStack backToLobby;

	public PlayerHandler() {
		this.backToLobby = new ItemBuilder(Material.BED)
				.name(CC.RED + "Back to Lobby " + CC.GRAY + "(Right Click)")
				.build();
	}

	public void setupInventory(Player player) {
		MeetupUtil.resetPlayer(player, true);

		player.getInventory().setItem(8, this.backToLobby);
		player.updateInventory();
	}
}
