package cn.enaium.foxbase.screen.clickgui.setting;

import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.Render2D;

public class BooleanSettingElement extends SettingElement {

    private boolean hovered;

    public BooleanSettingElement(Setting setting) {
        super(setting);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta, double x, double y, double width, double height) {
        super.render(mouseX, mouseY, delta, x, y, width, height);
        this.hovered = Render2D.isHovered(mouseX, mouseY, x + width + 2, y + 2, height - 4, height - 4);
        int color = ColorUtils.CHECK_BG;
        if (this.setting.isToggle()) {
            color = ColorUtils.CHECK_TOGGLE;
        }
        if (this.hovered) {
            color = ColorUtils.SELECT;
        }
        Render2D.drawRectWH(x + width + 2, y + 2, height - 4, height - 4, color);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hovered && button == 0) {
            this.setting.setToggle(!this.setting.isToggle());
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
