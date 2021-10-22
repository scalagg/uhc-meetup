package gg.scala.meetup.task.game;

import gg.scala.meetup.Meetup;
import gg.scala.meetup.game.GameState;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author GrowlyX
 * @since 5/31/2021
 */

public class GameCheckTask extends BukkitRunnable {

    @Override
    public void run() {
        if (!Meetup.getInstance().getGameHandler().getGame().isState(GameState.IN_GAME)) {
            return;
        }

        Meetup.getInstance().getGameHandler().checkWinners();
    }
}
