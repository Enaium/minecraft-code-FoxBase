package cn.enaium.foxbase.client.clickgui;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.module.Category;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2DUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class CategroyPanel {

    private Category category;
    private double x;
    private double y;
    private double width;
    private double height;

    private double prevX;
    private double prevY;

    private boolean hovered;

    private boolean dragging;

    private boolean displayModulePanel;

    private ArrayList<ModulePanel> modulePanels;

    public CategroyPanel(Category category, double x, double y, double width, double height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.modulePanels = new ArrayList<>();
        ArrayList<Object> modules = new ArrayList<>();
        modules.addAll(getModulesForCategory(this.category));
        for (Object m : modules) {
            this.modulePanels.add(new ModulePanel(m));
        }
    }

    private ArrayList<Object> getModulesForCategory(Category category) {
        ArrayList<Object> ms = new ArrayList<>();
        for (Object module : CF4M.INSTANCE.module.getModules()) {
            if (CF4M.INSTANCE.module.getCategory(module).equals(category)) {
                ms.add(module);
            }
        }
        return ms;
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.hovered = Render2DUtils.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        if (this.dragging) {
            this.x = this.prevX + mouseX;
            this.y = this.prevY + mouseY;
        }
        Render2DUtils.drawRectWH(this.x, this.y, this.width, this.height, this.hovered ? ColorUtils.SELECT : ColorUtils.BG);
        FontUtils.drawHVCenteredString(this.category.name(), this.x + this.width / 2, this.y + this.height / 2, Color.WHITE.getRGB());
        if (this.displayModulePanel) {
            double moduleY = this.y + this.height;
            for (ModulePanel modulePanel : this.modulePanels) {
                modulePanel.render(mouseX, mouseY, delta, this.x + this.width / 2 - (getWidestModule() + 10) / 2.0, moduleY, getWidestModule() + 10, FontUtils.getFontHeight() + 10);
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
        for (Object m : CF4M.INSTANCE.module.getModules()) {
            String name = CF4M.INSTANCE.module.getName(m);
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

}
