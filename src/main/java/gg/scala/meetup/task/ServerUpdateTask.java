package gg.scala.meetup.task;

import gg.scala.meetup.util.DataUtil;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author GrowlyX
 * @since 6/1/2021
 */

public class ServerUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        DataUtil.update();
    }
}
