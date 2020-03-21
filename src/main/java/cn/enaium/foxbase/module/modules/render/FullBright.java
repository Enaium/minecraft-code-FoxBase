package cn.enaium.foxbase.module.modules.render;

import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventUpdate;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import org.lwjgl.glfw.GLFW;

public class FullBright extends Module {

    public FullBright() {
        super("FullBright", GLFW.GLFW_KEY_G, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.options.gamma = 300;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.options.gamma = 1;
    }


}
