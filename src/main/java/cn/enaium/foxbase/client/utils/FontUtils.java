package cn.enaium.foxbase.client.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class FontUtils {

    public static TextRenderer tr = MinecraftClient.getInstance().textRenderer;

    public static void drawString(MatrixStack matrices, String string, int x, int y, int color) {
        tr.draw(matrices, string, x, y, color);
    }

    public static void drawString(MatrixStack matrices, String string, float x, float y, int color) {
        tr.draw(matrices, string, x, y, color);
    }

    public static void drawStringWithShadow(MatrixStack matrices, String string, int x, int y, int color) {
        tr.drawWithShadow(matrices, string, x, y, color);
    }

    public static void drawStringWithShadow(MatrixStack matrices, String string, double x, double y, int color) {
        tr.drawWithShadow(matrices, string, (float) x, (float) y, color);
    }

    public static void drawStringWithShadow(MatrixStack matrices, String string, float x, float y, int color) {
        tr.drawWithShadow(matrices, string, x, y, color);
    }

    public static void drawHCenteredString(MatrixStack matrices, String text, int x, int y, int color) {
        tr.draw(matrices, text, x - tr.getWidth(text) / 2, y, color);
    }

    public static void drawHCenteredString(MatrixStack matrices, String text, double x, double y, int color) {
        tr.draw(matrices, text, (float) x - tr.getWidth(text) / 2, (float) y, color);
    }

    public static void drawHCenteredString(MatrixStack matrices, String text, float x, float y, int color) {
        tr.draw(matrices, text, x, y - tr.fontHeight / 2, color);
    }

    public static void drawVCenteredString(MatrixStack matrices, String text, int x, int y, int color) {
        tr.draw(matrices, text, x, y - tr.fontHeight / 2, color);
    }

    public static void drawVCenteredString(MatrixStack matrices, String text, double x, double y, int color) {
        tr.draw(matrices, text, (float) x, (float) y - tr.fontHeight / 2, color);
    }

    public static void drawHVCenteredString(MatrixStack matrices, String text, int x, int y, int color) {
        tr.draw(matrices, text, x - tr.getWidth(text) / 2, y - tr.fontHeight / 2, color);
    }

    public static void drawHVCenteredString(MatrixStack matrices, String text, double x, double y, int color) {
        tr.draw(matrices, text, (float) x - tr.getWidth(text) / 2, (float) y - tr.fontHeight / 2, color);
    }

    public static void drawHVCenteredString(MatrixStack matrices, String text, float x, float y, int color) {
        tr.draw(matrices, text, x - tr.getWidth(text) / 2, y - tr.fontHeight / 2, color);
    }

    public static void drawVCenteredString(MatrixStack matrices, String text, float x, float y, int color) {
        tr.draw(matrices, text, x - tr.getWidth(text) / 2, y - tr.fontHeight / 2, color);
    }


    public static int getStringWidth(String string) {
        return tr.getWidth(string);
    }

    public static int getFontHeight() {
        return tr.fontHeight;
    }
}
