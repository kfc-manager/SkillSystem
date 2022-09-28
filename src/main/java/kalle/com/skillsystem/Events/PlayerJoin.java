package kalle.com.skillsystem.Events;

import kalle.com.skillsystem.Main;
import kalle.com.skillsystem.MyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * A class which implements the listener that detects the event when a player joins the server.
 */
public class PlayerJoin implements Listener {

    private Main plugin;

    /**
     * A class constructor, so we can pass the Main class because we need it
     * to create a MyPlayer object.
     * @param plugin
     */
    public PlayerJoin(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * A method that gets called when a player joins the server.
     * @param event
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MyPlayer myPlayer = new MyPlayer(player, plugin);
    }

}
