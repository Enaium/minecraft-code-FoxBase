package cn.enaium.foxbase.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Render2D {

    public static int getScaledWidth() {
        return MinecraftClient.getInstance().getWindow().getScaledWidth();
    }

    public static int getScaledHeight() {
        return MinecraftClient.getInstance().getWindow().getScaledHeight();
    }


    public static void drawRect(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        DrawableHelper.fill(matrices, x1, y1, x2, y2, color);
    }

    public static void drawRect(MatrixStack matrices, double x1, double y1, double x2, double y2, int color) {
        fill(matrices.peek().getModel(), x1, y1, x2, y2, color);
    }

    public static void drawRectWH(MatrixStack matrices, int x, int y, int width, int height, int color) {
        DrawableHelper.fill(matrices, x, y, x + width, y + height, color);
    }

    public static void drawRectWH(MatrixStack matrices, double x, double y, double width, double height, int color) {
        fill(matrices.peek().getModel(), x, y, x + width, y + height, color);
    }

    public static void drawHorizontalLine(MatrixStack matrices, int i, int j, int k, int l) {
        if (j < i) {
            int m = i;
            i = j;
            j = m;
        }

        drawRect(matrices, i, k, j + 1, k + 1, l);
    }

    public static void drawVerticalLine(MatrixStack matrices, int i, int j, int k, int l) {
        if (k < j) {
            int m = j;
            j = k;
            k = m;
        }

        drawRect(matrices, i, j + 1, i + 1, k, l);
    }

    public static void fill(Matrix4f matrix4f, double x1, double y1, double x2, double y2, int color) {
        double j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, (float) x1, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix4f, (float) x2, (float) y2, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix4f, (float) x2, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.vertex(matrix4f, (float) x1, (float) y1, 0.0F).color(g, h, k, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
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

    public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }

    public static boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }
}
