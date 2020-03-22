package cn.enaium.foxbase.module.modules.movement;

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
public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_V, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.thePlayer.setSprinting(true);
    }
}
