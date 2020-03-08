package cn.enaium.foxbase.module;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventKeyboard;
import cn.enaium.foxbase.module.modules.movement.Sprint;
import cn.enaium.foxbase.module.modules.render.HUD;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ModuleManager {
    private ArrayList<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList();
        FoxBase.instance.eventManager.register(this);
    }

    public void loadMods() {
        this.addModule(new Sprint());
        this.addModule(new HUD());
    }


    private void addModule(Module m) {
        modules.add(m);
    }

    @EventTarget
    public void onKey(EventKeyboard e) {
        if(e.getAction() != GLFW.GLFW_PRESS)
            return;

        Screen screen = MinecraftClient.getInstance().currentScreen;
        if(screen != null)
            return;

        for (Module mod : modules) {
            if (mod.getKeyCode() == e.getKeyCode())
                mod.toggle();
        }
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

}
