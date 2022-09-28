package kalle.com.skillsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A class that builds a graphical user interface with the players inventory
 * and items.
 */
public class GUI implements Listener {

    private MyPlayer myPlayer; //variable so we can get necessary data to display
    private Inventory inv; //inventory that functions as frame

    private static ItemStack placeholder = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);

    private static ItemStack enableAll = new ItemStack(Material.EMERALD, 1); //button to enable all skills
    private static ItemStack disableAll = new ItemStack(Material.REDSTONE, 1); //button to disable all skills

    /**
     * A class constructor to build the gui.
     * @param player
     */
    public GUI(Player player) {
        myPlayer = MyPlayer.getInstance(player);
        int coins = myPlayer.getCoins();
        String end;
        if (coins == 1) { //check quantity of coins
            end = " coin"; //we need singular
        } else {
            end = " coins"; //we need plural
        }
        inv = Bukkit.createInventory(null, 27, "Your Balance: " + coins + end);

        //put the items into the inventory
        initializeItems();
    }

    /**
     * A class constructor to simply initialize items we use globally.
     * Need to create an instance anyway to register the event (button press).
     */
    public GUI() {
        //placeholder, so a player can not put items in the gui
        ItemMeta pmeta = placeholder.getItemMeta();
        pmeta.setDisplayName(" ");
        pmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        placeholder.setItemMeta(pmeta);

        //initialize enable all button
        ItemMeta emeta = enableAll.getItemMeta();
        emeta.setDisplayName(ChatColor.GREEN + "Enable All");
        emeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        enableAll.setItemMeta(emeta);

        //initialize disable all button
        ItemMeta dmeta = disableAll.getItemMeta();
        dmeta.setDisplayName(ChatColor.RED + "Disable All");
        dmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        disableAll.setItemMeta(dmeta);
    }

    /**
     * A method to put all buttons inside the inventory/gui.
     */
    private void initializeItems() {
        int count = 0; //index of the inventory
        for(Skill i : myPlayer.getSkills()) { //loop through all skills a player can possibly have
            inv.setItem(count, i.getItem()); //get the item with information
            count++;
        }
        for (int i = 0 ; i < 14 ; i++) { //fill with placeholders
            inv.setItem(count, placeholder);
            count++;
        }
        inv.setItem(count, enableAll); //add enable all button
        count++;
        inv.setItem(count, disableAll); //add disable all button
    }

    /**
     * A method to open the gui for the player it
     * was created for.
     */
    public void open() {
        Player player = myPlayer.getPlayer();
        player.openInventory(inv);
    }

    /**
     * A method that gets called when an item gets clicked in an inventory, so
     * we can track the presses of our created buttons/items.
     * @param event event that gets triggered
     */
    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); //get the player who started the event
        myPlayer = MyPlayer.getInstance(player);
        ItemStack clickedItem = event.getCurrentItem(); //get the clicked item to compare it to our buttons

        //check if the button was an item of one of our skills
        for (Skill i : myPlayer.getSkills()) {
            if (clickedItem.isSimilar(i.getItem())) {
                event.setCancelled(true);
                try {
                    boolean enabled = myPlayer.toggleSkill(i);
                    if (enabled) { //skill is now enabled
                        player.sendMessage(ChatColor.GREEN + i.getTag() + " has been enabled!");
                    } else { //skill is now disabled
                        player.sendMessage(ChatColor.RED + i.getTag() + " has been disabled!");
                    }
                } catch (IllegalStateException e) { //skill was tried to be purchased but player did not have enough coins
                    player.sendMessage(ChatColor.RED + "You do not have enough coins to buy that skill!");
                }
                player.closeInventory();
                return;
            }
        }

        //placeholder was clicked, so the event just needs to be cancelled
        if (clickedItem.isSimilar(placeholder)) {
            event.setCancelled(true);
            return;
        }

        //enable all button was pressed
        if (clickedItem.isSimilar(enableAll)) {
            event.setCancelled(true);
            myPlayer.enableAll();
            player.sendMessage(ChatColor.GREEN + "All your skills have been enabled!");
            return;
        }

        //disable all button was pressed
        if (clickedItem.isSimilar(disableAll)) {
            event.setCancelled(true);
            myPlayer.disableAll();
            player.sendMessage(ChatColor.RED + "All your skills have been disabled!");
        }
    }

}
