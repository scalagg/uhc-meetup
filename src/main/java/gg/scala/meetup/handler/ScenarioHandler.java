package gg.scala.meetup.handler;

import gg.scala.meetup.scenario.Scenario;
import gg.scala.meetup.scenario.impl.NoCleanScenario;
import gg.scala.meetup.scenario.impl.TimeBombScenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author puugz
 * @since 05/06/2021 12:14
 */

@SuppressWarnings("all")
public class ScenarioHandler {

	private final List<Scenario> scenarios = new ArrayList<>();

	public ScenarioHandler() {
		this.registerScenarios(new NoCleanScenario(), new TimeBombScenario());
	}

	public void registerScenarios(Scenario... scenarios) {
		this.scenarios.addAll(Arrays.asList(scenarios));
	}

	public <T extends Scenario> T getScenario(Class<T> scenarioClass) {
		return (T) this.scenarios.stream()
				.filter(scenario -> scenario.getClass().equals(scenarioClass))
				.findFirst().orElse(null);
	}
}
