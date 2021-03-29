package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command({"t", "toggle"})
public class ToggleCommand {
    @Exec
    private void exec(@Param("module") String name) {
        ModuleProvider module = CF4M.module.getByName(name);

        if (module == null) {
            CF4M.configuration.command().message("The module with the name " + name + " does not exist.");
            return;
        }

        module.enable();
    }
}
