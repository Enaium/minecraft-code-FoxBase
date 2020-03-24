package cn.enaium.foxbase.module.modules.render;

import cn.enaium.foxbase.gui.clickgui.ClickGUI;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class GUI extends Module {
    public GUI() {
        super("GUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(new ClickGUI());
        toggle();
    }
}
