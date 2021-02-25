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

    public Map<String, Channel> channels = new HashMap<>();

    public String configDefaultPrefix = "[%key%]";
    public String configDefaultColor = "&f";
    public String configMeColor = "&3";
    public String configSayColor = "&5";
    public boolean configSaveToFile = false;
    public boolean configDefaultNotify = false;
}
