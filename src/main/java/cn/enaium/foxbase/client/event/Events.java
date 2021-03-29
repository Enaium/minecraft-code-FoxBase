package cn.enaium.foxbase.client.event;

import cn.enaium.cf4m.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Project: FoxBase
 * Author: Enaium
 */
public class Events {

    public static class UpdatingEvent extends Listener {
    }

    public static class UpdatedEvent extends Listener {
    }

    public static class Render2DEvent extends Listener {
        private final MatrixStack matrixStack;

        public Render2DEvent(MatrixStack matrixStack) {
            this.matrixStack = matrixStack;
        }

        public MatrixStack getMatrixStack() {
            return matrixStack;
        }
    }

    public static class Render3DEvent extends Listener {

        public float particleTicks;

        public Render3DEvent(float particleTicks) {
            this.particleTicks = particleTicks;
        }

    }

    public static class KeyboardEvent extends Listener{
        private final int key;

        public KeyboardEvent(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }
    }
}
