package gg.scala.meetup.cubed_impl

import gg.scala.meetup.Meetup
import gg.scala.meetup.handler.GameHandler
import net.evilblock.cubed.nametag.NametagInfo
import net.evilblock.cubed.nametag.NametagProvider
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 10/27/2021
 */
object UHCMeetupNametag : NametagProvider(
    "uhc_meetup", 10
)
{
    val gameHandler: GameHandler = Meetup.instance.gameHandler

    override fun fetchNametag(toRefresh: Player, refreshFor: Player): NametagInfo?
    {
        val viewer = Meetup.instance.playerHandler.getByPlayer(refreshFor)
        val target = Meetup.instance.playerHandler.getByPlayer(refreshFor)

        if (gameHandler.spectators.contains(target) && gameHandler.spectators.contains(viewer))
        {
            return createNametag(CC.GRAY, "")
        }

        return null
    }
}
