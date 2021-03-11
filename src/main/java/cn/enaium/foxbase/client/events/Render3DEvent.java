package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.Listener;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Render3DEvent extends Listener {

    public float particleTicks;

    public Render3DEvent(float particleTicks) {
        super(At.HEAD);
        this.particleTicks = particleTicks;
    }

}
