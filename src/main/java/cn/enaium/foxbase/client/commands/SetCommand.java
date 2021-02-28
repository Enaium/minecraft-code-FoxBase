package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.foxbase.client.settings.*;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command({"s", "setting"})
public class SetCommand {

    private Object currentModule;
    private ArrayList<Object> settings;
    private Object currentSetting;

    @Exec
    public void exec(@Param("Module") String moduleName) {
        currentModule = CF4M.INSTANCE.module.getModule(moduleName);
        if (currentModule == null) {
            CF4M.INSTANCE.configuration.message("The module with the name \"" + moduleName + "\" does not exist.");
            return;
        }

        settings = CF4M.INSTANCE.setting.getSettings(currentModule);

        if (settings == null) {
            CF4M.INSTANCE.configuration.message("The module with the name \"" + moduleName + "\" no setting exists.");
            return;
        }

        CF4M.INSTANCE.configuration.message("Here are the list of settings:");

        for (Object s : settings) {
            CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.setting.getName(currentModule, s) + "(" + s.getClass().getSimpleName() + ")" + CF4M.INSTANCE.setting.getDescription(currentModule, s));
            if (s instanceof ModeSetting) {
                ((ModeSetting) s).getModes().forEach(CF4M.INSTANCE.configuration::message);
            }
        }
    }

    @Exec
    public void exec(@Param("Module") String moduleName, @Param("Setting") String settingName) {
        exec(moduleName);
        Object setting = CF4M.INSTANCE.setting.getSetting(currentModule, settingName);
        if (setting != null) {
            currentSetting = setting;
            CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.setting.getName(currentModule, currentSetting) + "|" + currentSetting.getClass().getSimpleName());
        } else {
            CF4M.INSTANCE.configuration.message("The setting with the name \"" + settingName + "\" does not exist.");
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

        CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.setting.getName(currentModule, currentSetting) + " has setting to " + settingValue + ".");
    }
}
