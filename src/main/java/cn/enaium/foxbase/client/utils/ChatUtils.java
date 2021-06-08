package cn.enaium.foxbase.client.utils;

import cn.enaium.foxbase.client.FoxBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

/**
 * @author Enaium
 */
public class ChatUtils {
    public static void message(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText(
                Formatting.WHITE + "[" + Formatting.RED + FoxBase.instance.name + Formatting.WHITE + "] " + message));
    }
}
