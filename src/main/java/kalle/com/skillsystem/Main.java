package kalle.com.skillsystem;

import kalle.com.skillsystem.Commands.SkillsCommand;
import kalle.com.skillsystem.Configuration.SettingsConfig;
import kalle.com.skillsystem.Configuration.PlayerDataConfig;
import kalle.com.skillsystem.Events.PlayerJoin;
import kalle.com.skillsystem.Events.PlayerKill;
import kalle.com.skillsystem.Events.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Main class that handles the plugin startup and shutdown.
 */
public final class Main extends JavaPlugin {

    private SettingsConfig settings = new SettingsConfig(this); //settings.yml
    private PlayerDataConfig playerdata = new PlayerDataConfig(this); //playerdata.yml

    //commands:
    private SkillsCommand skillsCommand = new SkillsCommand(); // /skills

    /**
     * A method that gets called on server start.
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        //create data folder where .yml files are stored
        if (!getDataFolder().exists()) { //check if folder already exists
            getDataFolder().mkdir(); //create data folder
        }

        //set config files up
        settings.setup();
        playerdata.setup();

        //register Events:
        getServer().getPluginManager().registerEvents(new PlayerJoin(this),this);
        getServer().getPluginManager().registerEvents(new PlayerKill(),this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(),this);
        getServer().getPluginManager().registerEvents(new GUI(),this);

        //register commands
        getCommand("skills").setExecutor(skillsCommand);

        //server gets reloaded:
        for(Player i : Bukkit.getOnlinePlayers()) { //loop through all player that are online during server reload
            MyPlayer myPlayer = new MyPlayer(i, this); //create MyPlayer object for them
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "SkillSystem has been enabled!");
    }

    /**
     * A method that gets called on server shutdown.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player i : Bukkit.getOnlinePlayers()) { //loop through all players that are online
            MyPlayer.getInstance(i).logOut(); //delete the MyPlayer object of the player
        }
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "SkillSystem has been disabled!");
    }
}
