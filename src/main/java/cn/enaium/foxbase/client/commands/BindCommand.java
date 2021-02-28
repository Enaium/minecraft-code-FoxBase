package cn.enaium.foxbase.client.commands;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.command.Command;
import cn.enaium.cf4m.annotation.command.Exec;
import cn.enaium.cf4m.annotation.command.Param;
import cn.enaium.foxbase.client.configuration.FoxBaseConfiguration;
import org.lwjgl.input.Keyboard;

@Command({"b", "bind"})
public class BindCommand {

    @Exec
    public void exec(@Param("Module") String moduleName, @Param("key") String key) {
        Object m = CF4M.INSTANCE.module.getModule(moduleName);

        if (m == null) {
            CF4M.INSTANCE.configuration.message("The module with the name " + moduleName + " does not exist.");
            return;
        }

        CF4M.INSTANCE.module.setKey(m, Keyboard.getKeyIndex(key.toUpperCase()));
        CF4M.INSTANCE.configuration.message(CF4M.INSTANCE.module.getName(m) + " has been bound to " + key + ".");
    }
}
