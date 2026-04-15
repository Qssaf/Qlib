package dev.parrotstudios.qlib.logger;

import dev.parrotstudios.qlib.QLib;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class QLoggerManager {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static JavaPlugin plugin;
    private static File loggerFile;
    private static String fileName;
    private static BufferedWriter writer;

    private QLoggerManager() {

    }

    public static void init() {
        init("logger.log");
    }
    static{
        plugin = QLib.getPlugin();
    }

    public static void init(String loggerFileName) {

        if (writer != null) {
            shutDown();
        }


        fileName = loggerFileName;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        loggerFile = new File(plugin.getDataFolder(), fileName);
        createLogFile();
        try {
            writer = new BufferedWriter(new FileWriter(loggerFile, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createLogFile() {
        if (!loggerFile.exists()) {
            try {
                loggerFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create log file!");
                e.printStackTrace();
            }
        }
    }

    public static boolean log(String message) {
        if (plugin == null) return false;
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.write(message.replace("{time}", timestamp));
            writer.newLine();

            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void shutDown() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            writer = null;
            plugin = null;
            loggerFile = null;
        }
    }
}