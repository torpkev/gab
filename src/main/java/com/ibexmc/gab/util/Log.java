package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.functions.StringFunctions;
import org.bukkit.command.ConsoleCommandSender;

public class Log {

    //region Objects
    Gab plugin;
    String internalName;
    boolean dev;
    //endregion
    //region Constructor
    public Log(Gab plugin) {
        this.plugin = plugin;
        this.internalName = Gab.internalName;
        //this.dev = Gab.instance.dev;
    }
    //endregion
    //region Methods

    /**
     * Logs a message to the console
     * @param title Title of the message
     * @param message Message to send
     */
    public void console(String title, String message) {
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "&5[" +
                                internalName +
                                ".Log]&b[" +
                                title +
                                "]&r " +
                                message
                )
        );
    }

    /**
     * Quick console output (with [QUICK] prefix)
     * @param message Message to output
     */
    public void quick(String message) {
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "&5[" +
                                internalName +
                                ".Log]&b[QUICK]&r " +
                                message
                )
        );
    }

    /**
     * Quick console output (with optional [QUICK] prefix
     * @param message Message to output
     * @param prefix If true, show the prefix
     */
    public void quick(String message, boolean prefix) {
        if (prefix) {
            quick(message);
        } else {
            ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
            ccs.sendMessage(StringFunctions.color(message));
        }
    }

    /**
     * Sends a quick debug message to the console
     * @param type The debug type to send
     * @param message Message to send
     */
    public void debug(DebugType type, String message) {
        if (!debug(type)) {
            return;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(StringFunctions.color("&d" + Gab.internalName + " Debug: &r" + message));
    }

    /**
     * Sends a debug message to the console that the code has entered the specified function
     * @param className Class name
     * @param functionName Function name
     */
    public void debugEnter(String className, String functionName) {
        DebugType debug = DebugType.ENTER;
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "d[" +
                                internalName +
                                ".Debug.Enter]&b[" +
                                className  +
                                "." +
                                functionName +
                                "]"
                )
        );
    }
    /**
     * Sends a debug message to the console that the code is exiting the specified function
     * @param className Class name
     * @param functionName Function name
     */
    public void debugExit(String className, String functionName) {
        DebugType debug = DebugType.ENTER;
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "d[" +
                                internalName +
                                ".Debug.Enter]&b[" +
                                className  +
                                "." +
                                functionName +
                                "]"
                )
        );
    }

    /**
     * Sends a debug message to the console
     * @param type Debug Type
     * @param className Class name
     * @param functionName Function name
     * @param message Message to send
     * @param additional Additional data
     */
    public void debug(DebugType type, String className, String functionName, String message, String additional) {
        if (!debug(type)) {
            return;
        }
        if (additional.length() > 0) {
            additional = "\n" + "Additional Information:\n" + additional;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "d[" +
                                internalName +
                                ".Debug]&b[" +
                                className  +
                                "." +
                                functionName +
                                "]&f " +
                                message +
                                additional
                )
        );
    }

    private boolean debug(DebugType type) {
        if (plugin.data.debug != null) {
            if (plugin.data.debug.containsKey(type)) {
                return plugin.data.debug.get(type);
            }
        }
        return false;
    }

    /**
     * Sends a quick dev message to the console
     * @param message Message to send
     */
    public void dev(String message) {
        if (!dev) {
            return;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(StringFunctions.color(message));
    }

    /**
     * Sends a dev message to the console
     * @param className Class name
     * @param functionName Function name
     * @param message Message to send
     * @param additional Additional data
     */
    public void dev(String className, String functionName, String message, String additional) {
        if (!dev) {
            return;
        }
        if (additional.length() > 0) {
            additional = "\n" + "Additional Information:\n" + additional;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "d[" +
                                internalName +
                                ".Debug]&b[" +
                                className  +
                                "." +
                                functionName +
                                "]&f " +
                                message +
                                additional
                )
        );
    }

    /**
     * Logs an error
     * @param code Error code
     * @param className Class name
     * @param functionName Function name
     * @param summary Summary to send
     * @param detail Detail to send
     * @param severity Severity to send
     * @param stackTraceElements Stack Trace to send
     */
    public void error(
            String code,
            String className,
            String functionName,
            String summary,
            String detail,
            Severity severity,
            StackTraceElement[]
                    stackTraceElements
    ) {
        String severityString = severity.toString();
        switch (severity) {
            case INFO:
                severityString = StringFunctions.color("&dInfo&r");
                break;
            case WARN:
                severityString = StringFunctions.color("&6Warning&r");
                break;
            case URGENT:
                severityString = StringFunctions.color("&eUrgent&r");
                break;
            case CRITICAL:
                severityString = StringFunctions.color("&cCritical&r");
                break;
        }


        StringBuilder stackTrace = new StringBuilder();;
        if (stackTraceElements != null) {
            stackTrace.append(
                    StringFunctions.color(
                            "\n&c############################################################" +
                                    "\nStack Trace\n" +
                                    "&fPlease include this in any bug reports submitted!\n" +
                                    "&c############################################################&r\n"
                    )
            );
            for (StackTraceElement element : stackTraceElements) {
                stackTrace.append(element.toString());
                stackTrace.append("\n");
            }
            stackTrace.append(
                    StringFunctions.color(
                            "&r############################################################&f"
                    )
            );
        }

        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                StringFunctions.color(
                        "&c[&fERROR&c]\n" +
                                "&4Error Code:&f " + code + "\n" +
                                "&4Source:&f " + className + "\n" +
                                "&4Function:&f " + functionName + "\n" +
                                "&4Severity:&r " + severityString + "\n" +
                                "&4Summary:&f " + summary + "\n" +
                                "&4Detail:&f " + detail + stackTrace
                )

        );
    }
    //endregion
}
