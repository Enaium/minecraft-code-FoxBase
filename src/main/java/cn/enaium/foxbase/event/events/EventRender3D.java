package cn.enaium.foxbase.event.events;

import cn.enaium.foxbase.event.Event;
public class EventRender3D extends Event {

	public float particlTicks;

	public EventRender3D(float particlTicks) {
		super(Type.PRE);
		this.particlTicks = particlTicks;
	}

}
