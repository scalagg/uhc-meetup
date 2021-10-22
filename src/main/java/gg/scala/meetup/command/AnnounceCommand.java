package gg.scala.meetup.command;

import gg.scala.lemon.Lemon;
import gg.scala.lemon.util.QuickAccess;
import net.evilblock.cubed.acf.BaseCommand;
import net.evilblock.cubed.acf.ConditionFailedException;
import net.evilblock.cubed.acf.annotation.CommandAlias;
import net.evilblock.cubed.acf.annotation.CommandPermission;
import gg.scala.meetup.Meetup;
import gg.scala.meetup.game.Game;
import gg.scala.meetup.game.GameState;
import gg.scala.meetup.handler.GameHandler;
import net.evilblock.cubed.util.CC;
import net.evilblock.cubed.util.bukkit.FancyMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

/**
 * @author GrowlyX
 * @since 6/5/2021
 */

public class AnnounceCommand extends BaseCommand {

    @CommandAlias("announce|a|gamealert|gameannounce")
    @CommandPermission("game.command.announce")
    public void execute(Player player) {
        final GameHandler gameHandler = Meetup.getInstance().getGameHandler();
        final Game game = gameHandler.getGame();

        if (game.getState().equals(GameState.IN_GAME)) {
            throw new ConditionFailedException("You cannot announce the game while it's running.");
        }

        if (gameHandler.getLastAnnouncement() + TimeUnit.SECONDS.toMillis(15L) > System.currentTimeMillis() && gameHandler.getLastAnnouncer() != null && !player.isOp()) {
            throw new ConditionFailedException("You must wait " + DurationFormatUtils.formatDurationWords(gameHandler.getLastAnnouncement() + TimeUnit.SECONDS.toMillis(15L) - System.currentTimeMillis(), true, true) + " as " + gameHandler.getLastAnnouncer() + CC.RED + " has already announced the game.");
        }

        gameHandler.setLastAnnouncement(System.currentTimeMillis());
        gameHandler.setLastAnnouncer(player.getDisplayName());

        player.sendMessage(CC.GREEN + "You've announced the game.");

        final FancyMessage message = new FancyMessage();
        message.withMessage(CC.B_PRI + "Alert " + CC.B_GRAY + "» " + player.getDisplayName() + CC.SEC + " wants you to play " + CC.PRI + "UHC Meetup" + CC.SEC + "!" + CC.B_GREEN + " [Join]");
        message.andHoverOf(
                CC.SEC + "Click to join " + CC.PRI + Lemon.getInstance().getSettings().getId() + CC.SEC + "!",
                "",
                CC.B_RED + "Warning: " + CC.RED + "This will switch your server!"
        );
        message.andCommandOf(
                ClickEvent.Action.RUN_COMMAND, "/join " + Lemon.getInstance().getSettings().getId()
        );

        QuickAccess.sendGlobalFancyBroadcast(message, null);
    }
}
