package cn.enaium.foxbase.client;

import cn.enaium.cf4m.CF4M;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public enum FoxBase {

    instance;

    public String name = "FoxBase";
    public String author = "Enaium";
    public String version = "1.0";
    public String game = "21w07a";

    public void run() {
        CF4M.INSTANCE.run(this);
    }
}
