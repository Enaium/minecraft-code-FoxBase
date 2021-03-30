package cn.enaium.foxbase.client.clickgui.setting;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.foxbase.client.setting.*;
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

    protected SettingProvider setting;

    public SettingElement(SettingProvider setting) {
        this.setting = setting;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        Render2D.drawRectWH(matrices, x, y, width + height, height, ColorUtils.BG);
        String name = setting.getName();
        if (this.setting.getSetting() instanceof IntegerSetting) {
            name = name + ":" + this.setting.<IntegerSetting>getSetting().getCurrent();
        } else if (this.setting.getSetting() instanceof DoubleSetting) {
            name = name + ":" + this.setting.<DoubleSetting>getSetting().getCurrent();
        } else if (this.setting.getSetting() instanceof FloatSetting) {
            name = name + ":" + this.setting.<FloatSetting>getSetting().getCurrent();
        } else if (this.setting.getSetting() instanceof LongSetting) {
            name = name + ":" + this.setting.<LongSetting>getSetting().getCurrent();
        } else if (this.setting.getSetting() instanceof ModeSetting) {
            name = name + ":" + this.setting.<ModeSetting>getSetting().getCurrent();
        }
        FontUtils.drawHVCenteredString(matrices, name, x + width / 2, y + height / 2, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}
