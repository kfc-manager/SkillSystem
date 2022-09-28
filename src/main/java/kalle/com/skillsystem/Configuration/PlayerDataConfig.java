package kalle.com.skillsystem.Configuration;

import kalle.com.skillsystem.Main;
import kalle.com.skillsystem.MyPlayer;
import kalle.com.skillsystem.Skill;
import kalle.com.skillsystem.Skills.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that handles all operations on the playerdata.yml file. For data persistence
 * we save changes in the playerdata.yml, so we do not lose data when we restart the
 * server.
 */
public class PlayerDataConfig {

    private static Main plugin;

    //file and configuration variable needed for making changes to the playerdata.yml
    private static FileConfiguration config;
    private static File file;

    /**
     * Class constructor to pass the main class, so we can send messages
     * to the console.
     * @param plugin
     */
    public PlayerDataConfig(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * A method to set the playerdata.yml file up. Creates the file if it
     * does not exist and sets it to default values.
     */
    public void setup() {
        //load playerdata.yml
        file = new File(plugin.getDataFolder(), "playerdata.yml");
        if (!file.exists()) { //check if file already exists
            try {
                file.createNewFile();
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SkillSystem] CONFIG: playerdata.yml file has been created!");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not create playerdata.yml file!");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * A method to save the changes in the config to the file.
     */
    public static void save() {
        try {
            config.save(file); //save the changes in the file
            config = YamlConfiguration.loadConfiguration(file); //set file to configuration again
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not save playerdata.yml file!");
        }
    }

    /**
     * A method to save the MyPlayer object of a player
     * in the playerdata.yml file.
     * @param myPlayer object that shall be saved
     */
    public static void savePlayer(MyPlayer myPlayer) {
        String uuid = myPlayer.getPlayer().getUniqueId().toString();
        String coins = "" + myPlayer.getCoins();
        config.set(uuid + ".Coins", coins); //save the players coin value
        for (Skill i : myPlayer.getSkills()) { //loop through all possible skills
            String tag = i.getTag(); //we save the skill with the tag as identifier
            Boolean enabled = i.isEnabled(); //enabled can be null
            config.set(uuid + ".Skills." + tag, enabled); //save skill in an entry
        }
        save(); //save changes
    }

    /**
     * A method to get the coins value of a player, which is
     * saved in the playerdata.yml file.
     * @param myPlayer object holds the owner, which is the player for which we need the value
     * @return a double retrieved from the entry of the player in the playerdata.yml file
     */
    public static int getCoins(MyPlayer myPlayer) {
        String uuid = myPlayer.getPlayer().getUniqueId().toString(); //get uuid as identifier to find player in the playerdata.yml file
        int coins = 0;
        try {
            String entry = config.get(uuid + ".Coins").toString(); //get the string of the entry
            coins = Integer.parseInt(entry); //parse the string ot an integer
        } catch (NullPointerException e) { //the entry was null and the player does not exist yet in the playerdata.yml file

        } catch (NumberFormatException e) { //string could not be parsed to integer
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SkillSystem] CONFIG ERROR: could not read playerdata.yml file!");
        }
        return coins;
    }

    /**
     * A method to get an ArrayList of all possible skills. If a skill is not purchased by a player
     * yet the "enabled" value of the skill is null. Otherwise, the value is false if the skill
     * is disabled and true if the skill is enabled.
     * @param myPlayer MyPlayer object of the player of which we want the skills
     * @return ArrayList of the skills of the player
     */
    public static ArrayList<Skill> getSkills(MyPlayer myPlayer) {

        ArrayList<Skill> skills = new ArrayList<>();

        //create instance of every possible skill and add it to the list
        FireResistance fireResistance = new FireResistance();
        skills.add(fireResistance);
        Haste haste = new Haste();
        skills.add(haste);
        Invisibility invisibility = new Invisibility();
        skills.add(invisibility);
        Leaping leaping = new Leaping();
        skills.add(leaping);
        Luck luck = new Luck();
        skills.add(luck);
        NightVision nightVision = new NightVision();
        skills.add(nightVision);
        Regeneration regeneration = new Regeneration();
        skills.add(regeneration);
        Resistance resistance = new Resistance();
        skills.add(resistance);
        Saturation saturation = new Saturation();
        skills.add(saturation);
        Strength strength = new Strength();
        skills.add(strength);
        Swiftness swiftness = new Swiftness();
        skills.add(swiftness);
        String uuid = myPlayer.getPlayer().getUniqueId().toString();
        for (Skill i : skills) { //loop through all possible skills
            Object entry = config.get(uuid + ".Skills." + i.getTag()); //get the value of the skill
            if (entry == null) continue; //skill is not purchased by player and "enabled" value stays null
            Boolean enabled = (Boolean) entry; //skill is purchased by player and either enabled or disabled
            i.setEnabled(enabled);
        }
        return skills;
    }

}
