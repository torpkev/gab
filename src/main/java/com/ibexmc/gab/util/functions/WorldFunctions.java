package com.ibexmc.gab.util.functions;

import org.bukkit.*;

public class WorldFunctions {

    /**
     * Serializes a chunk into world:x:z
     * @param chunk Chunk to serialize
     * @return Serialized simple chunk
     */
    public static String serializeChunk(Chunk chunk) {
        if (chunk == null) {
            return "";
        }
        return chunk.getWorld().getName() + ":" +
                chunk.getX() + ":" +
                chunk.getZ();
    }

    /**
     * Gets a chunk from a string value.
     * @param chunkString String to deserialize
     * @return Chunk (null if invalid)
     */
    public static Chunk deserializeChunk(String chunkString) {
        if (chunkString == null || chunkString.trim() == "") {
            return null;
        }
        final String[] parts = chunkString.split(":");
        if (parts.length >= 3) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int z =Integer.parseInt(parts[2]);
            return w.getChunkAt(x, z);
        }
        return null;
    }

    /**
     * Checks if the serialized chunk provided is valid
     * @param chunkString String to deserialize
     * @return If true, chunk string is valid
     */
    public static boolean validDeserializedChunk(String chunkString) {
        if (chunkString == null || chunkString.trim() == "") {
            return false;
        }
        final String[] parts = chunkString.split(":");
        if (parts.length >= 3) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            int x = Integer.parseInt(parts[1]);
            int z =Integer.parseInt(parts[2]);
            return true;
        }
        return false;
    }

    /**
     * Serializes a Location into a simple string (no pitch/yaw)
     * @param location Location to serialize
     * @return Serialized simple location
     */
    public static String serializeSimpleLocation(Location location) {
        if (location == null) {
            return "";
        }
        return location.getWorld().getName() + ":" +
                location.getBlockX() + ":" +
                location.getBlockY() + ":" +
                location.getBlockZ();
    }

    /**
     * Deserializes a simple string (no pitch/yaw) into a location
     * If invalid, returns null
     * @param locationString Location string to deserialize
     * @return Location if valid, null if not valid
     */
    public static Location deserializeSimpleLocation(String locationString) {
        if (locationString == null || locationString.trim().equals("")) {
            return null;
        }
        final String[] parts = locationString.split(":");
        if (parts.length == 4) {
            World w = Bukkit.getServer().getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            return new Location(w, x, y, z);
        }
        return null;
    }

    /**
     * Serializes a Location into a string
     * @param location Location to serialize
     * @return Serialized location
     */
    public static String serializeLocation(Location location) {
        if (location == null) {
            return "";
        }
        return location.getWorld().getName() + ":" +
                location.getBlockX() + ":" +
                location.getBlockY() + ":" +
                location.getBlockZ() + ":" +
                location.getPitch() + ":" +
                location.getYaw();
    }

    /**
     * Returns a new Location object with pitch and yaw removed
     * @param location Location to simplify
     * @return Simplified location
     */
    public static Location simplifyLocation(Location location) {
        return new Location(
                location.getWorld(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }

    /**
     * Returns a simplified string location for a location
     * @param location Location to simplify
     * @return Simplified location in String format
     */
    public static String simpleReadableLocation(Location location) {
        String returnValue = "";
        if (location != null) {
            returnValue =
                    "World: " + location.getWorld().getName() +
                            " X:" + location.getBlockX() +
                            " Y: " + location.getBlockY() +
                            " Z: " + location.getBlockZ();
        }
        return returnValue;
    }

    /**
     * Returns a nicely formatted Location string
     * @param location Location to format
     * @return Formatted string for the location
     */
    public static String fancyReadableLocation(Location location) {
        if (location == null) {
            return "";
        }
        String ret = "";
        ret = "&5World: &d" +
                location.getWorld().getName() +
                " &5X:&d" +
                location.getBlockX() +
                " &5Y:&d " +
                location.getBlockY() +
                " &5Z:&d " +
                location.getBlockZ() +
                " &5Pitch: &d " +
                location.getPitch() +
                " &5Yaw: &d " +
                location.getYaw();
        return ret;
    }

    /**
     * Returns a nicely formatted simple Location string
     * @param location Location to format
     * @return Formatted string for the simple location
     */
    public static String simpleFancyReadableLocation(Location location) {
        if (location == null) {
            return "";
        }
        String ret = "";
        ret = "&5World: &d" +
                location.getWorld().getName() +
                " &5X:&d" +
                location.getBlockX() +
                " &5Y:&d " +
                location.getBlockY() +
                " &5Z:&d " +
                location.getBlockZ();
        return ret;
    }

    /**
     * Checks if a location is safe to teleport to
     * @param location Location to check
     * @return If true, location is safe
     */
    public static boolean safeTeleport(Location location) {
        final Location tpLoc = location.clone();
        final Location head = tpLoc.clone().add(0, 1, 0);
        Location below = tpLoc.clone();
        if (head.getBlock().getType().equals(Material.AIR) || head.getBlock().getType().equals(Material.CAVE_AIR) || head.getBlock().getType().equals(Material.WATER)) {
            for (int i = 0; i < 4; i++) {
                below = below.add(0, -1, 0);
                if (below.getBlock().getType().isSolid()) {
                    // A solid block was found, return true
                    return true;
                } else {
                    if (below.getBlock().getType().equals(Material.LAVA) && !below.getBlock().getType().equals(Material.VOID_AIR)) {
                        // Either lava or void has been detected, return false
                        return false;
                    }
                }
            }
        } else {
            // Player would suffocate, return false
            return false;
        }
        // Unsafe, or we would have exited the function by now
        return false;
    }

    /**
     * Returns true if the location is inside a cuboid
     * @param location Location to check
     * @param corner1 Corner 1 of the cuboid
     * @param corner2 Corner 2 of the cuboid
     * @return If true, location is inside the cuboid
     */
    public static boolean insideCuboid(Location location, Location corner1, Location corner2) {

        if (location.getWorld().equals(corner1.getWorld()) && location.getWorld().equals(corner2.getWorld()))
        {
            int x = location.getBlockX();
            int y= location.getBlockY();
            int z = location.getBlockZ();
            int x1 = Math.min(corner1.getBlockX(), corner2.getBlockX());
            int y1 = Math.min(corner1.getBlockY(), corner2.getBlockY());
            int z1 = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
            int x2 = Math.max(corner1.getBlockX(), corner2.getBlockX());
            int y2 = Math.max(corner1.getBlockY(), corner2.getBlockY());
            int z2 = Math.max(corner1.getBlockZ(), corner2.getBlockZ());

            return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
        } else {
            return false;
        }

    }

    /**
     * Returns true if the location is inside the chunk
     * @param location Location to check
     * @param chunk Chunk to check
     * @return If true, location is inside the chunk
     */
    public static boolean insideChunk(Location location, Chunk chunk) {
        return location.getWorld().equals(chunk.getWorld()) &&
                location.getChunk().getX() == chunk.getX() &&
                location.getChunk().getZ() == chunk.getZ();
    }

    /**
     * Gets corner 1 from a chunk
     * @param chunk Chunk to get location from
     * @return Corner 1 location
     */
    public static Location getChunkCorner1(Chunk chunk) {
        return chunk.getBlock(0, 0, 0).getLocation();
    }

    /**
     * Gets corner 2 from a chunk
     * @param chunk Chunk to get location from
     * @return Corner 2 location
     */
    public static Location getChunkCorner2(Chunk chunk) {
        return chunk.getBlock(15, 255, 15).getLocation();
    }

}
