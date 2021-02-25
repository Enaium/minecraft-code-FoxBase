package cn.enaium.foxbase.client.modules.combat;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.foxbase.client.settings.IntegerSetting;
import cn.enaium.foxbase.client.settings.ModeSetting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("Aura")
public class Aura {

    @Setting("CPS")
    IntegerSetting cps = new IntegerSetting(7, 1, 20);

    @Setting("Mode")
    ModeSetting mode = new ModeSetting("MOD1", new ArrayList<>(Arrays.asList("MOD1", "MOD2", "MOD3")));

    @Event
    public void onUpdate(UpdateEvent e) {

    }
}
