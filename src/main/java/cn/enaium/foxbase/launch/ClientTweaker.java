package cn.enaium.foxbase.launch;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: untitled
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ClientTweaker implements ITweaker {

    private List<String> args;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args = new ArrayList<>();
        this.args.addAll(args);
        addArg("--gameDir", gameDir != null ? gameDir.getAbsolutePath() : null);
        addArg("--assetsDir", assetsDir != null ? assetsDir.getPath() : null);
        addArg("--version", profile);
    }

    void addArg(String name, String value) {
        if (value != null) {
            args.add(name);
            args.add(value);
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.UNKNOWN);
        Mixins.addConfiguration("mixins.foxbase.json");
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }
}
