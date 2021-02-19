package cn.enaium.foxbase.client.module.modules.render;

import cn.enaium.cf4m.annotation.module.Disable;
import cn.enaium.cf4m.annotation.module.Enable;
import cn.enaium.cf4m.annotation.module.Module;
import net.minecraft.client.Minecraft;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("FullBright")
public class FullBright {

    @Enable
    public void onEnable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 300;
    }

    @Disable
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1;
    }


}
