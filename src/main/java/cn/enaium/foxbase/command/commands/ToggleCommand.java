package cn.enaium.foxbase.command.commands;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.command.Command;
import cn.enaium.foxbase.utils.ChatUtils;
import cn.enaium.foxbase.module.Module;

public class ToggleCommand implements Command {

    @Override
    public boolean run(String[] args) {

        if (args.length == 2) {

            Module module = FoxBase.instance.moduleManager.getModule(args[1]);

            if (module == null) {
                ChatUtils.message("The module with the name " + args[1] + " does not exist.");
                return true;
            }

            module.toggle();

            return true;
        }


        return false;
    }

    @Override
    public String[] usage() {
        return new String[]{"-toggle [module]"};
    }
}
