package cn.enaium.foxbase.client.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;

public class FontUtils {

    public static TextRenderer tr = MinecraftClient.getInstance().textRenderer;

    public static void drawString(String string, int x, int y, int color) {
        tr.draw(string, x, y, color);
    }

    public static void drawString(String string, float x, float y, int color) {
        tr.draw(string, x, y, color);
    }

    public static void drawStringWithShadow(String string, int x, int y, int color) {
        tr.drawWithShadow(string, x, y, color);
    }

    public static void drawStringWithShadow(String string, double x, double y, int color) {
        tr.drawWithShadow(string, (float) x, (float) y, color);
    }

    public static void drawStringWithShadow(String string, float x, float y, int color) {
        tr.drawWithShadow(string, x, y, color);
    }

    public static void drawHCenteredString(String text, int x, int y, int color) {
        tr.draw(text, x - tr.getStringWidth(text) / 2, y, color);
    }

    public static void drawHCenteredString(String text, double x, double y, int color) {
        tr.draw(text, (float) x - tr.getStringWidth(text) / 2, (float) y, color);
    }

    public static void drawHCenteredString(String text, float x, float y, int color) {
        tr.draw(text, x, y - tr.fontHeight / 2, color);
    }

    public static void drawVCenteredString(String text, int x, int y, int color) {
        tr.draw(text, x, y - tr.fontHeight / 2, color);
    }

    public static void drawVCenteredString(String text, double x, double y, int color) {
        tr.draw(text, (float) x, (float) y - tr.fontHeight / 2, color);
    }

    public static void drawHVCenteredString(String text, int x, int y, int color) {
        tr.draw(text, x - tr.getStringWidth(text) / 2, y - tr.fontHeight / 2, color);
    }

    public static void drawHVCenteredString(String text, double x, double y, int color) {
        tr.draw(text, (float) x - tr.getStringWidth(text) / 2, (float) y - tr.fontHeight / 2, color);
    }

    public static void drawHVCenteredString(String text, float x, float y, int color) {
        tr.draw(text, x - tr.getStringWidth(text) / 2, y - tr.fontHeight / 2, color);
    }

    public static void drawVCenteredString(String text, float x, float y, int color) {
        tr.draw(text, x - tr.getStringWidth(text) / 2, y - tr.fontHeight / 2, color);
    }


    public static int getStringWidth(String string) {
        return tr.getStringWidth(string);
    }

    public static int getFontHeight() {
        return tr.fontHeight;
    }
}
