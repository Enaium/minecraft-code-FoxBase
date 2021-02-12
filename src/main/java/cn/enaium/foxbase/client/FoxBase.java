package cn.enaium.foxbase.client;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.manager.ClassManager;
import net.minecraft.client.MinecraftClient;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public enum FoxBase {

    instance;

    public String name = "FoxBase";
    public String author = "Enaium";
    public String version = "1.0";
    public String game = "21w06a";

    public void run() {
        new CF4M(this, MinecraftClient.getInstance().runDirectory.toString() + "/" + name);
        CF4M.getInstance().classManager = new ClassManager() {
            @Override
            public Class<?> loadClass(Class<?> clazz) throws ClassNotFoundException {
                return Class.forName(clazz.getName());
            }
        };
        CF4M.getInstance().run();
    }

    public void stop() {
        CF4M.getInstance().stop();
    }
}
