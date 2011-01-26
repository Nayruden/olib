package net.omnivr.olib;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

// This code is inspired by sk89's TargetBlock.java in WorldEdit.

/**
 * This class is an inefficient trace implementation. It simply crawls along a
 * direction checking for new blocks.
 *
 * @author Nayruden
 */
public class Trace {

    World world;
    Block current_block;
    Vector slice;
    Vector start;
    int iterations = 0;
    double max_distance;
    double slice_distance;
    static final double DEFAULT_SLICE_DISTANCE = 0.2;
    static final double DEFAULT_MAX_DISTANCE = 300;

    public static Block Simple(Player player) {
        return (new Trace(player)).getNextNonAirBlock();
    }

    public static Block Simple(Player player, double max_distance) {
        return (new Trace(player, max_distance)).getNextNonAirBlock();
    }

    public Trace(Player player) {
        this(player, DEFAULT_MAX_DISTANCE, DEFAULT_SLICE_DISTANCE);
    }

    public Trace(Player player, double max_distance) {
        this(player, max_distance, DEFAULT_SLICE_DISTANCE);
    }

    /**
     * Create a trace. Does not actually perform any tracing yet.
     * @param player the player you'd like to trace from (using view vector)
     * @param max_distance the maximum distance to allow the trace to go
     * @param slice_distance the slice distance for the trace, smaller is more
     * accurate but slower.
     */
    public Trace(Player player, double max_distance, double slice_distance) {
        this.world = player.getWorld();
        this.max_distance = max_distance;
        this.slice_distance = slice_distance;
        start = player.getLocation().toVector().add(Constants.PLAYER_VIEW_OFFSET);
        slice = Util.forwardVector(player).multiply(slice_distance);
        current_block = world.getBlockAt(start.getBlockX(), start.getBlockY(), start.getBlockZ());
    }

    public Block getNextBlock() {
        int current_x = current_block.getX();
        int current_y = current_block.getY();
        int current_z = current_block.getZ();
        Block next_block = null;

        iterations++;
        while (iterations * slice_distance < max_distance) {
            Vector proposed_location = start.clone().add(slice.clone().multiply(iterations));
            if (proposed_location.getBlockX() != current_x
                    || proposed_location.getBlockY() != current_y
                    || proposed_location.getBlockZ() != current_z) {
                next_block = world.getBlockAt(proposed_location.getBlockX(), proposed_location.getBlockY(), proposed_location.getBlockZ());
                break;
            }

            iterations++;
        }

        current_block = next_block;
        return next_block;
    }

    public Block getNextNonAirBlock() {
        Block block;
        do {
            block = getNextBlock();
        } while (block != null && block.getType() == Material.AIR);
        
        return block;
    }

    public Block getNextSolidBlock() {
        Block block;
        do {
            block = getNextBlock();
        } while (block != null && (block.getType() == Material.AIR || block.getType() == Material.LAVA || block.getType() == Material.WATER));

        return block;
    }
}
