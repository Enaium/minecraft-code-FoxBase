package cn.enaium.foxbase.config;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.config.config.ModulesConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigManager {

    private ArrayList<Config> files;
    private Gson gson;
    private File directory;

    public ConfigManager() {
        files = new ArrayList<Config>();
        gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        directory = new File(MinecraftClient.getInstance().runDirectory.toString() + "/" + FoxBase.instance.name);
        makeDirectory();
        registerFiles();
    }

    private void makeDirectory() {
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private void registerFiles() {
        files.add(new ModulesConfig(gson, null));
    }


    public void loadConfig() {

        for (Config file : files) {
            try {
                file.loadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void saveConfig() {

        for (Config file : files) {
            try {
                file.saveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
