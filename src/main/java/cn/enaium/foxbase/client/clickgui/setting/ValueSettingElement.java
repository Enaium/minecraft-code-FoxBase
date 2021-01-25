package cn.enaium.foxbase.client.clickgui.setting;

import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.setting.settings.*;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;
import cn.enaium.foxbase.utils.Utils;
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

    public ValueSettingElement(Setting setting) {
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
            if (this.setting instanceof IntegerSetting) {
                if (((IntegerSetting) this.setting).getCurrent() < ((IntegerSetting) this.setting).getMax()) {
                    ((IntegerSetting) this.setting).setCurrent(((IntegerSetting) this.setting).getCurrent() + 1);
                }
            } else if (this.setting instanceof DoubleSetting) {
                if (((DoubleSetting) this.setting).getCurrent() < ((DoubleSetting) this.setting).getMax()) {
                    ((DoubleSetting) this.setting).setCurrent(((DoubleSetting) this.setting).getCurrent() + 0.1D);
                }
            } else if (this.setting instanceof FloatSetting) {
                if (((FloatSetting) this.setting).getCurrent() < ((FloatSetting) this.setting).getMax()) {
                    ((FloatSetting) this.setting).setCurrent(((FloatSetting) this.setting).getCurrent() + 0.1F);
                }
            } else if (this.setting instanceof LongSetting) {
                if (((LongSetting) this.setting).getCurrent() < ((LongSetting) this.setting).getMax()) {
                    ((LongSetting) this.setting).setCurrent(((LongSetting) this.setting).getCurrent() + 1L);
                }
            } else if (this.setting instanceof ModeSetting) {
                try {
                    ((ModeSetting) this.setting).setCurrent(((ModeSetting) this.setting).getModes().get(Utils.getCurrentModeIndex((ModeSetting) this.setting) + 1));
                } catch (Exception e) {
                    ((ModeSetting) this.setting).setCurrent(((ModeSetting) this.setting).getModes().get(0));
                }
            }
        } else if (this.removeHovered && button == 0) {
            if (this.setting instanceof IntegerSetting) {
                if (((IntegerSetting) this.setting).getCurrent() > ((IntegerSetting) this.setting).getMin()) {
                    ((IntegerSetting) this.setting).setCurrent(((IntegerSetting) this.setting).getCurrent() - 1);
                }
            } else if (this.setting instanceof DoubleSetting) {
                if (((DoubleSetting) this.setting).getCurrent() > ((DoubleSetting) this.setting).getMin()) {
                    ((DoubleSetting) this.setting).setCurrent(((DoubleSetting) this.setting).getCurrent() - 0.1D);
                }
            } else if (this.setting instanceof FloatSetting) {
                if (((FloatSetting) this.setting).getCurrent() > ((FloatSetting) this.setting).getMin()) {
                    ((FloatSetting) this.setting).setCurrent(((FloatSetting) this.setting).getCurrent() - 0.1F);
                }
            } else if (this.setting instanceof LongSetting) {
                if (((LongSetting) this.setting).getCurrent() > ((LongSetting) this.setting).getMin()) {
                    ((LongSetting) this.setting).setCurrent(((LongSetting) this.setting).getCurrent() - 1L);
                }
            } else if (this.setting instanceof ModeSetting) {
                try {
                    ((ModeSetting) this.setting).setCurrent(((ModeSetting) this.setting).getModes().get(Utils.getCurrentModeIndex((ModeSetting) this.setting) - 1));
                } catch (Exception e) {
                    ((ModeSetting) this.setting).setCurrent(((ModeSetting) this.setting).getModes().get(((ModeSetting) this.setting).getModes().size() - 1));
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
