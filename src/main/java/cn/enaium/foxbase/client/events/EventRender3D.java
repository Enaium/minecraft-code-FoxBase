package cn.enaium.foxbase.client.events;

import cn.enaium.cf4m.event.EventBase;

public class EventRender3D extends EventBase {

	public float partialTicks;

	public EventRender3D(float partialTicks) {
		super(Type.PRE);
		this.partialTicks = partialTicks;
	}

}
