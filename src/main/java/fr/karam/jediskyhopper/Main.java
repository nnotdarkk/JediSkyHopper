package fr.karam.jediskyhopper;

import dev.rosewood.rosestacker.api.RoseStackerAPI;
import fr.karam.jediskyhopper.storage.HopperManager;
import fr.karam.jediskyhopper.listener.BlockBreakListener;
import fr.karam.jediskyhopper.listener.BlockPlaceListener;
import fr.karam.jediskyhopper.task.HopperTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private HopperManager hopperManager;
    private RoseStackerAPI rsAPI;

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
