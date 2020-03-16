package cn.enaium.foxbase.module.modules.combat;

import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventUpdate;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.lwjgl.glfw.GLFW;

import java.util.Iterator;
import java.util.stream.StreamSupport;

public class Aura extends Module {

    public Aura() {
        super("Aura", GLFW.GLFW_KEY_R, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        ClientPlayerEntity player = mc.player;
        ClientWorld world = mc.world;
        for (Iterator<Entity> entities = StreamSupport.stream(world.getEntities().spliterator(), true).iterator(); entities.hasNext(); ) {

        }
    }
}
