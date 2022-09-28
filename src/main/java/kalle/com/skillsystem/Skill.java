package kalle.com.skillsystem;

import kalle.com.skillsystem.Configuration.SettingsConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A class to let all skills inherit from because they need same methods.
 */
public abstract class Skill {

    private final PotionEffectType type;
    private final int amplifier;
    private final String tag;
    private final int costs;
    private final Material material;

    private Boolean enabled = null;

    private static HashMap<PotionEffectType,Integer> entries = SettingsConfig.getCosts(); //map of all the effect with the costs

    /**
     * A class constructor to create an instance for every skill.
     * @param type effect type the skill is providing
     * @param amplifier level of the effect
     * @param tag display name of the skill
     * @param material material of the item (functions a icon for the button)
     */
    public Skill(PotionEffectType type, int amplifier, String tag, Material material) {
        this.type = type;
        this.amplifier = amplifier;
        this.tag = tag;
        this.costs = getEntry(type);
        this.material = material;
    }

    /**
     * A method to check if the skill is currently
     * enabled fo the player.
     * @return true if the skill is enabled, false if not and null if the skill is not purchased
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * A method to toggle the state of the skill. Enables it if it is not enabled.
     * Disables it if it is enabled and buys it if enabled == null.
     */
    public void toggleEnabled() {
        if (isEnabled() == null) { //check if the skill is not bought
            enabled = true; //skill is now purchased
            return;
        }
        if (isEnabled()) {
            enabled = false;
            return;
        }
        enabled = true;
    }

    /**
     * A method to set the enabled value directly, so we
     * can load the data of the skills easier from the config.
     * @param enabled boolean it gets set to
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * A method to get the costs of the skill.
     * @return costs of the skill
     */
    public int getCosts() {
        return costs;
    }

    /**
     * A method to get the display name of the skill.
     * @return display name of the skill
     */
    public String getTag() {
        return tag;
    }

    /**
     * A method to get the level of the effect.
     * @return level of the effect
     */
    public int getAmplifier() {
        return amplifier;
    }

    /**
     * A method to get the effect type of the skill.
     * @return effect type of the skill
     */
    public PotionEffectType getType() {
        return type;
    }

    /**
     * A method to get the item of the skill that simulates a button
     * in our gui.
     * @return button to toggle the skill
     */
    public ItemStack getItem() {
        ItemStack item = new ItemStack(material,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + getTag());
        String lore[] = new String[1];
        //set lore of the item depending on the state of the skill
        if (isEnabled() == null) {
            if (costs != 1) lore[0] = ChatColor.GRAY + "Buy for: " + costs + " coins";
            if (costs == 1) lore[0] = ChatColor.GRAY + "Buy for: " + costs + " coin"; //costs only one coin and we have to use singular
        } else {
            if (isEnabled()) {
                lore[0] = ChatColor.RED + "Disable";
            } else {
                lore[0] = ChatColor.GREEN + "Enable";
            }
        }
        meta.setLore(Arrays.asList(lore));
        meta.addEnchant(Enchantment.DURABILITY, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * A method to get the costs from the map provided by the config.
     * @param type effect type of the skill we want the costs of
     * @return costs mapped to given effect type
     */
    public int getEntry(PotionEffectType type) {
        if (!entries.containsKey(type)) { //key not found
            entries.put(type,0); //we just add 0, so we will not get any NullPointerExceptions
        }
        int entry = entries.get(type);
        return entry;
    }

}
