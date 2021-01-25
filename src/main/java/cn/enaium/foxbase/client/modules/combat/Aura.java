package cn.enaium.foxbase.client.modules.combat;

import cn.enaium.cf4m.event.EventAT;
import cn.enaium.cf4m.event.events.UpdateEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.setting.SettingAT;
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
public class Aura extends Module {

    @SettingAT
    IntegerSetting cps = new IntegerSetting(this, "CPS", "CPS", 7, 1, 20);

    @SettingAT
    ModeSetting mode = new ModeSetting(this, "MODE", "MODE", "MOD1", new ArrayList<>(Arrays.asList(new String[]{"MOD1", "MOD2", "MOD3"})));


    public Aura() {
        super("Aura", "Aura", GLFW.GLFW_KEY_R, Category.COMBAT);
    }

    @EventAT
    public void onUpdate(UpdateEvent e) {

    }
}
