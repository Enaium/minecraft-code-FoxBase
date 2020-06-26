package cn.enaium.foxbase.event.events;

import cn.enaium.foxbase.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class EventRender2D extends Event {
	private final MatrixStack matrixStack;
	public EventRender2D(MatrixStack matrixStack) {
		super(Type.PRE);
		this.matrixStack = matrixStack;
	}

	public MatrixStack getMatrixStack() {
		return matrixStack;
	}

}
