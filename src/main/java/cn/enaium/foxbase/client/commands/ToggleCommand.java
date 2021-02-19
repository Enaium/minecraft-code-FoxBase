package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Command;
import cn.enaium.cf4m.command.ICommand;

@Command({"t","toggle"})
public class ToggleCommand implements ICommand {

    @Override
    public boolean run(String[] args) {

        if (args.length == 2) {

            Object module = CF4M.INSTANCE.module.getModule(args[1]);

            if (module == null) {
                CF4M.INSTANCE.configuration.message("The module with the name " + args[1] + " does not exist.");
                return true;
            }

            CF4M.INSTANCE.module.enable(module);

            return true;
        }


        return false;
    }

    @Override
    public String usage() {
        return "<module>";
    }
}
