package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.log.Error;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigParser {

    Gab plugin;
    FileConfiguration config;
    
    public ConfigParser(Gab plugin, File configFile) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(configFile);
        this.config = fileConfig;
    }

    public ConfigReturn getValue(String configItem, Integer defaultValue, boolean required) {
        try {
            if (this.config.contains(configItem)) {
                if (this.config.isInt(configItem)) {
                    return new ConfigReturn(true, this.config.getInt(configItem));
                } else {
                    plugin.error().save(
                            "GabConfig.getValue.001",
                            "GabConfig",
                            "getValue(String configItem, Integer defaultValue, boolean required)",
                            "Config item is not string",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                    return new ConfigReturn(false, defaultValue);
                }
            } else {
                if (required) {
                    plugin.error().save(
                            "GabConfig.getValue.002",
                            "GabConfig",
                            "getValue(String configItem, Integer defaultValue, boolean required)",
                            "Config item is missing and required",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                }
                return new ConfigReturn(false, defaultValue);
            }
        } catch (Exception ex) {
            plugin.error().save(
                    "GabConfig.getValue.003",
                    "GabConfig",
                    "getValue(String configItem, Integer defaultValue, boolean required)",
                    "Unexpected Error getting String",
                    "Config Item: " + configItem + "\nError: " + ex.getMessage(),
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );
            return new ConfigReturn(false, defaultValue);
        }
    }
    public ConfigReturn getValue(String configItem, String defaultValue, boolean required) {
        try {
            if (this.config.contains(configItem)) {
                if (this.config.isString(configItem)) {
                    return new ConfigReturn(true, this.config.getString(configItem));
                } else {
                    plugin.error().save(
                            "GabConfig.getValue.001",
                            "GabConfig",
                            "getValue(String configItem, String defaultValue, boolean required)",
                            "Config item is not string",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                    return new ConfigReturn(false, defaultValue);
                }
            } else {
                if (required) {
                    plugin.error().save(
                            "GabConfig.getValue.002",
                            "GabConfig",
                            "getValue(String configItem, String defaultValue, boolean required)",
                            "Config item is missing and required",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                }
                return new ConfigReturn(false, defaultValue);
            }
        } catch (Exception ex) {
            plugin.error().save(
                    "GabConfig.getValue.003",
                    "GabConfig",
                    "getValue(String configItem, String defaultValue, boolean required)",
                    "Unexpected Error getting String",
                    "Config Item: " + configItem + "\nError: " + ex.getMessage(),
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );
            return new ConfigReturn(false, defaultValue);
        }
    }
    public ConfigReturn getValue(String configItem, boolean defaultValue, boolean required) {
        try {
            if (this.config.contains(configItem)) {
                if (this.config.isBoolean(configItem)) {
                    return new ConfigReturn(true, this.config.getString(configItem));
                } else {
                    plugin.error().save(
                            "GabConfig.getValue.001",
                            "GabConfig",
                            "getValue(String configItem, boolean defaultValue, boolean required)",
                            "Config item is not boolean",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                    return new ConfigReturn(false, defaultValue);
                }
            } else {
                if (required) {
                    plugin.error().save(
                            "GabConfig.getValue.002",
                            "GabConfig",
                            "getValue(String configItem, boolean defaultValue, boolean required)",
                            "Config item is missing and required",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                }
                return new ConfigReturn(false, defaultValue);
            }
        } catch (Exception ex) {
            plugin.error().save(
                    "GabConfig.getValue.003",
                    "GabConfig",
                    "getValue(String configItem, boolean defaultValue, boolean required)",
                    "Unexpected Error getting String",
                    "Config Item: " + configItem + "\nError: " + ex.getMessage(),
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );
            return new ConfigReturn(false, defaultValue);
        }
    }
    public ConfigReturn getValue(String configItem, ArrayList<String> defaultValue, boolean required) {
        try {
            if (this.config.contains(configItem)) {
                if (this.config.isList(configItem)) {
                    return new ConfigReturn(true, this.config.getStringList(configItem));
                } else {
                    plugin.error().save(
                            "GabConfig.getValue.001",
                            "GabConfig",
                            "getValue(String configItem, List<String> defaultValue, boolean required)",
                            "Config item is not a list",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                    return new ConfigReturn(false, defaultValue);
                }
            } else {
                if (required) {
                    plugin.error().save(
                            "GabConfig.getValue.002",
                            "GabConfig",
                            "getValue(String configItem, boolean defaultValue, boolean required)",
                            "Config item is missing and required",
                            "Config Item: " + configItem,
                            Error.Severity.WARN,
                            null
                    );
                }
                return new ConfigReturn(false, defaultValue);
            }
        } catch (Exception ex) {
            plugin.error().save(
                    "GabConfig.getValue.003",
                    "GabConfig",
                    "getValue(String configItem, boolean defaultValue, boolean required)",
                    "Unexpected Error getting String",
                    "Config Item: " + configItem + "\nError: " + ex.getMessage(),
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );
            return new ConfigReturn(false, defaultValue);
        }
    }

    public class ConfigReturn {
        private boolean success;
        private Object returnObject;

        public ConfigReturn(boolean success, Object returnObject) {
            this.success = success;
            this.returnObject = returnObject;
        }

        public boolean isSuccess() {
            return success;
        }

        public Object getReturnObject() {
            return returnObject;
        }

        public String getString() {
            if (returnObject instanceof String) {
                return (String) returnObject;
            } else {
                return null;
            }
        }

        public Boolean getBoolean() {
            if (returnObject instanceof Boolean) {
                return (Boolean) returnObject;
            } else {
                return false;
            }
        }

        public Integer getInt() {
            if (returnObject instanceof Integer) {
                return (Integer) returnObject;
            } else {
                return -1;
            }
        }

        public Double getDouble() {
            if (returnObject instanceof Double) {
                return (Double) returnObject;
            } else {
                return new Double(-1);
            }
        }

        public List<String> getStringList() {
            if (returnObject instanceof List) {
                return (List<String>) returnObject;
            } else {
                return new ArrayList<>();
            }
        }
    }
}
