package cn.enaium.foxbase.gui.clickgui.setting;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;

import java.awt.*;

public class SettingElement {

    protected Setting setting;

    public SettingElement(Setting setting) {
        this.setting = setting;
    }


    public void render(int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        Render2D.drawRectWH(x, y, width, height + height, ColorUtils.BG);
        FontUtils.drawHVCenteredString(this.setting.getName(), x + width / 2, y + width / 2, Color.WHITE.getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}
