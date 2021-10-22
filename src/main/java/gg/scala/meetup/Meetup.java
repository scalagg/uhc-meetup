package gg.scala.meetup;

import gg.scala.lemon.Lemon;
import gg.scala.meetup.chat.MeetupSpectateChannel;
import gg.scala.meetup.command.*;
import gg.scala.meetup.handler.*;
import gg.scala.meetup.listener.GameListener;
import gg.scala.meetup.listener.PlayerListener;
import gg.scala.meetup.provider.ScoreboardProvider;
import gg.scala.meetup.scenario.Scenario;
import gg.scala.meetup.task.ServerUpdateTask;
import gg.scala.meetup.task.game.GameCheckTask;
import gg.scala.meetup.util.MeetupUtil;
import lombok.Getter;
import net.evilblock.cubed.acf.InvalidCommandArgument;
import net.evilblock.cubed.acf.PaperCommandManager;
import net.evilblock.cubed.scoreboard.ScoreboardHandler;
import net.evilblock.cubed.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public final class Meetup extends JavaPlugin {

    @Getter
    private static Meetup instance;

    private GameHandler gameHandler;
    private MongoHandler mongoHandler;
    private PlayerHandler playerHandler;
    private SpectatorHandler spectatorHandler;
    private ScenarioHandler scenarioHandler;
    private KitHandler kitHandler;
    private BorderHandler borderHandler;
    private DeathMessageHandler deathMessageHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.gameHandler = new GameHandler();
        this.gameHandler.setupGame();

        this.mongoHandler = new MongoHandler();
        this.playerHandler = new PlayerHandler();
        this.spectatorHandler = new SpectatorHandler();
        this.scenarioHandler = new ScenarioHandler();
        this.kitHandler = new KitHandler();
        this.borderHandler = new BorderHandler();
        this.deathMessageHandler = new DeathMessageHandler();

        this.setupResourceRunnables();

        this.registerCommands();
        this.registerListeners();

        ScoreboardHandler.configure(new ScoreboardProvider());

        Lemon.getInstance().getLocalInstance()
                .getMetaData().put("game-server", "true");

        Lemon.getInstance().getLocalInstance()
                .getMetaData().put("game-server-type", "meetup");

        // TODO: 10/22/2021 add a more advanced system
//        CorePlugin.getInstance().getChatCheckList().add(new MeetupSpectateChannel());
    }

    @Override
    public void onDisable() {
        MeetupUtil.deleteWorld();
    }

    private void registerCommands() {
        final PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new ForceStartCommand());
        manager.registerCommand(new LoadoutCommand());
        manager.registerCommand(new ResetLoadoutCommand());
        manager.registerCommand(new SpectateCommand());
        manager.registerCommand(new AnnounceCommand());
        manager.registerCommand(new ReRollCommand());

        manager.getCommandContexts().registerContext(Integer.class, bukkitCommandExecutionContext -> {
            try {
                return Integer.parseInt(bukkitCommandExecutionContext.getFirstArg());
            } catch (Exception ignored) {
                throw new InvalidCommandArgument(CC.RED + bukkitCommandExecutionContext.getFirstArg() + " is not a valid integer.");
            }
        });

        manager.getCommandCompletions().registerAsyncCompletion("purchasable", context ->
                Arrays.asList("1", "2", "3", "4", "5", "10", "20", "30", "50")
        );
    }

    private void setupResourceRunnables() {
        new ServerUpdateTask().runTaskTimerAsynchronously(this, 0L, 100L);
        new GameCheckTask().runTaskTimerAsynchronously(this, 0L, 100L);
    }

    private void registerListeners() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new GameListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
    }

    public <T extends Scenario> T getScenario(Class<T> scenarioClass) {
        return this.scenarioHandler.getScenario(scenarioClass);
    }

    public void setWorldProperties() {
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(Entity::remove));

        final World world = Bukkit.getWorld("meetup_game");

        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("difficulty", "0");
        world.setTime(0);
    }
}
