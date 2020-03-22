package cn.enaium.foxbase;

import cn.enaium.foxbase.event.EventManager;
import cn.enaium.foxbase.module.ModuleManager;
import org.lwjgl.opengl.Display;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public enum FoxBase {

    instance;

    public String name = "FoxBase";
    public String version = "B1";
    public String author = "Enaium";

    public EventManager eventManager;
    public ModuleManager moduleManager;

    public void Start() {
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        Display.setTitle(name + " | " + version + " | " + author);
        moduleManager.loadMods();
    }

    public void Stop() {
        System.out.println("Stopping!!!!!!!!!!!");
    }

}
