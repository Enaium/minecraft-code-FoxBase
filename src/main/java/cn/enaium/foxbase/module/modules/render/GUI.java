package cn.enaium.foxbase.module.modules.render;

import cn.enaium.foxbase.screen.clickgui.ClickGUI;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import org.lwjgl.glfw.GLFW;

public class GUI extends Module {
    public GUI() {
        super("GUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.openScreen(new ClickGUI());
        toggle();
    }
}
