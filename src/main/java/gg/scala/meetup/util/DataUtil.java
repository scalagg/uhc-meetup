package gg.scala.meetup.util;

import gg.scala.lemon.Lemon;
import gg.scala.meetup.Meetup;

/**
 * @author GrowlyX
 * @since 6/1/2021
 */

public class DataUtil {

    public static void update()
    {
        final int remaining = Meetup.getInstance()
                .getGameHandler().getRemaining().size();

        Lemon.getInstance().getLocalInstance()
                .getMetaData().put("game-state", Meetup.getInstance().getGameHandler().getGame().getState().name());

        Lemon.getInstance().getLocalInstance()
                .getMetaData().put("remaining", Integer.toString(remaining));
    }
}
