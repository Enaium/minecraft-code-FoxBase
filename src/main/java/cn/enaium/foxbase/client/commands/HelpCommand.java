package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.command.Command;
import cn.enaium.cf4m.command.CommandAT;
import cn.enaium.foxbase.utils.ChatUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class HelpCommand implements Command {

    @Override
    public void run(String[] args) {
        ChatUtils.message("Here are the list of commands:");
        for (Command c : CF4M.getInstance().commandManager.commands.values()) {
            for (String s : c.usage()) {
                ChatUtils.message("USAGE:" + CF4M.getInstance().commandManager.prefix + s);
            }
        }
    }

    @Override
    public List<String> usage() {
        return Arrays.asList("help");
    }
}

