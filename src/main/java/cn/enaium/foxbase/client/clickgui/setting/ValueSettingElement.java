package cn.enaium.foxbase.client.clickgui.setting;

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
public class ValueSettingElement extends SettingElement {

    private boolean addHovered;
    private boolean removeHovered;

    public ValueSettingElement(SettingProvider setting) {
        super(setting);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        super.render(matrices, mouseX, mouseY, delta, x, y, width, height);
        this.addHovered = Render2D.isHovered(mouseX, mouseY, x + width + 2, y + 2, height / 2 - 4, height - 4);
        this.removeHovered = Render2D.isHovered(mouseX, mouseY, x + width + 2 + 8, y + 2, height / 2 - 4, height - 4);
        Render2D.drawRectWH(matrices, x + width + 2, y + 2, height / 2 - 4, height - 4, this.addHovered ? ColorUtils.CHECK_BG : ColorUtils.SELECT);
        Render2D.drawRectWH(matrices, x + width + 2 + 8, y + 2, height / 2 - 4, height - 4, this.removeHovered ? ColorUtils.CHECK_BG : ColorUtils.SELECT);
        FontUtils.drawVCenteredString(matrices, "+", x + width + 2, y + 2 + height / 2, Color.WHITE.getRGB());
        FontUtils.drawVCenteredString(matrices, "-", x + width + 2 + 8, y + 2 + height / 2, Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.addHovered && button == 0) {
            if (this.setting.getSetting() instanceof IntegerSetting) {
                if (this.setting.<IntegerSetting>getSetting().getCurrent() < this.setting.<IntegerSetting>getSetting().getMax()) {
                    this.setting.<IntegerSetting>getSetting().setCurrent(this.setting.<IntegerSetting>getSetting().getCurrent() + 1);
                }
            } else if (this.setting.getSetting() instanceof FloatSetting) {
                if (this.setting.<FloatSetting>getSetting().getCurrent() < this.setting.<FloatSetting>getSetting().getMax()) {
                    this.setting.<FloatSetting>getSetting().setCurrent(this.setting.<FloatSetting>getSetting().getCurrent() + 0.1F);
                }
            } else if (this.setting.getSetting() instanceof DoubleSetting) {
                if (this.setting.<DoubleSetting>getSetting().getCurrent() < this.setting.<DoubleSetting>getSetting().getMax()) {
                    this.setting.<DoubleSetting>getSetting().setCurrent(this.setting.<DoubleSetting>getSetting().getCurrent() + 0.1D);
                }
            } else if (this.setting.getSetting() instanceof LongSetting) {
                if (this.setting.<LongSetting>getSetting().getCurrent() < this.setting.<LongSetting>getSetting().getMax()) {
                    this.setting.<LongSetting>getSetting().setCurrent(this.setting.<LongSetting>getSetting().getCurrent() + 1L);
                }
            } else if (this.setting.getSetting() instanceof ModeSetting) {
                try {
                    this.setting.<ModeSetting>getSetting().setCurrent(this.setting.<ModeSetting>getSetting().getModes().get(this.setting.<ModeSetting>getSetting().getCurrentModeIndex() + 1));
                } catch (Exception e) {
                    this.setting.<ModeSetting>getSetting().setCurrent(this.setting.<ModeSetting>getSetting().getModes().get(0));
                }
            }
        } else if (this.removeHovered && button == 0) {
            if (this.setting.getSetting() instanceof IntegerSetting) {
                if (this.setting.<IntegerSetting>getSetting().getCurrent() > this.setting.<IntegerSetting>getSetting().getMin()) {
                    this.setting.<IntegerSetting>getSetting().setCurrent(this.setting.<IntegerSetting>getSetting().getCurrent() - 1);
                }
            } else if (this.setting.getSetting() instanceof FloatSetting) {
                if (this.setting.<FloatSetting>getSetting().getCurrent() > this.setting.<FloatSetting>getSetting().getMin()) {
                    this.setting.<FloatSetting>getSetting().setCurrent(this.setting.<FloatSetting>getSetting().getCurrent() - 0.1F);
                }
            } else if (this.setting.getSetting() instanceof DoubleSetting) {
                if (this.setting.<DoubleSetting>getSetting().getCurrent() > this.setting.<DoubleSetting>getSetting().getMin()) {
                    this.setting.<DoubleSetting>getSetting().setCurrent(this.setting.<DoubleSetting>getSetting().getCurrent() - 0.1D);
                }
            } else if (this.setting.getSetting() instanceof LongSetting) {
                if (this.setting.<LongSetting>getSetting().getCurrent() > this.setting.<LongSetting>getSetting().getMin()) {
                    this.setting.<LongSetting>getSetting().setCurrent(this.setting.<LongSetting>getSetting().getCurrent() - 1L);
                }
            } else if (this.setting.getSetting() instanceof ModeSetting) {
                try {
                    this.setting.<ModeSetting>getSetting().setCurrent(this.setting.<ModeSetting>getSetting().getModes().get(this.setting.<ModeSetting>getSetting().getCurrentModeIndex() - 1));
                } catch (Exception e) {
                    this.setting.<ModeSetting>getSetting().setCurrent(this.setting.<ModeSetting>getSetting().getModes().get(this.setting.<ModeSetting>getSetting().getModes().size() - 1));
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
