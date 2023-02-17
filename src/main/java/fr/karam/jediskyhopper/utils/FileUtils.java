package fr.karam.jediskyhopper.utils;

import fr.karam.jediskyhopper.Main;

import java.io.File;

public class FileUtils {

    private final Main main;

    public FileUtils(Main main) {
        this.main = main;
    }

    public void createFile(String filename){
        File file = new File(main.getDataFolder() + File.separator + filename + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeFile(String filename){
        File file = new File(main.getDataFolder() + File.separator + filename + ".yml");

        if(file.exists()){
            file.delete();
        }
    }

    public File getFile(String filename){
        return new File(main.getDataFolder() + File.separator + filename + ".yml");
    }

    public boolean fileExists(String filename){
        return getFile(filename).exists();
    }
}
