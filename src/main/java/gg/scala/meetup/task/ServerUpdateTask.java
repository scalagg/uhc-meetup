package gg.scala.meetup.task;

import gg.scala.meetup.Meetup;
import gg.scala.meetup.util.JedisUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;

/**
 * @author GrowlyX
 * @since 6/1/2021
 */

public class ServerUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        CompletableFuture.runAsync(() ->
                Meetup.getInstance().getJedisManager().publish(JedisUtil.getServerUpdateJson())
        );
    }
}
