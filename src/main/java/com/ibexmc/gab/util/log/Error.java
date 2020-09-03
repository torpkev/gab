package com.ibexmc.gab.util.log;

import com.ibexmc.gab.Gab;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Error {

    Gab plugin;
    public Error(Gab plugin) {
        this.plugin = plugin;
    }

    public enum Severity
    {
        INFO,
        WARN,
        URGENT,
        CRITICAL
    }

    public void save(
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
                severityString = ChatColor.LIGHT_PURPLE + "Info" + ChatColor.RESET;
                break;
            case WARN:
                severityString = ChatColor.GOLD + "Warning" + ChatColor.RESET;
                break;
            case URGENT:
                severityString = ChatColor.YELLOW + "Urgent" + ChatColor.RESET;
                break;
            case CRITICAL:
                severityString = ChatColor.RED + "Critical" + ChatColor.RESET;
                break;
        }


        StringBuilder stackTrace = new StringBuilder();;
        if (stackTraceElements != null) {
            stackTrace.append(
                    ChatColor.RED +"\n############################################################" +
                            "\nStack Trace\n" + ChatColor.RED +
                            "Please include this in any bug reports submitted!\n" +
                            ChatColor.RED +"############################################################\n" + ChatColor.RESET
            );
            for (StackTraceElement element : stackTraceElements) {
                stackTrace.append(element.toString());
                stackTrace.append("\n");
            }
            stackTrace.append(
                    ChatColor.RED +"############################################################" + ChatColor.RESET
            );
        }

        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                ChatColor.RED + "[" + ChatColor.WHITE + "ERROR" + ChatColor.RED + "]\n" + ChatColor.RESET +
                        ChatColor.DARK_RED + "Error Code: " + ChatColor.WHITE + code + "\n" +
                        ChatColor.DARK_RED + "Source: " + ChatColor.WHITE + className + "\n" +
                        ChatColor.DARK_RED + "Function: " + ChatColor.WHITE + functionName + "\n" +
                        ChatColor.DARK_RED + "Severity: " + severityString + "\n" +
                        ChatColor.DARK_RED + "Summary: " + ChatColor.WHITE + summary + "\n" +
                        ChatColor.DARK_RED + "Detail: " + ChatColor.WHITE + detail + stackTrace
        );

    }
}
