package net.omnivr.olib;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

/**
 *
 * @author Zoot
 */
public class Constants {

    public static final Vector PLAYER_VIEW_OFFSET = new Vector(0, 1.65f, 0);
    public static final BlockFace[] NEIGHBORS = {BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH};

    private Constants() {
    } // No instances of this class
}
