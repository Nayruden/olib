/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.omnivr.olib;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.ContainerBlock;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 *
 * @author Zoot
 */
public class Util {

    /**
     * Converts pitch and yaw to an unit vector representing the rotation.
     *
     * @param pitch the pitch, -90 looking along +y, 90 looking along -y
     * @param yaw the yaw, 0 looking along +z, 90 looking along +y
     * @return the converted vector
     */
    public static Vector forwardVector(double pitch, double yaw) {
        pitch = Math.toRadians(pitch);
        yaw = Math.toRadians(yaw);
        double ratio = Math.cos(pitch);
        return new Vector(-Math.sin(yaw) * ratio, -Math.sin(pitch),
                Math.cos(yaw) * ratio);
    }

    public static Vector forwardVector(Entity ent) {
        return forwardVector(ent.getLocation().getPitch(), ent.getLocation().getYaw());
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static class DoubleChest {

        public Block primary_chest;
        public Block secondary_chest;
    }

    public static DoubleChest getDoubleChestIfExists(Block block) {
        if (block.getType() == Material.CHEST) { // Look for double chest
            for (BlockFace neighbor_face : Constants.NEIGHBORS) {
                Block neighbor = block.getRelative(neighbor_face);
                if (neighbor.getType() == Material.CHEST) {
                    DoubleChest double_chest = new DoubleChest();
                    if (neighbor_face == BlockFace.NORTH || neighbor_face == BlockFace.EAST) {
                        double_chest.primary_chest = neighbor;
                        double_chest.secondary_chest = block;
                    } else {
                        double_chest.primary_chest = block;
                        double_chest.secondary_chest = neighbor;
                    }
                    return double_chest;
                }
            }
        }
        return null;
    }

    public static void extractResourceTo(String resource, String path) {
        FileWriter output;
        try {
            output = new FileWriter(path);
        } catch (IOException ex) {
            System.err.println("Error: Could not create file " + path);
            return;
        }

        InputStream input = Util.class.getResourceAsStream(resource);
        try {
            int c = input.read();
            while (c > 0) {
                output.write(c);
                c = input.read();
            }
        } catch (IOException ex) {
            System.err.println("Error while writing file: " + path);
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException ex) {
                // Just give up
            }
        }
    }

    private Util() {
    } // No instances of this class
}
