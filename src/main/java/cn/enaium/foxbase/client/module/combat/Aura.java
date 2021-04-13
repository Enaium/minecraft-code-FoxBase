package cn.enaium.foxbase.client.module.combat;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.annotation.module.Setting;
import cn.enaium.foxbase.client.event.Events.UpdatingEvent;
import cn.enaium.foxbase.client.setting.IntegerSetting;
import cn.enaium.foxbase.client.setting.ModeSetting;

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
    public void onUpdate(UpdatingEvent e) {

    }
}
