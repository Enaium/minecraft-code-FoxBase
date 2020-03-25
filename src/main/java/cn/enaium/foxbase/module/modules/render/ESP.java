package cn.enaium.foxbase.module.modules.render;

import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventRender3D;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.Render3DUtils;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class ESP extends Module {

    private Setting player = new Setting(this, "Player", true);
    private Setting mob = new Setting(this, "Mob", true);
    private Setting anim = new Setting(this, "Animal", true);


    public ESP() {
        super("ESP", 0, Category.RENDER);
    }

    @EventTarget
    public void onESP(EventRender3D e) {
        for (Object o : mc.theWorld.loadedEntityList) {
            if (o instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer) o;
                if (entity != mc.thePlayer && !entity.isDead) {
                    
                }
            }

            if (o instanceof EntityAnimal) {
                EntityAnimal entity = (EntityAnimal) o;
                if (!entity.isDead) {
                    
                }
            }

            if (o instanceof EntityMob) {
                EntityMob entity = (EntityMob) o;
                if (!entity.isDead) {
                    
                }
            }
        }
    }


}
