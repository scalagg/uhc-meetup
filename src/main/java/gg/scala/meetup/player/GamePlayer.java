package gg.scala.meetup.player;

import com.google.gson.annotations.SerializedName;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import gg.scala.lemon.Lemon;
import gg.scala.meetup.Meetup;
import gg.scala.meetup.MeetupConstants;
import gg.scala.meetup.model.Loadout;
import gg.scala.meetup.task.NoCleanTimer;
import lombok.Getter;
import lombok.Setter;
import net.evilblock.cubed.serializers.Serializers;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Long-term player profile where data is saved
 * <p>
 *
 * @author GrowlyX
 * @since 5/31/2021
 */

@Getter
@Setter
public class GamePlayer {

    @SerializedName("_id")
    private final UUID uuid;
    private final String name;

    private int gameKills;
    private int kills;
    private int deaths;
    private int played;
    private int wins;
    private int reRolls;

    private NoCleanTimer noCleanTimer;
    private Loadout loadout;

    public GamePlayer(UUID player, String name) {
        this.uuid = player;
        this.name = name;

        this.loadPlayerData();
    }

    public void savePlayerData(boolean remove) {
        CompletableFuture.runAsync(() -> {
            Meetup.getInstance().getMongoHandler().getPlayerCollection().replaceOne(Filters.eq("uuid", this.uuid.toString()), this.getDocument(), new ReplaceOptions().upsert(true));

            Lemon.getInstance().getBanana().useResource(jedis -> {
                jedis.hset(MeetupConstants.JEDIS_GAMEMODE_STATS_CACHE, this.uuid.toString(), Serializers.getGson().toJson(this));
                return null;
            });
        }).whenComplete((unused, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
        });

        if (remove) {
            Meetup.getInstance().getPlayerHandler().remove(this.getUuid());
        }
    }

    public Document getDocument() {
        final Document document = new Document("_id", this.uuid);

        document.put("uuid", this.uuid.toString());
        document.put("name", this.name);

        document.put("kills", this.kills);
        document.put("deaths", this.deaths);
        document.put("played", this.played);
        document.put("wins", this.wins);
        document.put("reRolls", this.reRolls);

        document.put("inventory", Serializers.getGson().toJson(this.loadout));

        return document;
    }

    private void loadPlayerData() {
        CompletableFuture.supplyAsync(() -> Meetup.getInstance().getMongoHandler().getPlayerCollection().find(Filters.eq("uuid", this.uuid.toString())).first()).thenAccept(document -> {
            if (document == null) {
                this.savePlayerData(false);
            } else {
                if (document.getInteger("kills") != null) {
                    this.kills = document.getInteger("kills");
                }
                if (document.getInteger("deaths") != null) {
                    this.deaths = document.getInteger("deaths");
                }
                if (document.getInteger("played") != null) {
                    this.played = document.getInteger("played");
                }
                if (document.getInteger("wins") != null) {
                    this.wins = document.getInteger("wins");
                }
                if (document.getInteger("reRolls") != null) {
                    this.deaths = document.getInteger("reRolls");
                }

                final String inventory = document.getString("inventory");

                if (inventory != null) {
                    this.loadout = Serializers.getGson().fromJson(inventory, Loadout.class);
                }
            }

            if (this.loadout == null) {
                this.loadout = new Loadout(this.uuid);
                this.loadout.setupDefaultInventory();
            }
        });
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public boolean isSpectating() {
        return Meetup.getInstance().getGameHandler().getSpectators().contains(this);
    }

    public boolean isPlaying() {
        return Meetup.getInstance().getGameHandler().getRemaining().contains(this);
    }
}
