package gg.scala.meetup.chat;

import com.solexgames.core.chat.IChatCheck;
import com.solexgames.core.player.PotPlayer;
import gg.scala.meetup.Meetup;
import gg.scala.meetup.player.GamePlayer;
import gg.scala.meetup.util.CC;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author GrowlyX
 * @since 6/23/2021
 */

public class UHCMeetupSpectatorChatCheckImpl implements IChatCheck {

    @Override
    public void check(AsyncPlayerChatEvent event, PotPlayer potPlayer) {
        final GamePlayer gamePlayer = Meetup.getInstance().getPlayerHandler().getByPlayer(event.getPlayer());

        if (!event.isCancelled() && gamePlayer.isSpectating()) {
            Meetup.getInstance().getPlayerHandler().getPlayerTypeMap().forEach((uuid, gamePlayer1) -> {
                if (gamePlayer1.isSpectating()) {
                    gamePlayer1.getPlayer().sendMessage(CC.GRAY + "[Spectator] " + event.getPlayer().getName() + ": " + event.getMessage());
                }
            });

            event.setCancelled(true);
        }
    }
}
