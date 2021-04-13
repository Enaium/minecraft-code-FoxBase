package cn.enaium.foxbase.client.command;

import cn.enaium.cf4m.annotation.Auto;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.foxbase.client.setting.*;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Auto
@Command({"s", "setting"})
public class SetCommand {

    private ModuleContainer module;
    private IConfiguration configuration;

    private ModuleProvider currentModule;
    private ArrayList<SettingProvider> settings;
    private SettingProvider currentSetting;

    @Exec
    public void exec(@Param("Module") String moduleName) {
        currentModule = module.getByName(moduleName);
        if (currentModule == null) {
            configuration.getCommand().message("The module with the name \"" + moduleName + "\" does not exist.");
            return;
        }

        settings = currentModule.getSetting().getAll();

        if (settings == null) {
            configuration.getCommand().message("The module with the name \"" + moduleName + "\" no setting exists.");
            return;
        }

        configuration.getCommand().message("Here are the list of settings:");

        for (SettingProvider s : settings) {
            configuration.getCommand().message(s.getName() + "(" + s.getClass().getSimpleName() + ")" + s.getDescription());
            if (s instanceof ModeSetting) {
                ((ModeSetting) s).getModes().forEach(configuration.getCommand()::message);
            }
        }
    }

    @Exec
    public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName) {
        exec(moduleName);
        SettingProvider setting = currentModule.getSetting().getByName(settingName);
        if (setting != null) {
            currentSetting = setting;
            configuration.getCommand().message(currentSetting.getName() + "|" + currentSetting.getClass().getSimpleName());
        } else {
            configuration.getCommand().message("The setting with the name \"" + settingName + "\" does not exist.");
        }
    }

    @Exec
    public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName, @Param("SettingValue") String settingValue) {
        exec(moduleName, settingName);
        if (currentSetting instanceof EnableSetting) {
            ((EnableSetting) currentSetting).setEnable(Boolean.parseBoolean(settingName));
        } else if (currentSetting instanceof IntegerSetting) {
            ((IntegerSetting) currentSetting).setCurrent(Integer.parseInt(settingName));
        } else if (currentSetting instanceof DoubleSetting) {
            ((DoubleSetting) currentSetting).setCurrent(Double.parseDouble(settingName));
        } else if (currentSetting instanceof LongSetting) {
            ((LongSetting) currentSetting).setCurrent(Long.parseLong(settingName));
        } else if (currentSetting instanceof ModeSetting) {
            ((ModeSetting) currentSetting).setCurrent(settingValue);
        }

        configuration.getCommand().message(currentSetting.getName() + " has setting to " + settingValue + ".");
    }
}
