package com.github.irvinglink.ChatFilter;

import com.github.irvinglink.ChatFilter.commands.ChatFilterCommand;
import com.github.irvinglink.ChatFilter.handlers.ChatFilterHandler;
import com.github.irvinglink.ChatFilter.handlers.CooldownHandler;
import com.github.irvinglink.ChatFilter.listeners.AsyncPlayerChat;
import com.github.irvinglink.ChatFilter.listeners.InvalidWord;
import com.github.irvinglink.ChatFilter.models.ThresholdAction;
import com.github.irvinglink.ChatFilter.models.WordCategory;
import com.github.irvinglink.ChatFilter.monitors.LoaderMonitor;
import com.github.irvinglink.ChatFilter.utils.Chat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class ChatFilterPlugin extends JavaPlugin {


    private File configFile, langFile;
    private FileConfiguration config, lang;

    private final List<File> fileList = new ArrayList<>();

    private final Map<UUID, Integer> filteredPlayers = Collections.synchronizedMap(new HashMap<>());
    private final List<WordCategory> wordCategories = Collections.synchronizedList(new ArrayList<>());

    private final List<ThresholdAction> thresholdActions = Collections.synchronizedList(new ArrayList<>());

    private final List<String> whitelistedIps = Collections.synchronizedList(new ArrayList<>());

    private final List<UUID> togglePlayers = Collections.synchronizedList(new ArrayList<>());

    private Chat chat;
    private ChatFilterHandler chatFilterHandler;
    private LoaderMonitor loaderMonitor;

    private CooldownHandler cooldownHandler;

    private String ignoredPermission;

    @Override
    public void onLoad() {

        createFiles();

        this.chat = new Chat();
        this.loaderMonitor = new LoaderMonitor();
        this.chatFilterHandler = new ChatFilterHandler();
        this.cooldownHandler = new CooldownHandler();

        chat.registerHook();


    }

    @Override
    public void onEnable() {

        this.ignoredPermission = getConfig().getString("settings.ignored-permission");

        new ChatFilterCommand("clashfilter", "ChatFilter.Admin", true);

        getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        getServer().getPluginManager().registerEvents(new InvalidWord(), this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            System.out.println("[ChatFilter] PlaceHolderAPI enabled");

        } else System.out.println("[ChatFilter] PlaceHolderAPI has not been found");

    }

    @Override
    public void onDisable() {

    }

    void createFiles() {

        File mainFolder = getDataFolder();

        if (!mainFolder.exists()) mainFolder.mkdirs();

        this.configFile = new File(mainFolder, "config.yml");
        this.langFile = new File(mainFolder, "lang.yml");

        if (!this.configFile.exists())
            try {
                Files.copy(getResource(configFile.getName()), configFile.toPath());
            } catch (IOException exception) {
                exception.printStackTrace();
            }


        if (!this.langFile.exists())
            try {
                Files.copy(getResource(langFile.getName()), langFile.toPath());
            } catch (IOException exception) {
                exception.printStackTrace();
            }


        fileList.add(this.configFile);
        fileList.add(this.langFile);

        updateFiles(fileList);

    }

    void updateFiles(List<File> files) {

        getServer().getLogger().info("[ChatFilter] Updating files...");

        for (File file : files) {

            YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(file);
            externalYamlConfig.options().copyDefaults(true);

            InputStreamReader internalConfig = new InputStreamReader(Objects.requireNonNull(getResource(file.getName())), StandardCharsets.UTF_8);
            YamlConfiguration internalYamlConfig = YamlConfiguration.loadConfiguration(internalConfig);

            for (String line : internalYamlConfig.getKeys(true))
                if (!externalYamlConfig.contains(line)) externalYamlConfig.set(line, internalYamlConfig.get(line));

            try {
                externalYamlConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        getServer().getLogger().info("[ChatFilter] Files are now updated!");

    }

    public void reloadConfig() {
        if (this.config != null)
            this.config = YamlConfiguration.loadConfiguration(this.configFile);

        if (this.lang != null)
            this.lang = YamlConfiguration.loadConfiguration(this.langFile);

    }

    public void saveConfig() {
        if (this.config != null)
            try {
                this.config.save(this.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (this.lang != null)
            try {
                this.lang.save(this.langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public FileConfiguration getConfig() {

        if (this.config != null)
            return this.config;

        this.config = new YamlConfiguration();

        try {
            this.config.load(this.configFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return this.config;

    }

    public FileConfiguration getLang() {
        if (this.lang != null)
            return this.lang;

        this.lang = new YamlConfiguration();

        try {
            this.lang.load(this.langFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return this.lang;
    }

    public Chat getChat() {
        return this.chat;
    }


    public Map<UUID, Integer> getFilteredPlayers() {
        return filteredPlayers;
    }

    public List<WordCategory> getWordCategories() {
        return wordCategories;
    }

    public ChatFilterHandler getChatFilterHandler() {
        return this.chatFilterHandler;
    }

    public String getIgnoredPermission() {
        return this.ignoredPermission;
    }

    public LoaderMonitor getLoaderMonitor() {
        return loaderMonitor;
    }

    public List<ThresholdAction> getThresholdActions() {
        return thresholdActions;
    }

    public List<String> getWhitelistedIps() {
        return whitelistedIps;
    }

    public CooldownHandler getCooldownHandler() {
        return cooldownHandler;
    }

    public List<UUID> getTogglePlayers() {
        return togglePlayers;
    }
}
