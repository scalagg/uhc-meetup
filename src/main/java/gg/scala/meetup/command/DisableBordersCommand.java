package gg.scala.meetup.command;

import net.evilblock.cubed.acf.BaseCommand;
import net.evilblock.cubed.acf.ConditionFailedException;
import net.evilblock.cubed.acf.annotation.CommandAlias;
import net.evilblock.cubed.acf.annotation.CommandPermission;
import net.evilblock.cubed.acf.annotation.Default;
import gg.scala.meetup.Meetup;
import gg.scala.meetup.game.Game;
import gg.scala.meetup.game.GameState;
import gg.scala.meetup.handler.GameHandler;
import net.evilblock.cubed.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author GrowlyX
 * @since 8/11/2021
 */

@CommandAlias("disableborders|db")
@CommandPermission("game.command.disableborders")
public class DisableBordersCommand extends BaseCommand {

    @Default
    public void onDefault(CommandSender sender) {
        final GameHandler gameHandler = Meetup.getInstance().getGameHandler();
        final Game game = gameHandler.getGame();

        if (!gameHandler.isCanPlay()) {
            throw new ConditionFailedException("The map is currently generating.");
        }

        if (!game.isState(GameState.WAITING)) {
            throw new ConditionFailedException("You cannot execute this command right now.");
        }

        game.setBorders(false);

        Bukkit.broadcastMessage((sender instanceof Player ? ((Player) sender).getDisplayName() : CC.D_RED + "Console") + CC.SEC + " has disabled borders for this game.");
    }
}
