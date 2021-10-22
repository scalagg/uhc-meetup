package gg.scala.meetup.command;

import net.evilblock.cubed.acf.BaseCommand;
import net.evilblock.cubed.acf.annotation.CommandAlias;
import gg.scala.meetup.Meetup;
import net.evilblock.cubed.util.CC;
import org.bukkit.entity.Player;

/**
 * @author puugz
 * @since 01/06/2021 18:45
 */
public class ResetLoadoutCommand extends BaseCommand {

	@CommandAlias("resetloadout")
	public void execute(Player player) {
		Meetup.getInstance().getPlayerHandler().getByPlayer(player)
				.getLoadout().setupDefaultInventory();

		player.sendMessage(CC.GREEN + "You've reset your loadout.");
	}
}
