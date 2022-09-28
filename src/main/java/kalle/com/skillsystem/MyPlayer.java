package kalle.com.skillsystem;

import kalle.com.skillsystem.Configuration.PlayerDataConfig;
import kalle.com.skillsystem.Configuration.SettingsConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that holds the skills and coins of a player and
 * implements all operations on them.
 */
public class MyPlayer {

    private static ArrayList<MyPlayer> instances = new ArrayList<>(); //list of all instances, so we can call an instance by the owner (player)

    private static HashMap<EntityType, Integer> mobDrop = SettingsConfig.getDrops(); //map of how much coins a mob drops

    private Main plugin;

    private Player player;
    private int coins;
    private ArrayList<Skill> skills;
    private int taskID; //task id, so we can cancel the thread once a player has disconnected

    /**
     * A class constructor, so we can initialize the
     * skills and coins with the data from the config file
     * abd start the thread that keeps the effects running.
     * @param player
     * @param plugin
     */
    public MyPlayer(Player player, Main plugin) {
        this.plugin = plugin;
        this.player = player;
        instances.add(this); //add this object to list, so we can call it by player
        //read data from config
        coins = PlayerDataConfig.getCoins(this);
        skills = PlayerDataConfig.getSkills(this);
        startEffects(); //start the thread
    }

    /**
     * The method that gets the player/owner of the object.
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The method that gets the amount of coins a player has.
     * @return
     */
    public int getCoins() {
        return coins;
    }

    /**
     * The method that gets the skills of the player.
     * @return
     */
    public ArrayList<Skill> getSkills() {
        return skills;
    }

    /**
     * A method to cancel the thread and remove this
     * instance from the list because we do not need
     * to call it anymore. Also saves the changes made
     * to the skills and coin balance.
     */
    public void logOut() {
        PlayerDataConfig.savePlayer(this); //save data
        instances.remove(this); //remove this object from list
        stopEffects(); //cancel the thread
    }

    /**
     * A method to toggle the current state of the skill.
     * Buys the skill if the player does not have it yet.
     * @param skill skill that needs to be toggled
     * @return the state of which the skill got toggled to
     * @throws IllegalStateException gets thrown if the player does not have enough coins to buy the skill
     */
    public boolean toggleSkill(Skill skill) throws IllegalStateException {
        if (skill.isEnabled() == null) { //skill needs to be bought
            int costs = skill.getCosts();
            if (costs > coins) {
                throw new IllegalStateException(); //player does not have enough coins
            }
            coins -= costs; //player bought the skill and coins need to be subtracted
        }
        skill.toggleEnabled(); //change the state of the skill
        return skill.isEnabled(); //return the new state of the skill
    }

    /**
     * A method to enable all the skills the player owns.
     */
    public void enableAll() {
        for(Skill i : skills) {
            if (i.isEnabled() == null) continue; //skill is not bought, so we can ignore that case
            if (!(i.isEnabled())) i.toggleEnabled(); //skill is not enabled yet and needs to be enabled
        }
    }

    /**
     * A method to disable all the skills the player owns.
     */
    public void disableAll() {
        for(Skill i : skills) {
            if (i.isEnabled() == null) continue; //skill is not bought, so we can ignore that case
            if (i.isEnabled()) i.toggleEnabled(); //skill is not disabled yet and needs to be disabled
        }
    }

    /**
     * A method to get an instance by its owner (player).
     * @param player the owner of the instance
     * @return the object of the passed player
     * @throws IndexOutOfBoundsException no object is somehow bound to the player
     */
    public static MyPlayer getInstance(Player player) throws IndexOutOfBoundsException {
        for (MyPlayer i : instances) { //loop through all instances
            if (player == i.getPlayer()) { //check if owner is the player we are looking for
                return i;
            }
        }
        throw new IndexOutOfBoundsException(); //no match found
    }

    /**
     * A method to start the thread that refreshes
     * all the effects of enabled skills.
     */
    private void startEffects() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> { //task id to be later able to cancel the thread
            for (Skill i : skills) { //loop through all the skills of the player
                if (i.isEnabled() == null) continue; //skill is not owner by player, so we need to do nothing
                if (i.isEnabled()) { //check if the skill is enabled
                    PotionEffectType type = i.getType();
                    int amplifier = i.getAmplifier();
                    player.addPotionEffect(new PotionEffect(type,140,amplifier)); //apply effect to the player
                }
            }
        }, 0L, 60L); //method gets called every 60 ticks (3 seconds)
    }

    /**
     * A method to stop the thread.
     */
    private void stopEffects() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    /**
     * A method to add the coins a player earns when
     * he kills a mob.
     * @param type
     */
    public void addReward(EntityType type) {
        try {
            int drop = mobDrop.get(type);
            coins += drop; //add drop to the balance of the player
        } catch (NullPointerException e) { //mob was not found and saved as it would not give any coins
            mobDrop.put(type, 0);
        }
    }

}
