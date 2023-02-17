package fr.karam.jediskyhopper.hopper;

import dev.rosewood.rosestacker.stack.StackedItem;
import fr.karam.jediskyhopper.Main;
import fr.karam.jediskyhopper.storage.HopperManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ChunkHopper {

    private final Location location;
    private final String owner;
    private final Chunk chunk;

    private final Main main = Main.getInstance();

    public ChunkHopper(Location location, String owner) {
        this.location = location;
        this.owner = owner;
        this.chunk = location.getChunk();
    }

    public Location getLocation() {
        return location;
    }

    public String getOwner() {
        return owner;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void collectItems() {
        Chunk chunk = getChunk();
        if (chunk.isLoaded()){
            Arrays.stream(chunk.getEntities()).filter(Entity::isOnGround).filter(entity -> !entity.isDead()).filter(entity -> entity instanceof Item)
                    .map(entity -> (Item)entity)
                    .forEach(item -> {
                        Block block = this.location.getBlock();
                        if (!block.getType().equals(Material.HOPPER)) {
                            new HopperManager().removeChunkHopper(location);
                            return;
                        }
                        Hopper hopper = (Hopper) this.location.getBlock().getState();
                        Inventory inventory = hopper.getInventory();
                        if (inventory.firstEmpty() != -1) {
                            collect(inventory, item);
                        } else {
                            collectRemainingItems(inventory, item);
                        }
                    });
        }

    }

    private void collect(Inventory inventory, Item item) {
        if(main.getRsAPI().getStackedItem(item).getStackSize() > 64){
            ItemStack itemStack = item.getItemStack();
            inventory.addItem(itemStack);
            StackedItem stackedItem = main.getRsAPI().getStackedItem(item);
            stackedItem.setStackSize(stackedItem.getStackSize() - 64);
            return;
        }
        ItemStack itemStack = item.getItemStack();
        inventory.addItem(itemStack);
        item.remove();
    }

    private void collectRemainingItems(Inventory inventory, Item item) {
        for (int i = 0; i < 5; i++) {
            ItemStack inventoryStack = inventory.getItem(i);
            ItemStack groundStack = item.getItemStack();

            int inventoryAmount = inventoryStack.getAmount();
            int groundAmount = main.getRsAPI().getStackedItem(item).getStackSize();

            ItemStack clone = groundStack.clone();
            clone.setAmount(inventoryAmount);

            if (inventoryStack.equals(clone)) {
                int maxStackSize = inventoryStack.getMaxStackSize();
                if (inventoryAmount + groundAmount <= maxStackSize) {
                    inventory.addItem(groundStack);
                    item.remove();
                } else if (inventoryAmount < maxStackSize) {
                    inventoryStack.setAmount(maxStackSize);
                    StackedItem stackedItem = main.getRsAPI().getStackedItem(item);
                    stackedItem.setStackSize(groundAmount - inventoryAmount);
                }
            }
        }
    }
}
