package fr.karam.jediskyhopper;

import dev.rosewood.rosestacker.api.RoseStackerAPI;
import fr.karam.jediskyhopper.command.GiveChunkHopper;
import fr.karam.jediskyhopper.storage.ConfigManager;
import fr.karam.jediskyhopper.storage.HopperManager;
import fr.karam.jediskyhopper.listener.BlockBreakListener;
import fr.karam.jediskyhopper.listener.BlockPlaceListener;
import fr.karam.jediskyhopper.task.HopperTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin {

    private static Main instance;
    private HopperManager hopperManager;
    private RoseStackerAPI rsAPI;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        hopperManager = new HopperManager();
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        new HopperTask(this).runTaskTimer(this, 0, getConfig().getInt("task-delay"));
        if (Bukkit.getPluginManager().isPluginEnabled("RoseStacker")) {
            this.rsAPI = RoseStackerAPI.getInstance();
        }
        registerCommand("jediskyhopper", new GiveChunkHopper("givechunkhopper", this));
        configManager = new ConfigManager(this);
        configManager.loadAll();
    }

    @Override
    public void onDisable() {
        configManager.saveAll();
    }

    public void registerCommand(String commandName, Command commandClass) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap)bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(commandName, commandClass);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static Main getInstance() {
        return instance;
    }

    public HopperManager getHopperManager() {
        return hopperManager;
    }

    public RoseStackerAPI getRsAPI() {
        return rsAPI;
    }
}
