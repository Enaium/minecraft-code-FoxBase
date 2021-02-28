package cn.enaium.foxbase.client.clickgui.setting;

import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.settings.*;
import cn.enaium.foxbase.client.utils.ColorUtils;
import cn.enaium.foxbase.client.utils.FontUtils;
import cn.enaium.foxbase.client.utils.Render2DUtils;

import java.awt.*;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class SettingElement {

    protected Object setting;
    protected Object module;

    public SettingElement(Object setting, Object module) {
        this.setting = setting;
        this.module = module;
    }


    public void render(int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        Render2DUtils.drawRectWH(x, y, width + height, height, ColorUtils.BG);
        String name = CF4M.INSTANCE.setting.getName(module, setting);
        if (this.setting instanceof IntegerSetting) {
            name = name + ":" + ((IntegerSetting) this.setting).getCurrent();
        } else if (this.setting instanceof DoubleSetting) {
            name = name + ":" + ((DoubleSetting) this.setting).getCurrent();
        } else if (this.setting instanceof FloatSetting) {
            name = name + ":" + ((FloatSetting) this.setting).getCurrent();
        } else if (this.setting instanceof LongSetting) {
            name = name + ":" + ((LongSetting) this.setting).getCurrent();
        } else if (this.setting instanceof ModeSetting) {
            name = name + ":" + ((ModeSetting) this.setting).getCurrent();
        }
        FontUtils.drawHVCenteredString(name, x + width / 2, y + height / 2, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}
