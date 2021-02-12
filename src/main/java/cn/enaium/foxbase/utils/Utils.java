package cn.enaium.foxbase.utils;

import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.settings.ModeSetting;
import cn.enaium.cf4m.CF4M;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Utils {

    public static ArrayList<Object> getModulesForCategory(Category c) {
        ArrayList<Object> modules = new ArrayList<>();

        for (Object module : CF4M.getInstance().module.getModules()) {
            if (CF4M.getInstance().module.getCategory(module).equals(c)) {
                modules.add(module);
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
