package cn.enaium.foxbase.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class FontUtils {

    public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    public static void drawString(String string, int x, int y, int color) {
        fr.drawString(string, x, y, color);
    }

    public static void drawString(String string, float x, float y, int color) {
        fr.drawString(string, x, y, color, true);
    }

    public static void drawStringWithShadow(String string, int x, int y, int color) {
        fr.drawStringWithShadow(string, x, y, color);
    }

    public static void drawStringWithShadow(String string, double x, double y, int color) {
        fr.drawStringWithShadow(string, (float) x, (float) y, color);
    }

    public static void drawStringWithShadow(String string, float x, float y, int color) {
        fr.drawStringWithShadow(string, x, y, color);
    }

    public static void drawHCenteredString(String text, int x, int y, int color) {
        fr.drawString(text, x - fr.getStringWidth(text) / 2, y, color);
    }

    public static void drawHCenteredString(String text, double x, double y, int color) {
        fr.drawString(text, (float) x - fr.getStringWidth(text) / 2, (float) y, color, true);
    }

    public static void drawHCenteredString(String text, float x, float y, int color) {
        fr.drawString(text, x, y - fr.FONT_HEIGHT / 2, color, true);
    }

    public static void drawVCenteredString(String text, int x, int y, int color) {
        fr.drawString(text, x, y - fr.FONT_HEIGHT / 2, color);
    }

    public static void drawVCenteredString(String text, double x, double y, int color) {
        fr.drawString(text, (float) x, (float) y - fr.FONT_HEIGHT / 2, color, true);
    }

    public static void drawHVCenteredString(String text, int x, int y, int color) {
        fr.drawString(text, x - fr.getStringWidth(text) / 2, y - fr.FONT_HEIGHT / 2, color);
    }

    public static void drawHVCenteredString(String text, double x, double y, int color) {
        fr.drawString(text, (float) x - fr.getStringWidth(text) / 2, (float) y - fr.FONT_HEIGHT / 2, color, true);
    }

    public static void drawHVCenteredString(String text, float x, float y, int color) {
        fr.drawString(text, x - fr.getStringWidth(text) / 2, y - fr.FONT_HEIGHT / 2, color, true);
    }

    public static void drawVCenteredString(String text, float x, float y, int color) {
        fr.drawString(text, x - fr.getStringWidth(text) / 2, y - fr.FONT_HEIGHT / 2, color, true);
    }


    public static int getStringWidth(String string) {
        return fr.getStringWidth(string);
    }

    public static int getFontHeight() {
        return fr.FONT_HEIGHT;
    }
}
