package fr.karam.jediskyhopper.listener;

import fr.karam.jediskyhopper.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final Main main;

    public BlockBreakListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(BlockBreakEvent e){
        if (e.isCancelled()) return;

        if(e.getBlock().getType() != Material.HOPPER){
            return;
        }

        if(main.getHopperManager().contains(e.getBlock().getLocation())){
            e.getPlayer().sendMessage("§cVous venez de casser votre chunk hopper.");
            main.getHopperManager().removeChunkHopper(e.getBlock().getLocation());
        }
    }
}
