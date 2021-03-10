package cn.enaium.foxbase.client.configuration;

import cn.enaium.cf4m.annotation.Configuration;
import cn.enaium.cf4m.configuration.IConfiguration;
import cn.enaium.foxbase.client.FoxBase;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Configuration
public class FoxBaseConfiguration implements IConfiguration
{
	@Override
	public void message(String message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(
				ChatFormatting.WHITE + "[" + ChatFormatting.RED + FoxBase.instance.name + ChatFormatting.WHITE + "] " + message));
	}
}
