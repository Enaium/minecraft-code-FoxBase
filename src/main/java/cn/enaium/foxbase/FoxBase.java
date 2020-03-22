package cn.enaium.foxbase;

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

    public void Start() {
        Display.setTitle(name + " | " + version + " | " + author);
    }

    public void Stop() {

    }

}
