package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.command.Command;
import cn.enaium.cf4m.command.CommandAT;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.setting.settings.*;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.utils.ChatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@CommandAT({"s", "setting"})
public class SetCommand implements Command {
    @Override
    public void run(String[] args) {
        if (args.length == 2 || args.length == 4) {

            Module module = getModule(args[1]);
            ArrayList<Setting> settings = getSettingsForModule(getModule(args[1]));

            if (module == null) {
                ChatUtils.message("The module with the name \"" + args[1] + "\" does not exist.");
                return;
            }

            if (settings == null) {
                ChatUtils.message("The module with the name \"" + args[1] + "\" no setting exists.");
                return;
            }

            if (args.length == 2) {
                ChatUtils.message("Here are the list of settings:");

                for (Setting s : settings) {
                    ChatUtils.message(s.getName() + "(" + s.getClass().getSimpleName() + ")");
                    if (s instanceof ModeSetting) {
                        ((ModeSetting) s).getModes().forEach(ChatUtils::message);
                    }
                }
            }

            if (args.length == 4) {
                for (Setting s : settings) {
                    if (s.getName().equalsIgnoreCase(args[2])) {
                        if (s instanceof EnableSetting) {
                            ((EnableSetting) s).setEnable(Boolean.parseBoolean(args[3]));
                        } else if (s instanceof IntegerSetting) {
                            ((IntegerSetting) s).setCurrent(Integer.parseInt(args[3]));
                        } else if (s instanceof DoubleSetting) {
                            ((DoubleSetting) s).setCurrent(Double.parseDouble(args[3]));
                        } else if (s instanceof FloatSetting) {
                            ((FloatSetting) s).setCurrent(Float.parseFloat(args[3]));
                        } else if (s instanceof ModeSetting) {
                            ((ModeSetting) s).setCurrent(args[3]);
                        }
                        ChatUtils.message(s.getName() + " has setting to " + args[3] + ".");
                    }
                }
            }
//            return;
        }
    }

    @Override
    public List<String> usage() {
        return Arrays.asList("-set [module]");
    }

    private ArrayList<Setting> getSettingsForModule(Module m) {
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

    public Module getModule(String name) {
        for (Module m : CF4M.getInstance().moduleManager.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
}
