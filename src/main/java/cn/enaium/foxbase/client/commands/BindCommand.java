package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Command;
import cn.enaium.cf4m.command.ICommand;
import cn.enaium.foxbase.client.configuration.FoxBaseConfiguration;
import org.lwjgl.input.Keyboard;

@Command({"b","bind"})
public class BindCommand implements ICommand {

    @Override
    public boolean run(String[] args) {
        if (args.length == 3) {

            Object m = CF4M.INSTANCE.module.getModule(args[1]);

            if (m == null)
                return false;

            CF4M.INSTANCE.module.setKey(m,Keyboard.getKeyIndex(args[2].toUpperCase()));
            CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.module.getName(m) + " has been bound to " + args[2] + ".");
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "<module> <key>";
    }
}
