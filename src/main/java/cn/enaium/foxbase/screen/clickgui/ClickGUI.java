package cn.enaium.foxbase.screen.clickgui;

import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.utils.FontUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;

public class ClickGUI extends Screen {

    ArrayList<CategroyPanel> categroyPanels;

    public ClickGUI() {
        super(new LiteralText(""));
        categroyPanels = new ArrayList<>();
        double categoryY = 5;
        for (Category category : Category.values()) {
            categroyPanels.add(new CategroyPanel(category, 5, categoryY, getWidestCategory() + 50, FontUtils.getFontHeight() + 10));
            categoryY += FontUtils.getFontHeight() + 10 + 5;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        for (CategroyPanel categroyPanel : categroyPanels) {
            categroyPanel.render(mouseX, mouseY, delta);
        }
        FontUtils.drawString("FoxClickGUI Design By - Enaium", 5, this.height - FontUtils.getFontHeight(), 0xFFFFFFFF);//Don't delete
        super.render(mouseX, mouseY, delta);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (CategroyPanel categroyPanel : categroyPanels) {
            categroyPanel.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (CategroyPanel categroyPanel : categroyPanels) {
            categroyPanel.mouseReleased(mouseX, mouseY, button);
        }
        return false;
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
