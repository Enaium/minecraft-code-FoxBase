package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Command;
import cn.enaium.cf4m.command.ICommand;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command({"t", "toggle"})
public class ToggleCommand implements ICommand {

    @Override
    public boolean run(String[] args) {
        if (args.length == 2) {
            Object module = CF4M.getInstance().module.getModule(args[1]);

            if (module == null) {
                CF4M.getInstance().configuration.message("The module with the name " + args[1] + " does not exist.");
                return true;
            }

            CF4M.getInstance().module.enable(module);
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "<module>";
    }
}
