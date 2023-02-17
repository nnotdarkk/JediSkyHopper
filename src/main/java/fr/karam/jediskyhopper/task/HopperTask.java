package fr.karam.jediskyhopper.task;

import fr.karam.jediskyhopper.Main;
import fr.karam.jediskyhopper.hopper.ChunkHopper;
import org.bukkit.scheduler.BukkitRunnable;

public class HopperTask extends BukkitRunnable {

    private final Main main;

    public HopperTask(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        for(ChunkHopper chunkHopper: main.getHopperManager().getChunkHoppers().values()){
            chunkHopper.collectItems();
        }
    }
}
