package cn.enaium.foxbase.gui.clickgui;

import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.utils.FontUtils;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ClickGUI extends GuiScreen {

    ArrayList<CategroyPanel> categroyPanels;

    public ClickGUI() {
        categroyPanels = new ArrayList<>();
        double categoryY = 5;
        for (Category category : Category.values()) {
            categroyPanels.add(new CategroyPanel(category, 5, categoryY, getWidestCategory() + 50, FontUtils.getFontHeight() + 10));
            categoryY += FontUtils.getFontHeight() + 10 + 5;
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        for (CategroyPanel categroyPanel : categroyPanels) {
            categroyPanel.render(mouseX, mouseY, delta);
        }
        FontUtils.drawString("FoxClickGUI Design By - Enaium", 5, this.height - FontUtils.getFontHeight(), 0xFFFFFFFF);//Don't delete
        super.drawScreen(mouseX, mouseY, delta);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        for (CategroyPanel categroyPanel : categroyPanels) {
            categroyPanel.mouseClicked(mouseX, mouseY, button);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        for (CategroyPanel categroyPanel : categroyPanels) {
            categroyPanel.mouseReleased(mouseX, mouseY, button);
        }
        super.mouseReleased(mouseX, mouseY, button);
    }


    private int getWidestCategory() {
        int width = 0;
        for (Category c : Category.values()) {
            String name = c.name();
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }
}
