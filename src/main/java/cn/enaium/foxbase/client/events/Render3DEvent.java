package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.EventBase;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Render3DEvent extends EventBase {

	public float particlTicks;

	public Render3DEvent(float partialTicks) {
		super(Type.PRE);
		this.particlTicks = partialTicks;
	}

}
