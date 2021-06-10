package cn.enaium.foxbase.client.command;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.foxbase.client.setting.*;
import cn.enaium.foxbase.client.utils.ChatUtils;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command({"s", "setting"})
public class SetCommand {

    @Autowired
    private ModuleContainer module;

    private ModuleProvider currentModule;
    private ArrayList<SettingProvider> settings;
    private SettingProvider currentSetting;

    @Exec
    public void exec(@Param("Module") String moduleName) {
        currentModule = module.getByName(moduleName);
        if (currentModule == null) {
            ChatUtils.message("The module with the name \"" + moduleName + "\" does not exist.");
            return;
        }

        settings = currentModule.getSetting().getAll();

        if (settings == null) {
            ChatUtils.message("The module with the name \"" + moduleName + "\" no setting exists.");
            return;
        }

        ChatUtils.message("Here are the list of settings:");

        for (SettingProvider s : settings) {
            ChatUtils.message(s.getName() + "(" + s.getClass().getSimpleName() + ")" + s.getDescription());
            if (s instanceof ModeSetting) {
                ((ModeSetting) s).getModes().forEach(ChatUtils::message);
            }
        }
    }

    @Exec
    public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName) {
        exec(moduleName);
        SettingProvider setting = currentModule.getSetting().getByName(settingName);
        if (setting != null) {
            currentSetting = setting;
            ChatUtils.message(currentSetting.getName() + "|" + currentSetting.getClass().getSimpleName());
        } else {
            ChatUtils.message("The setting with the name \"" + settingName + "\" does not exist.");
        }
    }

    @Exec
    public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName, @Param("SettingValue") String settingValue) {
        exec(moduleName, settingName);
        if (currentSetting instanceof EnableSetting) {
            ((EnableSetting) currentSetting.getSetting()).setEnable(Boolean.parseBoolean(settingName));
        } else if (currentSetting instanceof IntegerSetting) {
            ((IntegerSetting) currentSetting.getSetting()).setCurrent(Integer.parseInt(settingName));
        } else if (currentSetting instanceof DoubleSetting) {
            ((DoubleSetting) currentSetting.getSetting()).setCurrent(Double.parseDouble(settingName));
        } else if (currentSetting instanceof LongSetting) {
            ((LongSetting) currentSetting.getSetting()).setCurrent(Long.parseLong(settingName));
        } else if (currentSetting instanceof ModeSetting) {
            ((ModeSetting) currentSetting.getSetting()).setCurrent(settingValue);
        }

        ChatUtils.message(currentSetting.getName() + " has setting to " + settingValue + ".");
    }
}
