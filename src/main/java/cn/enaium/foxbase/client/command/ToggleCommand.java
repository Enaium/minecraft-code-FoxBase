package cn.enaium.foxbase.client.command;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.foxbase.client.utils.ChatUtils;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command({"t", "toggle"})
public class ToggleCommand {

    @Autowired
    private ModuleContainer moduleContainer;

    @Exec
    private void exec(@Param("module") String name) {
        ModuleProvider module = moduleContainer.getByName(name);

        if (module == null) {
            ChatUtils.message("The module with the name " + name + " does not exist.");
            return;
        }

        module.enable();
    }
}
