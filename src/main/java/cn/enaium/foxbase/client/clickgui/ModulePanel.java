package cn.enaium.foxbase.client.clickgui;

import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.setting.settings.*;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.clickgui.setting.BooleanSettingElement;
import cn.enaium.foxbase.client.clickgui.setting.SettingElement;
import cn.enaium.foxbase.client.clickgui.setting.ValueSettingElement;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;
import cn.enaium.foxbase.utils.Utils;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModulePanel {

    private Module module;
    private boolean hovered;

    private boolean displaySettingElement;

    private ArrayList<SettingElement> settingElements;

    public ModulePanel(Module module) {
        this.module = module;
        this.settingElements = new ArrayList<>();
        ArrayList<Setting> settings = Utils.getSettingsForModule(this.module);
        if (settings != null) {
            for (Setting setting : settings) {
                if (setting instanceof EnableSetting) {
                    this.settingElements.add(new BooleanSettingElement((EnableSetting) setting));
                } else if (setting instanceof IntegerSetting || setting instanceof DoubleSetting || setting instanceof FloatSetting || setting instanceof LongSetting || setting instanceof ModeSetting) {
                    this.settingElements.add(new ValueSettingElement(setting));
                }
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        this.hovered = Render2D.isHovered(mouseX, mouseY, x, y, width, height);
        int color = ColorUtils.BG;
        if (this.module.isEnable()) {
            color = ColorUtils.TOGGLE;
        }
        if (this.hovered) {
            color = ColorUtils.SELECT;
        }

        Render2D.drawRectWH(matrices, x, y, width, height, color);
        FontUtils.drawHVCenteredString(matrices, this.module.getName(), x + width / 2, y + height / 2, Color.WHITE.getRGB());
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
                this.module.enable();
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
        for (Setting m : CF4M.getInstance().settingManager.settings) {
            String name = m.getName();
            if (m instanceof IntegerSetting) {
                name = name + ":" + ((IntegerSetting) m).getCurrent();
            } else if (m instanceof DoubleSetting) {
                name = name + ":" + ((DoubleSetting) m).getCurrent();
            } else if (m instanceof FloatSetting) {
                name = name + ":" + ((FloatSetting) m).getCurrent();
            } else if (m instanceof ModeSetting) {
                name = name + ":" + ((ModeSetting) m).getCurrent();
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
