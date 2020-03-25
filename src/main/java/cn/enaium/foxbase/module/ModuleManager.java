package cn.enaium.foxbase.module;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventKeyboard;
import cn.enaium.foxbase.module.modules.movement.Sprint;
import cn.enaium.foxbase.module.modules.render.ESP;
import cn.enaium.foxbase.module.modules.render.FullBright;
import cn.enaium.foxbase.module.modules.render.GUI;
import cn.enaium.foxbase.module.modules.render.HUD;

import java.util.ArrayList;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ModuleManager {
    private ArrayList<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList();
        FoxBase.instance.eventManager.register(this);
    }

    public void loadMods() {
        this.addModule(new Sprint());
        this.addModule(new FullBright());
        this.addModule(new GUI());
        this.addModule(new HUD());
        this.addModule(new ESP());
    }

    private void addModule(Module m) {
        modules.add(m);
    }

    @EventTarget
    public void onKey(EventKeyboard e) {

        for (Module mod : modules) {
            if (mod.getKeyCode() == e.getKeyCode()) {
                mod.toggle();
            }
        }
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Module> getModulesForCategory(Category c) {
        ArrayList<Module> modules = new ArrayList<>();

        for (Module m : this.modules) {
            if (m.getCategory().equals(c)) {
                modules.add(m);
            }
        }

        return modules;
    }
}