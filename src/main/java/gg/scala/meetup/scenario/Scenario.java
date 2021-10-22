package gg.scala.meetup.scenario;

import gg.scala.meetup.Meetup;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * @author puugz
 * @since 05/06/2021 12:12
 */
public abstract class Scenario {

	public Scenario() {
		this.setupListeners();
	}

	private void setupListeners() {
		this.getListeners()
				.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, Meetup.getInstance()));
	}

	public abstract List<Listener> getListeners();

}
