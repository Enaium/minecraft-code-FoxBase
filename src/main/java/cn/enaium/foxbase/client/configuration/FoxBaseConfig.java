package cn.enaium.foxbase.client.configuration;

import cn.enaium.cf4m.configuration.CommandConfiguration;
import cn.enaium.foxbase.client.utils.ChatUtils;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class FoxBaseConfig extends CommandConfiguration {
    @Override
    public void message(String message) {
        ChatUtils.message(message);
    }
}