package dev.parrotstudios.qlib;

import org.bukkit.plugin.java.JavaPlugin;

public final class QLib {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        QLib.plugin = plugin;
    }
    public static JavaPlugin getPlugin() {
        if(plugin == null) { init(JavaPlugin.getProvidingPlugin(QLib.class));}
        return plugin;
    }
}
