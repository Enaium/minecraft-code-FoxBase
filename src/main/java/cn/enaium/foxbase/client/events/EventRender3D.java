package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.Listener;

public class EventRender3D extends Listener {

	public float particleTicks;

	public EventRender3D(float particleTicks) {
		super(At.TAIL);
		this.particleTicks = particleTicks;
	}

}
