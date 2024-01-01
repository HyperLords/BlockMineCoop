package com.me.oyuntarayici.api;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigurationFile {

    private final YamlConfiguration yamlConfiguration;
    private final File file;

    /**
     * Create a new ConfigurationFile
     * @param plugin The plugin which should own this file.
     * @param name The name (without extension) of the file
     */
    public ConfigurationFile(Plugin plugin, String src, String name) {
        if (!new File(src).isDirectory()) new File(src).mkdirs();
        this.file = new File(src, name);
        this.yamlConfiguration = new YamlConfiguration();
        if (!this.file.exists()) {
            try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream(name)) {
                if(in==null){
                    this.file.createNewFile();
                }else{
                    Files.copy(in, file.toPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return yamlConfiguration;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        try {
            yamlConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}