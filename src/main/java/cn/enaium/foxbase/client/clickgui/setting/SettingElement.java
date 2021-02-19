package cn.enaium.foxbase.client.clickgui.setting;

import cn.enaium.cf4m.setting.SettingBase;
import cn.enaium.cf4m.setting.settings.DoubleSetting;
import cn.enaium.cf4m.setting.settings.FloatSetting;
import cn.enaium.cf4m.setting.settings.IntegerSetting;
import cn.enaium.cf4m.setting.settings.ModeSetting;
import cn.enaium.foxbase.client.utils.ColorUtils;
import cn.enaium.foxbase.client.utils.FontUtils;
import cn.enaium.foxbase.client.utils.Render2D;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class SettingElement {

    protected SettingBase setting;

    public SettingElement(SettingBase setting) {
        this.setting = setting;
    }


    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        Render2D.drawRectWH(matrices, x, y, width + height, height, ColorUtils.BG);
        String name = this.setting.getName();
        if (this.setting instanceof IntegerSetting) {
            name = name + ":" + ((IntegerSetting) this.setting).getCurrent();
        } else if (this.setting instanceof DoubleSetting) {
            name = name + ":" + ((DoubleSetting) this.setting).getCurrent();
        } else if (this.setting instanceof FloatSetting) {
            name = name + ":" + ((FloatSetting) this.setting).getCurrent();
        } else if (this.setting instanceof ModeSetting) {
            name = name + ":" + ((ModeSetting) this.setting).getCurrent();
        }
        FontUtils.drawHVCenteredString(matrices, name, x + width / 2, y + height / 2, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}