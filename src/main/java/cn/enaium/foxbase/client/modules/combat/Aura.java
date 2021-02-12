package cn.enaium.foxbase.client.modules.combat;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.settings.IntegerSetting;
import cn.enaium.cf4m.setting.settings.ModeSetting;
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

    @Setting
    IntegerSetting cps = new IntegerSetting(this, "CPS", "CPS", 7, 1, 20);

    @Setting
    ModeSetting mode = new ModeSetting(this, "MODE", "MODE", "MOD1", new ArrayList<>(Arrays.asList("MOD1", "MOD2", "MOD3")));

    @Event
    public void onUpdate(UpdateEvent e) {

    }
}
