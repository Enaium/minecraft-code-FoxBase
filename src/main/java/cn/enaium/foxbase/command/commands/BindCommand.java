package cn.enaium.foxbase.command.commands;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.command.Command;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.utils.ChatUtils;
import org.lwjgl.input.Keyboard;

public class BindCommand implements Command {

    @Override
    public boolean run(String[] args) {
        if (args.length == 3) {

            Module m = FoxBase.instance.moduleManager.getModule(args[1]);

            if (m == null)
                return false;

            m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
            ChatUtils.message(m.getName() + " has been bound to " + args[2] + ".");
            return true;
        }
        return false;
    }

    @Override
    public String[] usage() {
        return new String[]{"-bind [module] [key]"};
    }

}
