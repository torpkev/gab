package com.ibexmc.gab.util.functions;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiscFunctions {
    public static List<Location> getHollowCube(Location corner1, Location corner2) {
        List<Location> result = new ArrayList<>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX()) - 1;
        double minY = Math.min(corner1.getY(), corner2.getY()) - 1;
        double minZ = Math.min(corner1.getZ(), corner2.getZ()) - 1;
        double maxX = Math.max(corner1.getX(), corner2.getX()) + 1;
        double maxY = Math.max(corner1.getY(), corner2.getY()) + 1;
        double maxZ = Math.max(corner1.getZ(), corner2.getZ()) + 1;

        for (double x = minX; x <= maxX; x++) {
            for (double y = minY; y <= maxY; y++) {
                for (double z = minZ; z <= maxZ; z++) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return result;
    }
    public static List<Location> getCorners(Location corner1, Location corner2) {
        List<Location> result = new ArrayList<>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX()) - 1;
        double minY = Math.min(corner1.getY(), corner2.getY()) - 1;
        double minZ = Math.min(corner1.getZ(), corner2.getZ()) - 1;
        double maxX = Math.max(corner1.getX(), corner2.getX()) + 1;
        double maxY = Math.max(corner1.getY(), corner2.getY()) + 1;
        double maxZ = Math.max(corner1.getZ(), corner2.getZ()) + 1;

        result.add(new Location(world, maxX, maxY, maxZ));
        result.add(new Location(world, maxX, maxY, minZ));
        result.add(new Location(world, maxX, minY, maxZ));
        result.add(new Location(world, maxX, minY, minZ));
        result.add(new Location(world, minX, maxY, maxZ));
        result.add(new Location(world, minX, maxY, minZ));
        result.add(new Location(world, minX, minY, maxZ));
        result.add(new Location(world, minX, minY, minZ));

        return result;
    }
    public static Map<Integer, Location> getExtendedCornerMap(Location corner1, Location corner2) {
        Map<Integer, Location> map = new HashMap<>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX()) - 1;
        double minY = Math.min(corner1.getY(), corner2.getY()) - 1;
        double minZ = Math.min(corner1.getZ(), corner2.getZ()) - 1;
        double maxX = Math.max(corner1.getX(), corner2.getX()) + 1;
        double maxY = Math.max(corner1.getY(), corner2.getY()) + 1;
        double maxZ = Math.max(corner1.getZ(), corner2.getZ()) + 1;

        map.put(1, new Location(world, maxX, maxY, minZ));
        map.put(101, new Location(world, maxX - 1, maxY, minZ));
        map.put(1001, new Location(world, maxX - 2, maxY, minZ));
        map.put(10001, new Location(world, maxX - 3, maxY, minZ));
        map.put(102, new Location(world, maxX, maxY - 1, minZ));
        map.put(1002, new Location(world, maxX, maxY - 2, minZ));
        map.put(10002, new Location(world, maxX, maxY - 3, minZ));
        map.put(103, new Location(world, maxX, maxY, minZ + 1));
        map.put(1003, new Location(world, maxX, maxY, minZ + 2));
        map.put(10003, new Location(world, maxX, maxY, minZ + 3));


        map.put(2, new Location(world, minX, maxY, minZ));
        map.put(201, new Location(world, minX + 1, maxY, minZ));
        map.put(2001, new Location(world, minX + 2, maxY, minZ));
        map.put(20001, new Location(world, minX + 3, maxY, minZ));
        map.put(202, new Location(world, minX, maxY - 1, minZ));
        map.put(2002, new Location(world, minX, maxY - 2, minZ));
        map.put(20002, new Location(world, minX, maxY - 3, minZ));
        map.put(203, new Location(world, minX, maxY, minZ + 1));
        map.put(2003, new Location(world, minX, maxY, minZ + 2));
        map.put(20003, new Location(world, minX, maxY, minZ + 3));

        map.put(3, new Location(world, maxX, minY, minZ));
        map.put(301, new Location(world, maxX - 1, minY, minZ));
        map.put(3001, new Location(world, maxX - 2, minY, minZ));
        map.put(30001, new Location(world, maxX - 3, minY, minZ));
        map.put(302, new Location(world, maxX, minY + 1, minZ));
        map.put(3002, new Location(world, maxX, minY + 2, minZ));
        map.put(30002, new Location(world, maxX, minY + 3, minZ));
        map.put(303, new Location(world, maxX, minY, minZ + 1));
        map.put(3003, new Location(world, maxX, minY, minZ + 2));
        map.put(30003, new Location(world, maxX, minY, minZ + 3));

        map.put(4, new Location(world, minX, minY, minZ));
        map.put(401, new Location(world, minX + 1, minY, minZ));
        map.put(4001, new Location(world, minX + 2, minY, minZ));
        map.put(40001, new Location(world, minX + 3, minY, minZ));
        map.put(402, new Location(world, minX, minY + 1, minZ));
        map.put(4002, new Location(world, minX, minY + 2, minZ));
        map.put(40002, new Location(world, minX, minY + 3, minZ));
        map.put(403, new Location(world, minX, minY, minZ + 1));
        map.put(4003, new Location(world, minX, minY, minZ + 2));
        map.put(40003, new Location(world, minX, minY, minZ + 3));

        map.put(5, new Location(world, maxX, maxY, maxZ));
        map.put(501, new Location(world, maxX - 1, maxY, maxZ));
        map.put(5001, new Location(world, maxX - 2, maxY, maxZ));
        map.put(50001, new Location(world, maxX - 3, maxY, maxZ));
        map.put(502, new Location(world, maxX, maxY - 1, maxZ));
        map.put(5002, new Location(world, maxX, maxY - 2, maxZ));
        map.put(50002, new Location(world, maxX, maxY - 3, maxZ));
        map.put(503, new Location(world, maxX, maxY, maxZ - 1));
        map.put(5003, new Location(world, maxX, maxY, maxZ - 2));
        map.put(50003, new Location(world, maxX, maxY, maxZ - 3));

        map.put(6, new Location(world, minX, maxY, maxZ));
        map.put(601, new Location(world, minX + 1, maxY, maxZ));
        map.put(6001, new Location(world, minX + 2, maxY, maxZ));
        map.put(60001, new Location(world, minX + 3, maxY, maxZ));
        map.put(602, new Location(world, minX, maxY - 1, maxZ));
        map.put(6002, new Location(world, minX, maxY - 2, maxZ));
        map.put(60002, new Location(world, minX, maxY - 3, maxZ));
        map.put(603, new Location(world, minX, maxY, maxZ - 1));
        map.put(6003, new Location(world, minX, maxY, maxZ - 2));
        map.put(60003, new Location(world, minX, maxY, maxZ - 3));

        map.put(7, new Location(world, maxX, minY, maxZ));
        map.put(701, new Location(world, maxX - 1, minY, maxZ));
        map.put(7001, new Location(world, maxX - 2, minY, maxZ));
        map.put(70001, new Location(world, maxX - 3, minY, maxZ));
        map.put(702, new Location(world, maxX, minY + 1, maxZ));
        map.put(7002, new Location(world, maxX, minY + 2, maxZ));
        map.put(70002, new Location(world, maxX, minY + 3, maxZ));
        map.put(703, new Location(world, maxX, minY, maxZ - 1));
        map.put(7003, new Location(world, maxX, minY, maxZ - 2));
        map.put(70003, new Location(world, maxX, minY, maxZ - 3));

        map.put(8, new Location(world, minX, minY, maxZ));
        map.put(801, new Location(world, minX + 1, minY, maxZ));
        map.put(8001, new Location(world, minX + 2, minY, maxZ));
        map.put(80001, new Location(world, minX + 3, minY, maxZ));
        map.put(802, new Location(world, minX, minY + 1, maxZ));
        map.put(8002, new Location(world, minX, minY + 2, maxZ));
        map.put(80002, new Location(world, minX, minY + 3, maxZ));
        map.put(803, new Location(world, minX, minY, maxZ - 1));
        map.put(8003, new Location(world, minX, minY, maxZ - 2));
        map.put(80003, new Location(world, minX, minY, maxZ - 3));

        return map;
    }

    public static List<Location> getRing(Location corner1, Location corner2, int eyeLevel) {
        List<Location> result = new ArrayList<Location>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX()) - 1;
        double minZ = Math.min(corner1.getZ(), corner2.getZ()) - 1;
        double maxX = Math.max(corner1.getX(), corner2.getX()) + 1;
        double maxZ = Math.max(corner1.getZ(), corner2.getZ()) + 1;

        for (double x = minX; x <= maxX; x++) {
            for (double z = minZ; z <= maxZ; z++) {
                int components = 0;
                if (x == minX || x == maxX) components++;
                if (z == minZ || z == maxZ) components++;
                if (components >= 1) {
                    result.add(new Location(world, x, eyeLevel, z));
                }
            }
        }

        return result;
    }

    public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        if (i >= minValueInclusive && i <= maxValueInclusive)
            return true;
        else
            return false;
    }
    public static boolean testOverlap(int x1, int x2, int y1, int y2) {
        return (x1 >= y1 && x1 <= y2) ||
                (x2 >= y1 && x2 <= y2) ||
                (y1 >= x1 && y1 <= x2) ||
                (y2 >= x1 && y2 <= x2);
    }
}
