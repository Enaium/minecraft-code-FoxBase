package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Render2DEvent extends Listener {
    private final MatrixStack matrixStack;

    public Render2DEvent(MatrixStack matrixStack) {
        super(At.HEAD);
        this.matrixStack = matrixStack;
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

}
