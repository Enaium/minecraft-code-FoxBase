package cn.enaium.foxbase.client.clickgui;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.foxbase.client.clickgui.setting.BooleanSettingElement;
import cn.enaium.foxbase.client.clickgui.setting.SettingElement;
import cn.enaium.foxbase.client.clickgui.setting.ValueSettingElement;
import cn.enaium.foxbase.client.setting.*;
import cn.enaium.foxbase.client.utils.ColorUtils;
import cn.enaium.foxbase.client.utils.FontUtils;
import cn.enaium.foxbase.client.utils.Render2D;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModulePanel {

    private final ModuleProvider module;
    private boolean hovered;

    private boolean displaySettingElement;

    private final ArrayList<SettingElement> settingElements;

    public ModulePanel(ModuleProvider module) {
        this.module = module;
        this.settingElements = new ArrayList<>();
        ArrayList<SettingProvider> settings = module.getSetting().getAll();
        if (settings != null) {
            for (SettingProvider setting : settings) {
                if (setting instanceof EnableSetting) {
                    this.settingElements.add(new BooleanSettingElement(setting));
                } else if (setting instanceof IntegerSetting || setting instanceof DoubleSetting || setting instanceof FloatSetting || setting instanceof LongSetting || setting instanceof ModeSetting) {
                    this.settingElements.add(new ValueSettingElement(setting));
                }
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        this.hovered = Render2D.isHovered(mouseX, mouseY, x, y, width, height);
        int color = ColorUtils.BG;
        if (this.module.getEnable()) {
            color = ColorUtils.TOGGLE;
        }
        if (this.hovered) {
            color = ColorUtils.SELECT;
        }

        Render2D.drawRectWH(matrices, x, y, width, height, color);
        FontUtils.drawHVCenteredString(matrices, module.getName(), x + width / 2, y + height / 2, Color.WHITE.getRGB());
        if (this.displaySettingElement) {
            double SettingElementY = y;
            for (SettingElement settingElement : settingElements) {
                settingElement.render(matrices, mouseX, mouseY, delta, x + width, SettingElementY, getWidestSetting(), height);
                SettingElementY += height;
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hovered) {
            if (button == 0) {
                if (!this.module.getName().equals("GUI")) {
                    module.enable();
                }
            } else if (button == 1) {
                this.displaySettingElement = !displaySettingElement;
            }
        }

        for (SettingElement settingElement : settingElements) {
            settingElement.mouseClicked(mouseX, mouseY, button);
        }
    }

    private int getWidestSetting() {
        int width = 0;
        for (SettingProvider setting : module.getSetting().getAll()) {
            String name = module.getName();
            if (setting instanceof IntegerSetting) {
                name = name + ":" + ((IntegerSetting) setting).getCurrent();
            } else if (setting instanceof DoubleSetting) {
                name = name + ":" + ((DoubleSetting) setting).getCurrent();
            } else if (setting instanceof FloatSetting) {
                name = name + ":" + ((FloatSetting) setting).getCurrent();
            } else if (setting instanceof ModeSetting) {
                name = name + ":" + ((ModeSetting) setting).getCurrent();
            }
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }
}
