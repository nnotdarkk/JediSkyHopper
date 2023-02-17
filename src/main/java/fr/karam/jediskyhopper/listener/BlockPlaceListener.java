package fr.karam.jediskyhopper.listener;

import fr.karam.jediskyhopper.Main;
import fr.karam.jediskyhopper.hopper.ChunkHopper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    private final Main main;

    public BlockPlaceListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void event(BlockPlaceEvent e){
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        ItemStack itemStack = e.getItemInHand();
        Location location = e.getBlock().getLocation();

        if (!itemStack.hasItemMeta()) return;
        String blockName = itemStack.getItemMeta().getDisplayName();
        if(blockName.equals(main.getConfig().getString("item-name").replaceAll("&", "§"))){
            e.getPlayer().sendMessage("§aVous venez de placer un chunk hopper.");
            main.getHopperManager().addChunkHopper(new ChunkHopper(location, p.getDisplayName()));
        }
    }
}
