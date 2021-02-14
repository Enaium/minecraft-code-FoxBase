package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.EventBase;

public class EventRender3D extends EventBase {

	public float particlTicks;

	public EventRender3D(float particlTicks) {
		super(Type.PRE);
		this.particlTicks = particlTicks;
	}

}
