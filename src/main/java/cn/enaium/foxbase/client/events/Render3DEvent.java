package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.Event;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Render3DEvent extends Event {

	public float particlTicks;

	public Render3DEvent(float particlTicks) {
		super(Type.PRE);
		this.particlTicks = particlTicks;
	}

}
