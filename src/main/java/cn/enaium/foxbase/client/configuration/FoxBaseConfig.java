package cn.enaium.foxbase.client.configuration;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.ICommandConfiguration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.foxbase.client.FoxBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

/**
 * Project: FoxBase-forge-example
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Configuration
public class FoxBaseConfig implements IConfiguration {
    @Override
    public ICommandConfiguration command() {
        return new ICommandConfiguration() {
            @Override
            public void message(String message) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(
                        Formatting.WHITE + "[" + Formatting.RED + FoxBase.instance.name + Formatting.WHITE + "] " + message));
            }
        };
    }
}
