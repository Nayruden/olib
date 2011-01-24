/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.omnivr.olib;

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

    private Util() {} // No instances of this class
}
