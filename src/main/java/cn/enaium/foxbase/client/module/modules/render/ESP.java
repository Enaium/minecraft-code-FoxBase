package cn.enaium.foxbase.client.module.modules.render;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.setting.settings.EnableSetting;
import cn.enaium.foxbase.client.events.EventRender3D;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module("ESP")
public class ESP {

    private EnableSetting player = new EnableSetting(this, "Player", "",true);
    private EnableSetting mob = new EnableSetting(this, "Mob", "",true);
    private EnableSetting anim = new EnableSetting(this, "Animal", "",true);

    @Event
    public void onESP(EventRender3D e) {
        for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer) o;
                if (entity != Minecraft.getMinecraft().thePlayer && !entity.isDead) {
                    
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
