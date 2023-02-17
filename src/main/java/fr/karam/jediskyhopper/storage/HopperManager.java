package fr.karam.jediskyhopper.storage;

import fr.karam.jediskyhopper.hopper.ChunkHopper;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class HopperManager {

    private static final Map<Location, ChunkHopper> chunkHoppers = new HashMap<>();

    public Map<Location, ChunkHopper> getChunkHoppers() {
        return chunkHoppers;
    }

    public void addChunkHopper(ChunkHopper chunkHopper) {
        chunkHoppers.put(chunkHopper.getLocation(), chunkHopper);
    }

    public void removeChunkHopper(Location location) {
        chunkHoppers.remove(location);
    }

    public boolean contains(Location location) {
        return chunkHoppers.containsKey(location);
    }

    public ChunkHopper getChunkHopper(Location location) {
        return chunkHoppers.get(location);
    }

    public boolean isChunkContainsHopper(Chunk chunk) {
        for(ChunkHopper chunkHopper: chunkHoppers.values()){
            if(chunkHopper.getChunk().equals(chunk)){
                return true;
            }
        }
        return false;
    }
}
