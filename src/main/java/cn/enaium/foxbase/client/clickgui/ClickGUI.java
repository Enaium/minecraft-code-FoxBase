package cn.enaium.foxbase.client.clickgui;

import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.utils.FontUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ClickGUI extends Screen {

    ArrayList<TypePanel> typePanels;

    public ClickGUI() {
        super(new LiteralText(""));
        typePanels = new ArrayList<>();
        double categoryY = 5;
        for (String category : CF4M.INSTANCE.getModule().getAllType()) {
            typePanels.add(new TypePanel(category, 5, categoryY, getWidestCategory() + 50, FontUtils.getFontHeight() + 10));
            categoryY += FontUtils.getFontHeight() + 10 + 5;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (TypePanel typePanel : typePanels) {
            typePanel.render(matrices, mouseX, mouseY, delta);
        }
        FontUtils.drawString(matrices, "FoxClickGUI Design By - Enaium", 5, this.height - FontUtils.getFontHeight(), 0xFFFFFFFF);//Don't delete
        super.render(matrices, mouseX, mouseY, delta);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (TypePanel typePanel : typePanels) {
            typePanel.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (TypePanel typePanel : typePanels) {
            typePanel.mouseReleased(mouseX, mouseY, button);
        }
        return false;
    }


    private int getWidestCategory() {
        int width = 0;
        for (String name : CF4M.INSTANCE.getModule().getAllType()) {
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }
}
