package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.command.Command;
import cn.enaium.cf4m.command.CommandAT;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.utils.ChatUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@CommandAT({"t", "toggle"})
public class ToggleCommand implements Command {

    @Override
    public void run(String[] args) {

        if (args.length == 2) {

            Module module = getModule(args[1]);
            if (module == null) {
                ChatUtils.message("The module with the name " + args[1] + " does not exist.");
                return;
            }

            module.enable();
        }
    }

    public Module getModule(String name) {
        for (Module m : CF4M.getInstance().moduleManager.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<String> usage() {
        return Arrays.asList("-toggle [module]");
    }

}
