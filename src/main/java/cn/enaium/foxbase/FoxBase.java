package cn.enaium.foxbase;

import cn.enaium.foxbase.command.CommandManager;
import cn.enaium.foxbase.config.ConfigManager;
import cn.enaium.foxbase.event.EventManager;
import cn.enaium.foxbase.module.ModuleManager;
import cn.enaium.foxbase.setting.SettingManager;

public enum FoxBase {

    instance;

    public String name = "FoxBase";
    public String author = "Enaium";
    public String version = "1.0";
    public String game = "1.16.3";

    public EventManager eventManager;
    public ModuleManager moduleManager;
    public SettingManager settingManager;
    public CommandManager commandManager;
    public ConfigManager configManager;

    public void run() {
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        settingManager = new SettingManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        moduleManager.loadMods();
        commandManager.loadCommands();
        configManager.loadConfig();
    }

    public void stop() {
        configManager.saveConfig();
    }

}
