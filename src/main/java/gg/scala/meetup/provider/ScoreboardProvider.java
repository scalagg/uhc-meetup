package gg.scala.meetup.provider;

import gg.scala.meetup.Meetup;
import gg.scala.meetup.game.Game;
import gg.scala.meetup.handler.GameHandler;
import gg.scala.meetup.player.GamePlayer;
import net.evilblock.cubed.scoreboard.ScoreboardAdapter;
import net.evilblock.cubed.util.CC;
import net.evilblock.cubed.util.nms.MinecraftReflection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author puugz
 * @since 18/06/2021 22:57
 */
public class ScoreboardProvider extends ScoreboardAdapter {

	private String getFormattedPing(int ping) {
		if (ping > 300) {
			return CC.D_RED + ping + " ms";
		} else if (ping > 150){
			return CC.PRI + ping + " ms";
		} else if (ping > 80){
			return CC.YELLOW + ping + " ms";
		} else {
			return CC.GREEN + ping + " ms";
		}
	}

	@Override
	public void getLines(
			@NotNull LinkedList<String> element, @NotNull Player player
	) {
		final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(player);
		final GameHandler gameHandler = Meetup.getInstance().getGameHandler();
		final Game game = gameHandler.getGame();

		final List<GamePlayer> remaining = gameHandler.getRemaining();

		element.add(CC.GRAY + CC.S + "-------------------");

		switch (game.getState()) {
			case WAITING:
				element.add("Waiting for players");

				if (remaining.size() < gameHandler.getMinPlayers()) {
					final int more = gameHandler.getMinPlayers() - remaining.size();
					element.add(CC.PRI + more + CC.WHITE + " more player" + (more == 1 ? "" : "s") + ".");
				}
				break;
			case STARTING:
				element.add("The game will start in:");
				element.add(CC.PRI + (game.getGameStartTime() + 1));
				break;
			case IN_GAME:
				element.add("Border: " + CC.PRI + game.getNextBorder() + game.getFormattedBorderStatus());
				element.add("Remaining: " + CC.PRI + gameHandler.getRemaining().size() + "/" + gameHandler.getInitialPlayers());
				element.add("Your Ping: " + CC.PRI + this.getFormattedPing(MinecraftReflection.getPing(player)));
				element.add("Kills: " + CC.PRI + gamePlayer.getGameKills());

				if (gamePlayer.getNoCleanTimer() != null) {
					element.add(CC.B_PRI + "Cooldowns:");
					element.add(CC.GRAY + " * " + CC.WHITE + "No Clean: " + CC.PRI + gamePlayer.getNoCleanTimer().getTime());

				}
				break;
			case ENDING:
				element.add(CC.B_PRI + "Top 3 kills:");
				element.addAll(gameHandler.getScoreboardEndingLines());
				break;
		}

		element.add("");
		element.add(CC.PRI + "www.pvp.bar");
		element.add(CC.GRAY + CC.S + "-------------------");
	}

	@NotNull
	@Override
	public String getTitle(@NotNull Player player) {
		return "UHC Meetup";
	}
}
