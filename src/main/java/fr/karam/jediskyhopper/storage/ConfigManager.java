package fr.karam.jediskyhopper.storage;

import fr.karam.jediskyhopper.Main;
import fr.karam.jediskyhopper.utils.FileUtils;

public class ConfigManager {

    private final Main main;
    private final FileUtils fileUtils;

    public ConfigManager(Main main) {
        this.main = main;
        this.fileUtils = new FileUtils(main);
    }
}
