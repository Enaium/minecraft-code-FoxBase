package cn.enaium.foxbase.gui.clickgui.setting;

import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.Render2D;

public class ValueSettingElement extends SettingElement {

    private boolean addHovered;
    private boolean removeHovered;

    public ValueSettingElement(Setting setting) {
        super(setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        super.render(mouseX, mouseY, delta, x, y, width, height);
        double Width = width / 2;
        double Height = height;
        int color = ColorUtils.CHECK_BG;
        if (this.setting.isToggle()) color = ColorUtils.CHECK_TOGGLE;
        if (this.addHovered) color = ColorUtils.SELECT;
        if (this.removeHovered) color = ColorUtils.SELECT;
        Render2D.drawRectWH(x + Width + 2, y + 2, Height - 4, height - 4, color);
        Render2D.drawRectWH(x + Width + 2 + Width, y + 2, Height - 4, height - 4, color);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.addHovered && button == 0) {
            if (this.setting.isValueInt())
                this.setting.setCurrentValueInt(this.setting.getCurrentValueInt() + 1);
            else if (this.setting.isValueFloat())
                this.setting.setCurrentValueFloat(this.setting.getCurrentValueFloat() + 0.1F);
            else if (this.setting.isValueDouble())
                this.setting.setCurrentValueDouble(this.setting.getCurrentValueDouble() + 0.1D);
        } else if (this.removeHovered && button == 0) {
            if (this.setting.isValueInt())
                this.setting.setCurrentValueInt(this.setting.getCurrentValueInt() - 1);
            else if (this.setting.isValueFloat())
                this.setting.setCurrentValueFloat(this.setting.getCurrentValueFloat() - 0.1F);
            else if (this.setting.isValueDouble())
                this.setting.setCurrentValueDouble(this.setting.getCurrentValueDouble() - 0.1D);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
