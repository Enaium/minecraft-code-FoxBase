package cn.enaium.foxbase.module.modules.movement;

import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventUpdate;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import org.lwjgl.glfw.GLFW;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", GLFW.GLFW_KEY_V, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.player.setSprinting(true);
    }
}
