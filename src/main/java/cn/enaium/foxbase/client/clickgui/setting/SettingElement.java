package cn.enaium.foxbase.client.clickgui.setting;

import cn.enaium.cf4m.setting.SettingBase;
import cn.enaium.cf4m.setting.settings.*;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2DUtils;

import java.awt.*;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class SettingElement {

    protected SettingBase setting;

    public SettingElement(SettingBase setting) {
        this.setting = setting;
    }


    public void render(int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        Render2DUtils.drawRectWH(x, y, width + height, height, ColorUtils.BG);
        String name = this.setting.getName();
        if (this.setting instanceof IntegerSetting) {
            name = name + ":" + ((IntegerSetting) this.setting).getCurrent();
        } else if (this.setting instanceof DoubleSetting) {
            name = name + ":" + ((DoubleSetting) this.setting).getCurrent();
        } else if (this.setting instanceof FloatSetting) {
            name = name + ":" + ((FloatSetting) this.setting).getCurrent();
        }  else if (this.setting instanceof LongSetting) {
            name = name + ":" + ((LongSetting) this.setting).getCurrent();
        }else if (this.setting instanceof ModeSetting) {
            name = name + ":" + ((ModeSetting) this.setting).getCurrent();
        }
        FontUtils.drawHVCenteredString(name, x + width / 2, y + height / 2, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}
