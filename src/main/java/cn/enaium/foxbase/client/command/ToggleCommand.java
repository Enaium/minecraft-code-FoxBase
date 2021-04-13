package cn.enaium.foxbase.client.command;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Auto;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.provider.ModuleProvider;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Auto
@Command({"t", "toggle"})
public class ToggleCommand {

    private ModuleContainer moduleContainer;
    private IConfiguration configuration;

    @Exec
    private void exec(@Param("module") String name) {
        ModuleProvider module = moduleContainer.getByName(name);

        if (module == null) {
            configuration.getCommand().message("The module with the name " + name + " does not exist.");
            return;
        }

        module.enable();
    }
}
