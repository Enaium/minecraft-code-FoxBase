package cn.enaium.foxbase.client;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.manager.ClassManager;
import cn.enaium.foxbase.client.commands.HelpCommand;
import cn.enaium.foxbase.client.commands.SetCommand;
import cn.enaium.foxbase.client.commands.ToggleCommand;
import cn.enaium.foxbase.client.config.ModuleConfig;
import cn.enaium.foxbase.client.modules.combat.Aura;
import cn.enaium.foxbase.client.modules.movement.Sprint;
import cn.enaium.foxbase.client.modules.render.FullBright;
import cn.enaium.foxbase.client.modules.render.GUI;
import cn.enaium.foxbase.client.modules.render.HUD;
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
    public String game = "21w03a";

    public void run() {
        new CF4M(this, MinecraftClient.getInstance().runDirectory.toString() + "/" + name);
        CF4M.getInstance().run();

        CF4M.getInstance().moduleManager.modules.add(new Aura());
        CF4M.getInstance().moduleManager.modules.add(new Sprint());
        CF4M.getInstance().moduleManager.modules.add(new FullBright());
        CF4M.getInstance().moduleManager.modules.add(new GUI());
        CF4M.getInstance().moduleManager.modules.add(new HUD());

        CF4M.getInstance().commandManager.commands.put(new String[]{"h", "help"}, new HelpCommand());
        CF4M.getInstance().commandManager.commands.put(new String[]{"s", "set"}, new SetCommand());
        CF4M.getInstance().commandManager.commands.put(new String[]{"t", "toggle"}, new ToggleCommand());

        CF4M.getInstance().configManager.configs.add(new ModuleConfig());
    }

    public void stop() {
        CF4M.getInstance().stop();
    }
}
