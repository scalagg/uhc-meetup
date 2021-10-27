package gg.scala.meetup.cubed_impl

import gg.scala.meetup.Meetup
import gg.scala.meetup.handler.GameHandler
import net.evilblock.cubed.visibility.VisibilityAction
import net.evilblock.cubed.visibility.VisibilityAdapter
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 10/27/2021
 */
object UHCMeetupVisibility : VisibilityAdapter
{
    val gameHandler: GameHandler = Meetup.getInstance().gameHandler

    override fun getAction(toRefresh: Player, refreshFor: Player): VisibilityAction
    {
        val viewer = Meetup.getInstance().playerHandler.getByPlayer(refreshFor)
        val target = Meetup.getInstance().playerHandler.getByPlayer(refreshFor)

        if (gameHandler.spectators.contains(target) && !gameHandler.spectators.contains(viewer))
        {
            return VisibilityAction.HIDE
        } else if (gameHandler.remaining.contains(viewer) && !gameHandler.remaining.contains(target))
        {
            return VisibilityAction.HIDE
        }

        return VisibilityAction.NEUTRAL
    }
}
