package kalle.com.skillsystem.Events;

import kalle.com.skillsystem.MyPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * A class which implements the listener that detects the event when a player kills a mob.
 */
public class PlayerKill implements Listener {

    /**
     * A method that gets called when a player kills a mob.
     * @param event
     */
    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Animals) { //entity is an animal, so we need to cast it to animals
            Animals animal = (Animals) entity;
            Player player = (Player) animal.getKiller();
            MyPlayer myPlayer = MyPlayer.getInstance(player);
            EntityType type = animal.getType();
            myPlayer.addReward(type); //adds coins to the balance of the player
        }
        if (entity instanceof Monster) { //entity is a monster, so we need to cast it to monster
            Monster monster = (Monster) entity;
            Player player = (Player) monster.getKiller();
            MyPlayer myPlayer = MyPlayer.getInstance(player);
            EntityType type = monster.getType();
            myPlayer.addReward(type); //adds coins to the balance of the player
        }
    }

}
