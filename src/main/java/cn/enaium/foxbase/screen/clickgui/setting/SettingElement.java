package cn.enaium.foxbase.screen.clickgui.setting;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class SettingElement {

    protected Setting setting;

    public SettingElement(Setting setting) {
        this.setting = setting;
    }


    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        Render2D.drawRectWH(matrices, x, y, width + height, height, ColorUtils.BG);
        String name = this.setting.getName();
        if (this.setting.isValueInt()) {
            name = name + ":" + this.setting.getCurrentValueInt();
        } else if (this.setting.isValueDouble()) {
            name = name + ":" + this.setting.getCurrentValueDouble();
        } else if (this.setting.isValueFloat()) {
            name = name + ":" + this.setting.getCurrentValueFloat();
        } else if (this.setting.isMode()) {
            name = name + ":" + this.setting.getCurrentMode();
        }
        FontUtils.drawHVCenteredString(matrices, name, x + width / 2, y + height / 2, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}
