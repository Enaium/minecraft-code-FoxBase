package cn.enaium.foxbase.utils;

import cn.enaium.foxbase.client.FoxBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ChatUtils {
    private static final MinecraftClient MC = MinecraftClient.getInstance();

    public static final String PREFIX =
            "\u00a7c[\u00a76" + FoxBase.instance.name + "\u00a7c]\u00a7r ";

    public static void component(Text component) {
        ChatHud chatHud = MC.inGameHud.getChatHud();
        LiteralText prefix = new LiteralText(PREFIX);
        chatHud.addMessage(prefix.append(component));
    }

    public static void message(String message) {
        component(new LiteralText(message));
    }

}
