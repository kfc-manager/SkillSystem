package kalle.com.skillsystem.Events;

import kalle.com.skillsystem.MyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A class which implements the listener that detects the event when a player leaves the server.
 */
public class PlayerQuit implements Listener {

    /**
     * A method that gets called when a player quits the server.
     * @param event
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        MyPlayer myPlayer = MyPlayer.getInstance(player);
        myPlayer.logOut(); //we need to destroy his MyPlayer object because we do not need it anymore
    }

}
