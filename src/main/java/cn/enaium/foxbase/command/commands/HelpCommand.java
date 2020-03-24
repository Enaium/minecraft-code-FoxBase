package cn.enaium.foxbase.command.commands;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.command.Command;
import cn.enaium.foxbase.utils.ChatUtils;

public class HelpCommand implements Command {

    @Override
    public boolean run(String[] args) {
        ChatUtils.message("Here are the list of commands:");
        for (Command c : FoxBase.instance.commandManager.getCommands().values()) {
            for(String s : c.usage()) {
                ChatUtils.message("USAGE: " + s);
            }
        }
        return true;
    }

    @Override
    public String[] usage() {
        return new String[]{"-help"};
    }

}
