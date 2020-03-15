package cn.enaium.foxbase.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Render2D {

    public static int getScaledWidth() {
        return MinecraftClient.getInstance().getWindow().getScaledWidth();
    }

    public static int getScaledHeight() {
        return MinecraftClient.getInstance().getWindow().getScaledHeight();
    }

    public static void drawString(String string, int x, int y, int color) {
        MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID).draw(string, x, y, color);
    }

    public static void drawString(String string, float x, float y, int color) {
        MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID).draw(string, x, y, color);
    }

    public static void drawStringWithShadow(String string, int x, int y, int color) {
        MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID).drawWithShadow(string, x, y, color);
    }

    public static void drawStringWithShadow(String string, float x, float y, int color) {
        MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID).drawWithShadow(string, x, y, color);
    }

    public static int getStringWidth(String string) {
        return MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID).getStringWidth(string);
    }

    public static int getFontHeight() {
        return 13;
    }

    public static void drawRect(int x1, int y1, int x2, int y2, int color) {
        DrawableHelper.fill(x1, y1, x2, y2, color);
    }

    public static void drawRectWH(int x, int y, int width, int height, int color) {
        DrawableHelper.fill(x, y, x + width, y + height, color);
    }

    public static void drawHorizontalLine(int i, int j, int k, int l) {
        if (j < i) {
            int m = i;
            i = j;
            j = m;
        }

        drawRect(i, k, j + 1, k + 1, l);
    }

    public static void drawVerticalLine(int i, int j, int k, int l) {
        if (k < j) {
            int m = j;
            j = k;
            k = m;
        }

        drawRect(i, j + 1, i + 1, k, l);
    }

    public static void setColor(Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                color.getAlpha() / 255.0f);
    }

    public static void setColor(int rgba) {
        int r = rgba & 0xFF;
        int g = rgba >> 8 & 0xFF;
        int b = rgba >> 16 & 0xFF;
        int a = rgba >> 24 & 0xFF;
        GL11.glColor4b((byte) r, (byte) g, (byte) b, (byte) a);
    }

    public static int toRGBA(Color c) {
        return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }
}
