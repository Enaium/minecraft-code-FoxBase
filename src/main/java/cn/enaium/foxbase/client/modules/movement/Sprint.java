package cn.enaium.foxbase.client.modules.movement;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module(value = "Sprint", key = Keyboard.KEY_V)
public class Sprint {
    @Event
    public void onUpdate(UpdateEvent e) {
        CF4M.getInstance().module.setValue(this, "tag", "Auto");
        Minecraft.getMinecraft().thePlayer.setSprinting(true);
    }
}
