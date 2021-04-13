package cn.enaium.foxbase.client.clickgui;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.provider.ModuleProvider;
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
public class TypePanel {

    private String type;
    private double x;
    private double y;
    private double width;
    private double height;

    private double prevX;
    private double prevY;

    private boolean hovered;

    private boolean dragging;

    public boolean displayModulePanel;

    private ArrayList<ModulePanel> modulePanels;

    public TypePanel(String type, double x, double y, double width, double height) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.modulePanels = new ArrayList<>();
        ArrayList<ModuleProvider> modules = new ArrayList<>(CF4M.INSTANCE.getModule().getAllByType(this.type));
        for (ModuleProvider module : modules) {
            this.modulePanels.add(new ModulePanel(module));
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.hovered = Render2D.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        if (this.dragging) {
            this.x = this.prevX + mouseX;
            this.y = this.prevY + mouseY;
        }
        Render2D.drawRectWH(matrices, this.x, this.y, this.width, this.height, this.hovered ? ColorUtils.SELECT : ColorUtils.BG);
        FontUtils.drawHVCenteredString(matrices, this.type, this.x + this.width / 2, this.y + this.height / 2, Color.WHITE.getRGB());
        if (this.displayModulePanel) {
            double moduleY = this.y + this.height;
            for (ModulePanel modulePanel : this.modulePanels) {
                modulePanel.render(matrices, mouseX, mouseY, delta, this.x + this.width / 2 - (getWidestModule() + 10) / 2.0, moduleY, getWidestModule() + 10, FontUtils.getFontHeight() + 10);
                moduleY += FontUtils.getFontHeight() + 10;
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hovered) {
            if (button == 0) {
                this.dragging = true;
                this.prevX = this.x - mouseX;
                this.prevY = this.y - mouseY;
            } else if (button == 1) {
                this.displayModulePanel = !this.displayModulePanel;
            }
        }

        for (ModulePanel modulePanel : this.modulePanels) {
            modulePanel.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.dragging = false;
        }
    }

    private int getWidestModule() {
        int width = 0;
        for (ModuleProvider module : CF4M.INSTANCE.getModule().getAll()) {
            String name = module.getName();
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

}
