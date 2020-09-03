package com.ibexmc.gab.util.log;

        import com.ibexmc.gab.Gab;
        import org.bukkit.ChatColor;
        import org.bukkit.command.ConsoleCommandSender;

public class Debug {

    Gab plugin;
    boolean debugActive = false;

    public Debug(Gab plugin, boolean debugActive) {
        this.plugin = plugin;
        this.debugActive = debugActive;
    }

    public void enter(String className, String functionName) {
        if (!debugActive) {
            return;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                ChatColor.LIGHT_PURPLE + "[" + plugin.getInternalName() + ".Debug.EnterFunction]" +
                        ChatColor.AQUA + "[" + className + "." + functionName + "]");
    }
    public void log(String className, String functionName, String message, String additional)
    {
        if (!debugActive) {
            return;
        }
        if (additional.length() > 0) {
            additional = "\n" + "Additional Information:\n" + additional;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                ChatColor.LIGHT_PURPLE + "[" + plugin.getInternalName() + ".Debug]" +
                        ChatColor.AQUA + "[" + className + "." + functionName + "] " + ChatColor.WHITE + message + additional);
    }
}
