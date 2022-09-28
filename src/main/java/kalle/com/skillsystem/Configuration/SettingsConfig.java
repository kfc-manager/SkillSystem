package kalle.com.skillsystem.Configuration;

import kalle.com.skillsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class that handles all operations on the settings.yml file.
 */
public class SettingsConfig {

    private static Main plugin;

    //file and configuration variable needed for making changes to the settings.yml
    private static FileConfiguration config;
    private static File file;

    /**
     * Class constructor to pass the main class, so we can send messages
     * to the console.
     * @param plugin
     */
    public SettingsConfig(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * A method to set the settings.yml file up. Creates the file if it
     * does not exist and sets it to default values.
     */
    public void setup() {
        //load settings.yml
        file = new File(plugin.getDataFolder(), "settings.yml");
        if (!file.exists()) { //check if file already exists
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                loadDefault();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SkillSystem] CONFIG: settings.yml file has been created!");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not create settings.yml file!");
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }
    }

    /**
     * A method to set the values of the settings.yml file to the default values.
     */
    public static void loadDefault() {
        //adding all minecraft mobs to the config with their default values
        config.set("Mobs.AXOLOTL", 0);
        config.set("Mobs.BAT", 0);
        config.set("Mobs.BEE", 0);
        config.set("Mobs.BLAZE", 1);
        config.set("Mobs.CAT", 0);
        config.set("Mobs.CAVE_SPIDER", 1);
        config.set("Mobs.CHICKEN", 0);
        config.set("Mobs.COD", 0);
        config.set("Mobs.COW", 0);
        config.set("Mobs.CREEPER", 1);
        config.set("Mobs.DOLPHIN", 0);
        config.set("Mobs.DONKEY", 0);
        config.set("Mobs.DROWNED", 1);
        config.set("Mobs.ELDER_GUARDIAN", 5);
        config.set("Mobs.ENDER_DRAGON", 50);
        config.set("Mobs.ENDERMAN", 1);
        config.set("Mobs.ENDERMITE", 1);
        config.set("Mobs.FOX", 0);
        config.set("Mobs.FROG", 0);
        config.set("Mobs.GHAST", 1);
        config.set("Mobs.GIANT", 1);
        config.set("Mobs.GLOW_SQUID", 0);
        config.set("Mobs.GOAT", 0);
        config.set("Mobs.GUARDIAN", 1);
        config.set("Mobs.HOGLIN", 1);
        config.set("Mobs.HORSE", 0);
        config.set("Mobs.HUSK", 1);
        config.set("Mobs.ILLUSIONER", 1);
        config.set("Mobs.IRON_GOLEM", 0);
        config.set("Mobs.LLAMA", 0);
        config.set("Mobs.MAGMA_CUBE", 1);
        config.set("Mobs.MULE", 0);
        config.set("Mobs.MUSHROOM_COW", 0);
        config.set("Mobs.OCELOT", 0);
        config.set("Mobs.PANDA", 0);
        config.set("Mobs.PARROT", 0);
        config.set("Mobs.PHANTOM", 1);
        config.set("Mobs.PIG", 0);
        config.set("Mobs.PIGLIN", 1);
        config.set("Mobs.PIGLIN_BRUTE", 1);
        config.set("Mobs.PILLAGER", 1);
        config.set("Mobs.PLAYER", 0);
        config.set("Mobs.POLAR_BEAR", 0);
        config.set("Mobs.PUFFERFISH", 0);
        config.set("Mobs.RABBIT", 0);
        config.set("Mobs.RAVAGER", 1);
        config.set("Mobs.SALMON", 0);
        config.set("Mobs.SHEEP", 0);
        config.set("Mobs.SHULKER", 1);
        config.set("Mobs.SILVERFISH", 1);
        config.set("Mobs.SKELETON", 1);
        config.set("Mobs.SKELETON_HORSE", 1);
        config.set("Mobs.SLIME", 1);
        config.set("Mobs.SNOWMAN", 0);
        config.set("Mobs.SPIDER", 1);
        config.set("Mobs.SQUID", 0);
        config.set("Mobs.STRAY", 1);
        config.set("Mobs.STRIDER", 0);
        config.set("Mobs.TADPOLE", 0);
        config.set("Mobs.TRADER_LLAMA", 0);
        config.set("Mobs.TROPICAL_FISH", 0);
        config.set("Mobs.TURTLE", 0);
        config.set("Mobs.VEX", 1);
        config.set("Mobs.VILLAGER", 0);
        config.set("Mobs.VINDICATOR", 1);
        config.set("Mobs.WANDERING_TRADER", 0);
        config.set("Mobs.WARDEN", 1);
        config.set("Mobs.WITCH", 1);
        config.set("Mobs.WITHER", 30);
        config.set("Mobs.WITHER_SKELETON", 1);
        config.set("Mobs.WOLF", 0);
        config.set("Mobs.ZOGLIN", 1);
        config.set("Mobs.ZOMBIE", 1);
        config.set("Mobs.ZOMBIE_HORSE", 1);
        config.set("Mobs.ZOMBIE_VILLAGER", 1);
        config.set("Mobs.ZOMBIFIED_PIGLIN", 1);

        //adding all possible skills to the config with their default values
        config.set("Skills.FIRE_RESISTANCE", 6250);
        config.set("Skills.FAST_DIGGING", 5000);
        config.set("Skills.INVISIBILITY", 10000);
        config.set("Skills.JUMP", 2500);
        config.set("Skills.LUCK", 2000);
        config.set("Skills.NIGHT_VISION", 3500);
        config.set("Skills.REGENERATION", 7500);
        config.set("Skills.DAMAGE_RESISTANCE", 6250);
        config.set("Skills.SATURATION", 5000);
        config.set("Skills.INCREASE_DAMAGE", 5000);
        config.set("Skills.SPEED", 4000);

        save(); //save all changes made
    }

    /**
     * A method to get all the values of the mobs from the settings.yml file. Reads every potion effect type
     * and adds it to the hashmap.
     * @return hashmap with the potion effect type and the amount of coins it costs to buy
     */
    public static HashMap<PotionEffectType,Integer> getCosts() {
        HashMap<PotionEffectType,Integer> result = new HashMap<>();
        for (PotionEffectType i : PotionEffectType.values()) { //loop through all possible potion effect types
            String name = i.getName(); //get type as string, so we can find entry in config
            int costs = 0;
            try {
                String entry = config.get("Skills." + name).toString(); //get the entry to the potion effect type i
                costs = Integer.parseInt(entry); //parse entry to int
                if (costs < 0) { //the entry was modified, so we would give a player coins if he buys a skill
                    throw new IllegalStateException();
                }
                result.put(i,costs);
            } catch (NullPointerException e) { //no entry of that skill
                result.put(i,0); //we add 0 because we either do not really the value or do not want the whole plugin to fail
            } catch (NumberFormatException | IllegalStateException e) { //entry empty or not an integer
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not read settings.yml file! Please repair settings.yml and restart the server!");
            }
        }
        return result;
    }

    /**
     * A method to get all the values of the mobs from the settings.yml file. Reads every entity type
     * and adds it to the hashmap.
     * @return hashmap with the entity type and the amount of coins it gives on kill
     */
    public static HashMap<EntityType,Integer> getDrops() {
        HashMap<EntityType,Integer> result = new HashMap<>();
        for (EntityType i : EntityType.values()) { //loop through all possible entity types
            String name = i.name(); //get type as string, so we can find entry in config
            int coins = 0;
            try {
                String entry = config.get("Mobs." + name).toString(); //get the entry to the entity type i
                coins = Integer.parseInt(entry); //parse entry to int
                if (coins < 0) { //the entry was modified, so players would lose coins when they kill a mob
                    throw new IllegalStateException();
                }
                result.put(i,coins); //if entity type gives exp add their value to hashmap
            } catch (NullPointerException e) { //no entry of that mob
                result.put(i,0); //we add 0 because it does not give any coins
            } catch (NumberFormatException | IllegalStateException e) { //entry empty or not an integer
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not read settings.yml file! Please repair settings.yml and restart the server!");
            }
        }
        return result;
    }

    /**
     * A method to save the changes in the config to the file.
     */
    public static void save() {
        try {
            config.save(file); //save the changes in the file
            config = YamlConfiguration.loadConfiguration(file); //set file to configuration again
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not save settings.yml file!");
        }
    }

}
