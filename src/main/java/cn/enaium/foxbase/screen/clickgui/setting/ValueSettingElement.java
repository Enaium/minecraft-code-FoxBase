package cn.enaium.foxbase.screen.clickgui.setting;

import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

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
            if (this.setting.isValueInt()) {
                if (this.setting.getCurrentValueInt() < this.setting.getMaxValueInt()) {
                    this.setting.setCurrentValueInt(this.setting.getCurrentValueInt() + 1);
                }
            } else if (this.setting.isValueFloat()) {
                if (this.setting.getCurrentValueFloat() < this.setting.getMaxValueFloat()) {
                    this.setting.setCurrentValueFloat(this.setting.getCurrentValueFloat() + 0.1F);
                }
            } else if (this.setting.isValueDouble()) {
                if (this.setting.getCurrentValueDouble() < this.setting.getMaxValueDouble()) {
                    this.setting.setCurrentValueDouble(this.setting.getCurrentValueDouble() + 0.1D);
                }
            } else if (this.setting.isMode()) {

                try {
                    this.setting.setCurrentMode(this.setting.getModes().get(this.setting.getCurrentModeIndex() + 1));
                } catch (Exception e) {
                    this.setting.setCurrentMode(this.setting.getModes().get(0));
                }
            }
        } else if (this.removeHovered && button == 0) {
            if (this.setting.isValueInt()) {
                if (this.setting.getCurrentValueInt() > this.setting.getMinValueInt()) {
                    this.setting.setCurrentValueInt(this.setting.getCurrentValueInt() - 1);
                }
            } else if (this.setting.isValueFloat()) {
                if (this.setting.getCurrentValueFloat() > this.setting.getMinValueFloat()) {
                    this.setting.setCurrentValueFloat(this.setting.getCurrentValueFloat() - 0.1F);
                }
            } else if (this.setting.isValueDouble()) {
                if (this.setting.getCurrentValueDouble() > this.setting.getMinValueDouble()) {
                    this.setting.setCurrentValueDouble(this.setting.getCurrentValueDouble() - 0.1D);
                }
            } else if (this.setting.isMode()) {
                try {
                    this.setting.setCurrentMode(this.setting.getModes().get(this.setting.getCurrentModeIndex() - 1));
                } catch (Exception e) {
                    this.setting.setCurrentMode(this.setting.getModes().get(this.setting.getModes().size() - 1));
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
