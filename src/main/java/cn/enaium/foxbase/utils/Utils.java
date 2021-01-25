package cn.enaium.foxbase.utils;

import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.setting.settings.ModeSetting;
import cn.enaium.cf4m.CF4M;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Utils {
    public static ArrayList<Setting> getSettingsForModule(Module m) {
        ArrayList<Setting> settings = new ArrayList<>();

        for (Setting s : CF4M.getInstance().settingManager.settings) {
            if (s.getModule().equals(m)) {
                settings.add(s);
            }
        }

        if (settings.isEmpty()) {
            return null;
        }

        return settings;
    }

    public static ArrayList<Module> getModulesForCategory(Category c) {
        ArrayList<Module> modules = new ArrayList<>();

        for (Module m : CF4M.getInstance().moduleManager.modules) {
            if (m.getCategory().equals(c)) {
                modules.add(m);
            }
        }

        return modules;
    }

    public static int getCurrentModeIndex(ModeSetting modeSetting) {
        int index = 0;
        for (String s : modeSetting.getModes()) {
            if (s.equalsIgnoreCase(modeSetting.getCurrent())) {
                return index;
            }
            index++;
        }
        return index;
    }
}
