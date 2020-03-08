/*
 * Copyright (C) 2014 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package cn.enaium.foxbase.utils;

import cn.enaium.foxbase.FoxBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
public enum ChatUtils
{
	;
	
	private static final MinecraftClient MC = MinecraftClient.getInstance();
	
	public static final String CHROME_PREFIX =
		"\u00a7c[\u00a76" + FoxBase.instance.name + "\u00a7c]\u00a7r ";

	public static void component(Text component)
	{
		ChatHud chatHud = MC.inGameHud.getChatHud();
		LiteralText prefix = new LiteralText(CHROME_PREFIX);
		chatHud.addMessage(prefix.append(component));
	}
	
	public static void message(String message)
	{
		component(new LiteralText(message));
	}

}
