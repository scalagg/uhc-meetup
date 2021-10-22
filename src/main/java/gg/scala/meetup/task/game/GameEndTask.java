package gg.scala.meetup.task.game;

import gg.scala.grape.GrapeSpigotPlugin;
import gg.scala.grape.player.impl.PersistentGrapePlayer;
import gg.scala.lemon.Lemon;
import gg.scala.meetup.Meetup;
import gg.scala.meetup.game.Game;
import gg.scala.meetup.player.GamePlayer;
import net.evilblock.cubed.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author GrowlyX
 * @since 6/6/2021
 */

public class GameEndTask extends BukkitRunnable {

	public GameEndTask() {
		this.runTaskTimer(Meetup.getInstance(), 0L, 20L);
	}

	@Override
	public void run() {
		final Game game = Meetup.getInstance().getGameHandler().getGame();

		if (game.getEndTime() == 8) {
			Lemon.setCanJoin(false);

			for (Player player : Bukkit.getOnlinePlayers()) {
				final boolean winner = game.getWinnerId().equals(player.getUniqueId());
				final PersistentGrapePlayer grapePlayer = GrapeSpigotPlugin.getInstance()
						.getPlayerHandler().getByPlayer(player);

				grapePlayer.setCoins(grapePlayer.getCoins() + (winner ? 150 : 15));

				final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(player);

				if (winner) {
					gamePlayer.setReRolls(gamePlayer.getReRolls() + 1);
					player.sendMessage(CC.SEC + "You've received " + CC.PRI + "1" + CC.SEC + " kit re-roll!");
				}
			}
		}

		if (game.getEndTime() == 5) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.kickPlayer(game.getWinner() + CC.GREEN + " has won the game, thanks for playing!");
			}
		}

		if (game.getEndTime() <= 0) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
			this.cancel();
			return;
		}

		game.setEndTime(game.getEndTime() - 1);
	}
}
