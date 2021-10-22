package gg.scala.meetup.scenario.impl;

import gg.scala.meetup.player.GamePlayer;
import gg.scala.meetup.scenario.Scenario;
import gg.scala.meetup.scenario.impl.listener.NoCleanListener;
import gg.scala.meetup.task.NoCleanTimer;
import gg.scala.meetup.util.CC;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;

/**
 * @author puugz
 * @since 05/06/2021 12:13
 */
public class NoCleanScenario extends Scenario {

	public void handleNoClean(GamePlayer gamePlayer) {
		new NoCleanTimer(gamePlayer);
	}

	public void handleCancelNoClean(GamePlayer gamePlayer) {
		if (gamePlayer == null || gamePlayer.getNoCleanTimer() == null) {
			return;
		}

		gamePlayer.getNoCleanTimer().cancel();
		gamePlayer.setNoCleanTimer(null);

		gamePlayer.getPlayer().sendMessage(CC.RED + CC.BOLD + "Your no clean timer has expired due to hostile action.");
	}

	@Override
	public List<Listener> getListeners() {
		return Collections.singletonList(new NoCleanListener());
	}
}
