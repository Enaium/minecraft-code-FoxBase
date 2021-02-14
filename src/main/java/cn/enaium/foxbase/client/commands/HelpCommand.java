package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Command;
import cn.enaium.cf4m.command.ICommand;

import java.util.Arrays;
import java.util.Map;

@Command({"h","help"})
public class HelpCommand implements ICommand {

    @Override
    public boolean run(String[] args) {
        CF4M.getInstance().configuration.message("Here are the list of commands:");
        for (Map.Entry<String[],ICommand> c : CF4M.getInstance().command.getCommands().entrySet()) {
            CF4M.getInstance().configuration.message("USAGE: " + Arrays.toString(c.getKey()));
        }
        return true;
    }

    @Override
    public String usage() {
        return "help";
    }
}
