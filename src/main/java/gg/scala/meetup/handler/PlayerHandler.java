package gg.scala.meetup.handler;

import gg.scala.meetup.commons.PlayerCache;
import gg.scala.meetup.player.GamePlayer;
import gg.scala.meetup.util.MeetupUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * @author puugz
 * @since 19/06/2021 22:31
 */
@Getter
public class PlayerHandler extends PlayerCache<GamePlayer> {

	public void setupInventory(Player player) {
		MeetupUtil.resetPlayer(player, true);
	}
}
