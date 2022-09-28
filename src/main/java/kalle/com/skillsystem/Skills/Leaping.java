package kalle.com.skillsystem.Skills;

import kalle.com.skillsystem.Skill;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

/**
 * A class that represents a specific skill. Inherits from the abstract class Skill.
 */
public class Leaping extends Skill {

    /**
     * A class constructors to pass attributes to Skill class, which
     * implements methods on these attributes.
     */
    public Leaping() {
        super(PotionEffectType.JUMP, 1, "Leaping", Material.LEATHER_BOOTS);
    }

}
