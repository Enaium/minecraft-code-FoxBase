package cn.enaium.foxbase.module.modules.render;

import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventUpdate;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class FullBright extends Module {

    public FullBright() {
        super("FullBright", Keyboard.KEY_G, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.gameSettings.gammaSetting = 300;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = 1;
    }


}
