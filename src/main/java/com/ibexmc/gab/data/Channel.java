package com.ibexmc.gab.data;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.functions.StringFunctions;

public class Channel {
    private String key;
    private String name;
    private String prefix;
    private boolean sendToDiscord = true;
    private String defaultColor = "&f";
    private boolean notify = false;

    public Channel(String perm) {

        Data gabData = Gab.instance.data;

        if (StringFunctions.isNullOrEmpty(perm)) {
            return;
        }
        if (perm.contains(".")) {
            String[] permSegments = perm.split("\\.");
            String channelSegment = permSegments[permSegments.length - 1];
            this.key = channelSegment.toLowerCase();
            this.name = channelSegment.substring(0, 1).toUpperCase() + channelSegment.substring(1);
        } else {
            this.key = perm.toLowerCase();
            this.name = perm.substring(0, 1).toUpperCase() + perm.substring(1);
        }
        this.prefix = gabData.configDefaultPrefix.replace("%key%", this.name);
        this.defaultColor = gabData.configDefaultColor;
        this.notify = gabData.configDefaultNotify;

        // load from file IF EXISTS
    }
}
