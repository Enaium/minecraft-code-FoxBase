package cn.enaium.foxbase.client.setting;

import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class ModeSetting {

    private String current;
    private final List<String> modes;

    public ModeSetting(String current, List<String> modes) {
        this.current = current;
        this.modes = modes;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public List<String> getModes() {
        return modes;
    }

    public int getCurrentModeIndex() {
        int index = 0;
        for (String s : modes) {
            if (s.equalsIgnoreCase(current)) {
                return index;
            }
            index++;
        }
        return index;
    }
}
