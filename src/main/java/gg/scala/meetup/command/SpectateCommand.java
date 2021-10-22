package gg.scala.meetup.command;

import gg.scala.meetup.Meetup;
import gg.scala.meetup.game.GameState;
import gg.scala.meetup.player.GamePlayer;
import net.evilblock.cubed.acf.BaseCommand;
import net.evilblock.cubed.acf.ConditionFailedException;
import net.evilblock.cubed.acf.annotation.CommandAlias;
import net.evilblock.cubed.acf.annotation.Default;
import net.evilblock.cubed.acf.annotation.Subcommand;
import net.evilblock.cubed.util.CC;
import net.evilblock.cubed.util.bukkit.FancyMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;

/**
 * @author puugz
 * @since 01/06/2021 18:45
 */

@CommandAlias("spectate|spec")
public class SpectateCommand extends BaseCommand {

    @Default
    public void onDefault(Player player) {
        final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(player);
        final boolean spectating = gamePlayer.isSpectating();

        final FancyMessage fancyMessage = new FancyMessage();

        if (!spectating) {
            fancyMessage
                    .withMessage("Are you sure you want to spectate?")
                    .withMessage(CC.GREEN + "[Click here to spectate]")
                    .andCommandOf(
                            ClickEvent.Action.RUN_COMMAND, "/spec confirm"
                    );
        } else {
			fancyMessage
					.withMessage("Are you sure you want to stop spectating?")
					.withMessage(CC.RED + "[Click here to stop spectating]")
					.andCommandOf(
							ClickEvent.Action.RUN_COMMAND, "/spec confirm"
					);
        }

        fancyMessage.sendToPlayer(player);
    }

    @Subcommand("confirm")
    public void onConfirm(Player player) {
        final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(player);
        final boolean spectating = gamePlayer.isSpectating();

        if (Meetup.getInstance().getGameHandler().getGame().isState(GameState.IN_GAME)) {
            throw new ConditionFailedException("You cannot spectate the game while it's running.");
        }

        if (!spectating) {
            Meetup.getInstance().getSpectatorHandler().setSpectator(gamePlayer, "chose to watch", true);
        } else {
            if (Meetup.getInstance().getGameHandler().getGame().isState(GameState.STARTING)) {
                throw new ConditionFailedException("You cannot spectate the game while it's starting.");
            }

            Meetup.getInstance().getSpectatorHandler().removeSpectator(gamePlayer);
        }
    }
}
