package com.ibexmc.gab.data;

import com.ibexmc.gab.util.DebugType;

import java.util.*;

public class Data {
    /**
     * Stores the debug flags
     */
    public Map<DebugType, Boolean> debug = new HashMap<>();

    /**
     * Language specific text by code
     */
    public Map<String, String> locale = new HashMap<>();

    public String meColor = "&3";

    /**
     * Globally muted player
     */
    public Set<UUID> globalMuted = new HashSet<>();

    /**
     * key = player muting
     * value = player being muted
     */
    public Map<UUID, UUID> muted = new HashMap<>();
}
