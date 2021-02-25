package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.annotation.Command;
import cn.enaium.cf4m.command.ICommand;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.settings.*;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command({"s", "setting"})
public class SetCommand implements ICommand {
    @Override
    public boolean run(String[] args) {
        if (args.length == 2 || args.length == 4) {
            Object module = CF4M.INSTANCE.module.getModule(args[1]);

            ArrayList<Object> settings = CF4M.INSTANCE.setting.getSettings(module);

            if (module == null) {
                CF4M.INSTANCE.configuration.message("The module with the name \"" + args[1] + "\" does not exist.");
                return false;
            }

            if (settings == null) {
                CF4M.INSTANCE.configuration.message("The module with the name \"" + args[1] + "\" no setting exists.");
                return false;
            }

            if (args.length == 2) {
                CF4M.INSTANCE.configuration.message("Here are the list of settings:");

                for (Object s : settings) {
                    CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.setting.getName(module, s) + "(" + s.getClass().getSimpleName() + ")");
                    if (s instanceof ModeSetting) {
                        ((ModeSetting) s).getModes().forEach(CF4M.INSTANCE.configuration::message);
                    }
                }
                return true;
            }

            if (args.length == 4) {
                for (Object s : settings) {
                    if (CF4M.INSTANCE.setting.getName(module, s).equalsIgnoreCase(args[2])) {
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
                        CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.setting.getName(module, s) + " has setting to " + args[3] + ".");
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String usage() {
        return "<module>";
    }
}
