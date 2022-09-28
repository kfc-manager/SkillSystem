package kalle.com.skillsystem.Commands;

import kalle.com.skillsystem.GUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The class of the command "skills" that implements its command execution.
 */
public class SkillsCommand implements CommandExecutor {

    /**
     * The method that gets called when the command "skills" is executed.
     * @param sender sender who is executing the command
     * @param command command that is getting executed
     * @param label
     * @param args arguments of the command that is getting executed
     * @return true if it is the passed command, false if not
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("skills")) {
            if (!(sender instanceof Player)) { sender.sendMessage(ChatColor.RED + "Sender of this command must be a player!"); return true; } //console executes this command and we need to cancel
            Player player = (Player) sender;
            GUI gui = new GUI(player); //create a gui for the player
            gui.open(); //open the gui
            return true;
        }
        return false;
    }

}
