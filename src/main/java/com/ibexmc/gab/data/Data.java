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

    public Map<String, Channel> channels = new HashMap<>();

    public String configDefaultPrefix = "[%key%]";
    public String configDefaultColor = "&f";
    public boolean configSaveToFile = false;
    public boolean configDefaultNotify = false;
}
