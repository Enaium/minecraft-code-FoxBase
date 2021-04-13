package cn.enaium.foxbase.client.event;

import net.minecraft.client.util.math.MatrixStack;

/**
 * Project: FoxBase
 * Author: Enaium
 */
public class Events {

    public static class UpdatingEvent {
    }

    public static class UpdatedEvent {
    }

    public static class Render2DEvent {
        private final MatrixStack matrixStack;

        public Render2DEvent(MatrixStack matrixStack) {
            this.matrixStack = matrixStack;
        }

        public MatrixStack getMatrixStack() {
            return matrixStack;
        }
    }

    public static class Render3DEvent {

        public float particleTicks;

        public Render3DEvent(float particleTicks) {
            this.particleTicks = particleTicks;
        }

    }

    public static class KeyboardEvent {
        private final int key;

        public KeyboardEvent(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }
    }
}
