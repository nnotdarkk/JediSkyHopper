package fr.karam.jediskyhopper.storage;

import fr.karam.jediskyhopper.Main;
import fr.karam.jediskyhopper.hopper.ChunkHopper;
import fr.karam.jediskyhopper.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private final Main main;
    private final FileUtils fileUtils;
    private final YamlConfiguration config;

    public ConfigManager(Main main) {
        this.main = main;
        this.fileUtils = new FileUtils(main);

        if(!fileUtils.fileExists("hoppers")){
            fileUtils.createFile("hoppers");
        }

        config = YamlConfiguration.loadConfiguration(fileUtils.getFile("hoppers"));
    }

    public String serializeLocation(Location location) {
        return location.getWorld().getName() + ";" + (int)location.getX() + ";" + (int)location.getY() + ";" + (int)location.getZ();
    }

    public Location deserializeLocation(String[] path) {
        return new Location(Bukkit.getWorld(path[0]), Integer.parseInt(path[1]), Integer.parseInt(path[2]), Integer.parseInt(path[3]));
    }

    public void save(Location location, String owner){
        List<String> list = config.getStringList("hoppers");
        list.add(serializeLocation(location) + ";" + owner);
        config.set("hoppers", list);
        configSave();
    }

    public void load(String path){
        Location location = deserializeLocation(path.split(";"));
        String owner = path.split(";")[4];
        main.getHopperManager().addChunkHopper(new ChunkHopper(location, owner));
    }

    public void saveAll(){
        config.set("hoppers", null);
        for(ChunkHopper chunkHopper: main.getHopperManager().getChunkHoppers().values()){
            save(chunkHopper.getLocation(), chunkHopper.getOwner());
        }
    }

    public void loadAll(){
        for(String path: config.getStringList("hoppers")){
            load(path);
        }
    }

    public void configSave(){
        try {
            config.save(fileUtils.getFile("hoppers"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
